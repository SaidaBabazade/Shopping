package az.saida.eshopping.dto.request;

import lombok.Data;

@Data


public class ReqProduct {

    private  Long productId;
    private String productName;
    private Integer quantity;
    private Double price;

}
