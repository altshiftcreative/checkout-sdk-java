package com.checkout.webhooks;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class WebhooksClientImpl extends AbstractClient implements WebhooksClient {

    private static final String WEBHOOKS = "webhooks";

    public WebhooksClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<List<WebhookResponse>> retrieveWebhooks() {
        return apiClient.getAsync(WEBHOOKS, sdkAuthorization(), WebhookResponse[].class)
                .thenApply(it -> it == null ? new WebhookResponse[0] : it)
                .thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<WebhookResponse> registerWebhook(final WebhookRequest webhookRequest) {
        return registerWebhook(webhookRequest, null);
    }

    @Override
    public CompletableFuture<WebhookResponse> registerWebhook(final WebhookRequest webhookRequest, final String idempotencyKey) {
        validateParams("webhookRequest", webhookRequest);
        return apiClient.postAsync(WEBHOOKS, sdkAuthorization(), WebhookResponse.class, webhookRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<WebhookResponse> retrieveWebhook(final String webhookId) {
        validateParams("webhookId", webhookId);
        return apiClient.getAsync(buildPath(WEBHOOKS, webhookId), sdkAuthorization(), WebhookResponse.class);
    }

    @Override
    public CompletableFuture<WebhookResponse> updateWebhook(final String webhookId, final WebhookRequest webhookRequest) {
        validateParams("webhookId", webhookId, "webhookRequest", webhookRequest);
        return apiClient.putAsync(buildPath(WEBHOOKS, webhookId), sdkAuthorization(), WebhookResponse.class, webhookRequest);
    }

    @Override
    public CompletableFuture<Void> removeWebhook(final String webhookId) {
        validateParams("webhookId", webhookId);
        return apiClient.deleteAsync(buildPath(WEBHOOKS, webhookId), sdkAuthorization());
    }

}
