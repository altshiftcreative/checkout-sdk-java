package com.checkout.customers.four;

import com.checkout.ApiClient;
import com.checkout.CheckoutApiException;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.TestHelper;
import com.checkout.common.Phone;
import com.checkout.common.four.IdResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomersClientImplTest {

    private static final String CUSTOMER_ID = "cus_123456789abcdefgh";

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @Mock
    private CustomerResponse customerResponse;

    @Mock
    private IdResponse idResponse;

    @Mock
    private Void voidResponse;

    @Mock
    private CompletableFuture<CustomerResponse> customerResponseAsync;

    @Mock
    private CompletableFuture<IdResponse> idAsync;

    @Mock
    private CompletableFuture<Void> voidAsync;

    private CustomersClient client;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        when(idResponse.getId()).thenReturn(CUSTOMER_ID);
        idAsync = CompletableFuture.completedFuture(idResponse);
        customerResponseAsync = CompletableFuture.completedFuture(customerResponse);
        voidAsync = CompletableFuture.completedFuture(voidResponse);
        client = new CustomersClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreateAndGetCustomer() throws ExecutionException, InterruptedException {
        doReturn(idAsync)
                .when(apiClient).postAsync(eq(CustomersClientImpl.CUSTOMERS_PATH), eq(authorization),
                eq(IdResponse.class), any(CustomerRequest.class), any());
        doReturn(customerResponseAsync)
                .when(apiClient)
                .getAsync(eq(CustomersClientImpl.CUSTOMERS_PATH + "/" + CUSTOMER_ID), eq(authorization),
                        eq(CustomerResponse.class));
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Customer")
                .phone(Phone.builder()
                        .countryCode("65")
                        .number("765432323")
                        .build())
                .build();
        when(customerResponse.getEmail()).thenReturn(customerRequest.getEmail());
        when(customerResponse.getName()).thenReturn(customerRequest.getName());
        when(customerResponse.getPhone()).thenReturn(customerRequest.getPhone());
        final String customerId = client.create(customerRequest).get().getId();
        assertNotNull(customerId);
        final CustomerResponse customerResponse = client.get(customerId).get();
        assertNotNull(customerResponse);
        assertEquals(customerRequest.getEmail(), customerResponse.getEmail());
        assertEquals(customerRequest.getName(), customerResponse.getName());
        assertEquals(customerRequest.getPhone(), customerResponse.getPhone());
        assertNull(customerResponse.getDefaultId());
    }

    @Test
    void shouldCreateAndUpdateCustomer() throws ExecutionException, InterruptedException {
        doReturn(idAsync)
                .when(apiClient).postAsync(eq(CustomersClientImpl.CUSTOMERS_PATH), eq(authorization),
                eq(IdResponse.class), any(CustomerRequest.class), any());
        doReturn(customerResponseAsync)
                .when(apiClient)
                .getAsync(eq(CustomersClientImpl.CUSTOMERS_PATH + "/" + CUSTOMER_ID), eq(authorization),
                        eq(CustomerResponse.class));
        doReturn(voidAsync)
                .when(apiClient)
                .patchAsync(eq(CustomersClientImpl.CUSTOMERS_PATH + "/" + CUSTOMER_ID), eq(authorization),
                        eq(Void.class), any(CustomerRequest.class), any());
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Customer")
                .phone(Phone.builder()
                        .countryCode("3")
                        .number("3455235233")
                        .build())
                .build();
        final String customerId = client.create(customerRequest).get().getId();
        assertNotNull(customerId);
        //Update Customer
        customerRequest.setEmail(TestHelper.generateRandomEmail());
        customerRequest.setName("Customer Changed");
        when(customerResponse.getEmail()).thenReturn(customerRequest.getEmail());
        when(customerResponse.getName()).thenReturn(customerRequest.getName());
        client.update(customerId, customerRequest).get();
        //Verify changes were applied
        final CustomerResponse customerDetailsResponse = client.get(customerId).get();
        assertNotNull(customerDetailsResponse);
        assertEquals(customerRequest.getName(), customerDetailsResponse.getName());
        assertEquals(customerRequest.getEmail(), customerDetailsResponse.getEmail());
    }

    @Test
    void shouldCreateAndEditCustomer() throws ExecutionException, InterruptedException {
        doReturn(idAsync)
                .when(apiClient).postAsync(eq(CustomersClientImpl.CUSTOMERS_PATH), eq(authorization),
                eq(IdResponse.class), any(CustomerRequest.class), any());
        doThrow(Mockito.mock(CheckoutApiException.class))
                .when(apiClient)
                .getAsync(eq(CustomersClientImpl.CUSTOMERS_PATH + "/" + CUSTOMER_ID), eq(authorization),
                        eq(CustomerResponse.class));
        doReturn(voidAsync)
                .when(apiClient)
                .deleteAsync(eq(CustomersClientImpl.CUSTOMERS_PATH + "/" + CUSTOMER_ID), eq(authorization));
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Customer")
                .phone(Phone.builder()
                        .countryCode("3")
                        .number("3455235233")
                        .build())
                .build();
        final String customerId = client.create(customerRequest).get().getId();
        assertNotNull(customerId);
        //Delete customer
        client.delete(customerId).get();
        //Verify customer does not exist
        try {
            client.get(customerId);
            fail();
        } catch (final CheckoutApiException ignore) {
            //do nothing
        }
    }
}