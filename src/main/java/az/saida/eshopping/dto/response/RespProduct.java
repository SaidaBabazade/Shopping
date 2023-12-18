package az.saida.eshopping.dto.response;

import az.saida.eshopping.entity.Customer;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespProduct {

    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;

}
