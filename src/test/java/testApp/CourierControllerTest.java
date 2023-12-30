package testApp;

import com.skip.dish.Controller.CourierStatementController;
import com.skip.dish.Service.DeliveryTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CourierStatementController.class)
public class CourierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryTransactionService deliveryTransactionService;

    @Test
    public void testGetDeliveryTransactions() throws Exception {
        mockMvc.perform(get("/deliveryTransactions"))
                .andExpect(status().isOk());
        // Additional assertions as needed
    }
}
