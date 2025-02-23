package com.checkout;

import com.checkout.common.AbstractFileRequest;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.FileRequest;
import com.checkout.marketplace.MarketplaceFileRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static com.checkout.ClientOperation.POST;
import static com.checkout.common.CheckoutUtils.PROJECT_NAME;
import static com.checkout.common.CheckoutUtils.getVersionFromManifest;

@Slf4j
class ApacheHttpClientTransport implements Transport {

    private static final String ACCEPT_CSV = "text/csv";
    private static final String ACCEPT_JSON = "application/json;charset=UTF-8";
    private static final String AUTHORIZATION = "Authorization";
    private static final String CKO_IDEMPOTENCY_KEY = "Cko-Idempotency-Key";
    private static final String FILE = "file";
    private static final String PURPOSE = "purpose";
    private static final String USER_AGENT = "user-agent";
    private static final String ACCEPT = "Accept";
    private static final String CKO_REQUEST_ID = "Cko-Request-Id";
    private static final String NO_REQUEST_ID_SUPPLIED = "NO_REQUEST_ID_SUPPLIED";
    private static final String PATH = "path";
    private final URI baseUri;
    private final CloseableHttpClient httpClient;
    private final Executor executor;

    ApacheHttpClientTransport(final URI baseUri, final HttpClientBuilder httpClientBuilder, final Executor executor) {
        CheckoutUtils.validateParams("baseUri", baseUri, "httpClientBuilder", httpClientBuilder, "executor", executor);
        this.baseUri = baseUri;
        this.httpClient = httpClientBuilder.build();
        this.executor = executor;
    }

    private Header[] sanitiseHeaders(final Header[] headers) {
        return Arrays.stream(headers)
                .filter(it -> !it.getName().equals(AUTHORIZATION))
                .toArray(Header[]::new);
    }

    @Override
    public CompletableFuture<Response> invoke(final ClientOperation clientOperation,
                                              final String path,
                                              final SdkAuthorization authorization,
                                              final String requestBody,
                                              final String idempotencyKey) {
        return CompletableFuture.supplyAsync(() -> {
            final HttpUriRequest request;
            switch (clientOperation) {
                case GET:
                case GET_CSV_CONTENT:
                    request = new HttpGet(getRequestUrl(path));
                    break;
                case PUT:
                    request = new HttpPut(getRequestUrl(path));
                    break;
                case POST:
                    request = new HttpPost(getRequestUrl(path));
                    break;
                case DELETE:
                    request = new HttpDelete(getRequestUrl(path));
                    break;
                case PATCH:
                    request = new HttpPatch(getRequestUrl(path));
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported HTTP Method: " + clientOperation);
            }
            if (idempotencyKey != null) {
                request.setHeader(CKO_IDEMPOTENCY_KEY, idempotencyKey);
            }
            log.info("{}: {}", clientOperation, request.getURI());
            return performCall(authorization, requestBody, request, clientOperation);
        }, executor);
    }

    @Override
    public CompletableFuture<Response> invokeQuery(final ClientOperation clientOperation,
                                                   final String path,
                                                   final SdkAuthorization authorization,
                                                   final Map<String, String> queryParams) {
        return CompletableFuture.supplyAsync(() -> {
            final HttpUriRequest request;
            try {
                final List<NameValuePair> params = queryParams.entrySet().stream()
                        .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList());
                request = new HttpGet(new URIBuilder(getRequestUrl(path)).addParameters(params).build());
                log.info("GET: {}", request.getURI());
                return performCall(authorization, null, request, clientOperation);
            } catch (final URISyntaxException e) {
                throw new CheckoutException(e);
            }
        }, executor);
    }

    @Override
    public CompletableFuture<Response> submitFile(final String path, final SdkAuthorization authorization, final AbstractFileRequest fileRequest) {
        return CompletableFuture.supplyAsync(() -> {
            final HttpPost post = new HttpPost(getRequestUrl(path));
            post.setEntity(getMultipartFileEntity(fileRequest));
            return performCall(authorization, null, post, POST);
        }, executor);
    }

    private HttpEntity getMultipartFileEntity(final AbstractFileRequest abstractFileRequest) {
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (abstractFileRequest instanceof FileRequest) {
            final FileRequest fileRequest = (FileRequest) abstractFileRequest;
            builder.addBinaryBody(FILE, fileRequest.getFile(), fileRequest.getContentType(), fileRequest.getFile().getName())
                    .addTextBody(PURPOSE, fileRequest.getPurpose().getPurpose(), ContentType.DEFAULT_BINARY)
                    .build();
        } else if (abstractFileRequest instanceof MarketplaceFileRequest) {
            final MarketplaceFileRequest fileRequest = (MarketplaceFileRequest) abstractFileRequest;
            builder.addBinaryBody(PATH, fileRequest.getFile(), fileRequest.getContentType(), fileRequest.getFile().getName())
                    .addTextBody(PURPOSE, fileRequest.getPurpose().getPurpose(), ContentType.DEFAULT_TEXT)
                    .build();
        } else {
            throw new CheckoutException("Not supported request object");
        }
        return builder.build();
    }

    private Response performCall(final SdkAuthorization authorization,
                                 final String requestBody,
                                 final HttpUriRequest request,
                                 final ClientOperation clientOperation) {
        request.setHeader(USER_AGENT, PROJECT_NAME + "/" + getVersionFromManifest());
        request.setHeader(ACCEPT, getAcceptHeader(clientOperation));
        request.setHeader(AUTHORIZATION, authorization.getAuthorizationHeader());
        log.info("Request: " + Arrays.toString(sanitiseHeaders(request.getAllHeaders())));
        if (requestBody != null && request instanceof HttpEntityEnclosingRequest) {
            ((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        }
        try (final CloseableHttpResponse response = httpClient.execute(request)) {
            log.info("Response: " + response.getStatusLine().getStatusCode() + " " + Arrays.toString(response.getAllHeaders()));
            final int statusCode = response.getStatusLine().getStatusCode();
            final String requestId = Optional.ofNullable(response.getFirstHeader(CKO_REQUEST_ID)).map(Header::getValue).orElse(NO_REQUEST_ID_SUPPLIED);
            if (statusCode != HttpStatus.SC_NOT_FOUND && response.getEntity() != null && response.getEntity().getContent() != null) {
                return Response.builder()
                        .statusCode(statusCode)
                        .requestId(requestId)
                        .body(EntityUtils.toString(response.getEntity()))
                        .build();
            }
            return Response.builder().statusCode(statusCode).requestId(requestId).build();
        } catch (final Exception e) {
            log.error("Exception occurred during the execution of the client...", e);
        }
        return Response.builder().statusCode(HttpStatus.SC_BAD_REQUEST).requestId(NO_REQUEST_ID_SUPPLIED).build();
    }

    private String getAcceptHeader(final ClientOperation clientOperation) {
        switch (clientOperation) {
            case GET:
            case PUT:
            case POST:
            case DELETE:
            case PATCH:
                return ACCEPT_JSON;
            case GET_CSV_CONTENT:
                return ACCEPT_CSV;
            default:
                throw new IllegalStateException(String.format("Accept header not configured for client operation %s", clientOperation));
        }
    }

    private String getRequestUrl(final String path) {
        try {
            return baseUri.resolve(path).toURL().toString();
        } catch (final MalformedURLException e) {
            throw new CheckoutException(e);
        }
    }

}
