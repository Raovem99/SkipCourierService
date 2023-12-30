package Service;

import com.skip.dish.Service.DeliveryTransactionService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeliveryTransactionServiceTest {
    private final DeliveryTransactionService deliveryTransactionService = mock(DeliveryTransactionService.class);

    @Test
    public void testCalculateWeeklyStatement() {
        // Setup
        when(deliveryTransactionService.getWeeklyStatementForCourier(String.valueOf(anyInt()))).thenReturn(105);

        // Action
        double statement = deliveryTransactionService.getWeeklyStatementForCourier(String.valueOf(1)).getValue();

        // Assertion
        assertEquals(100.0, statement, "The weekly statement should be 100.0");
    }
}

