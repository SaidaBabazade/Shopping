package az.saida.eshopping.dto.request;

import lombok.Builder;
import lombok.Data;

@Data

public class ReqPayment {

    private  Long paymentId;
    private  Double amount;
    private  Long customerId;
    private  Long categoriesId;
}
