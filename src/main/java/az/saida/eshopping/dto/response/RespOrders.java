package az.saida.eshopping.dto.response;

import az.saida.eshopping.entity.Customer;
import az.saida.eshopping.entity.Payment;
import az.saida.eshopping.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespOrders {

    private  Long ordersId;
    private  String ordersNumber;
    private RespCustomer respCustomer;
    private RespPayment respPayment;
}
