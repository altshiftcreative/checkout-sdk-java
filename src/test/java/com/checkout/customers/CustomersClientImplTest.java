package com.checkout.customers;

import com.checkout.ApiClient;
import com.checkout.CheckoutApiException;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.TestHelper;
import com.checkout.common.IdResponse;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomersClientImplTest {

    private static final String CUSTOMERS = "/customers";
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
    private CustomerDetailsResponse customerDetailsResponse;

    @Mock
    private IdResponse idResponse;

    @Mock
    private Void voidResponse;

    @Mock
    private CompletableFuture<CustomerDetailsResponse> customerDetailsAsync;

    @Mock
    private CompletableFuture<IdResponse> idAsync;

    @Mock
    private CompletableFuture<Void> voidAsync;

    private CustomersClient client;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        when(idResponse.getId()).thenReturn(CUSTOMER_ID);
        idAsync = CompletableFuture.completedFuture(idResponse);
        customerDetailsAsync = CompletableFuture.completedFuture(customerDetailsResponse);
        voidAsync = CompletableFuture.completedFuture(voidResponse);
        client = new CustomersClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreateAndGetCustomer() throws ExecutionException, InterruptedException {
        doReturn(idAsync)
                .when(apiClient).postAsync(eq(CUSTOMERS), eq(authorization),
                eq(IdResponse.class), any(CustomerRequest.class), any());
        doReturn(customerDetailsAsync)
                .when(apiClient)
                .getAsync(eq(CUSTOMERS + "/" + CUSTOMER_ID), eq(authorization),
                        eq(CustomerDetailsResponse.class));
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Customer")
                .phone(TestHelper.createPhone())
                .build();
        when(customerDetailsResponse.getEmail()).thenReturn(customerRequest.getEmail());
        when(customerDetailsResponse.getName()).thenReturn(customerRequest.getName());
        when(customerDetailsResponse.getPhone()).thenReturn(customerRequest.getPhone());
        final String customerId = client.create(customerRequest).get().getId();
        assertNotNull(customerId);
        final CustomerDetailsResponse customerDetailsResponse = client.get(customerId).get();
        assertNotNull(customerDetailsResponse);
        assertEquals(customerRequest.getEmail(), customerDetailsResponse.getEmail());
        assertEquals(customerRequest.getName(), customerDetailsResponse.getName());
        assertEquals(customerRequest.getPhone(), customerDetailsResponse.getPhone());
        assertNull(customerDetailsResponse.getDefaultId());
        assertTrue(customerDetailsResponse.getInstruments().isEmpty());
    }

    @Test
    void shouldCreateAndUpdateCustomer() throws ExecutionException, InterruptedException {
        doReturn(idAsync)
                .when(apiClient).postAsync(eq(CUSTOMERS), eq(authorization),
                eq(IdResponse.class), any(CustomerRequest.class), any());
        doReturn(customerDetailsAsync)
                .when(apiClient)
                .getAsync(eq(CUSTOMERS + "/" + CUSTOMER_ID), eq(authorization),
                        eq(CustomerDetailsResponse.class));
        doReturn(voidAsync)
                .when(apiClient)
                .patchAsync(eq(CUSTOMERS + "/" + CUSTOMER_ID), eq(authorization),
                        eq(Void.class), any(CustomerRequest.class), any());
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Customer")
                .phone(TestHelper.createPhone())
                .build();
        final String customerId = client.create(customerRequest).get().getId();
        assertNotNull(customerId);
        //Update Customer
        customerRequest.setEmail(TestHelper.generateRandomEmail());
        customerRequest.setName("Customer Changed");
        when(customerDetailsResponse.getEmail()).thenReturn(customerRequest.getEmail());
        when(customerDetailsResponse.getName()).thenReturn(customerRequest.getName());
        client.update(customerId, customerRequest).get();
        //Verify changes were applied
        final CustomerDetailsResponse customerDetailsResponse = client.get(customerId).get();
        assertNotNull(customerDetailsResponse);
        assertEquals(customerRequest.getName(), customerDetailsResponse.getName());
        assertEquals(customerRequest.getEmail(), customerDetailsResponse.getEmail());
    }

    @Test
    void shouldCreateAndEditCustomer() throws ExecutionException, InterruptedException {
        doReturn(idAsync)
                .when(apiClient).postAsync(eq(CUSTOMERS), eq(authorization),
                eq(IdResponse.class), any(CustomerRequest.class), any());
        doThrow(Mockito.mock(CheckoutApiException.class))
                .when(apiClient)
                .getAsync(eq(CUSTOMERS + "/" + CUSTOMER_ID), eq(authorization),
                        eq(CustomerDetailsResponse.class));
        doReturn(voidAsync)
                .when(apiClient)
                .deleteAsync(eq(CUSTOMERS + "/" + CUSTOMER_ID), eq(authorization));
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Customer")
                .phone(TestHelper.createPhone())
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