package az.saida.eshopping.dto.response;

import az.saida.eshopping.entity.Categories;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespPayment {
     private  Long paymentId;
     private  Double amount;
     private RespCustomer respCustomer;
     private RespCategories respCategories;
}
