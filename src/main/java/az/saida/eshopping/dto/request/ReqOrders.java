package az.saida.eshopping.dto.request;

import az.saida.eshopping.entity.Product;
import lombok.Data;

@Data
public class ReqOrders {

    private  Long ordersId;
    private  String ordersNumber;
    private  Long customerId;
    private  Long paymentId;
}
