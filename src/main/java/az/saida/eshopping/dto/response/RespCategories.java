package az.saida.eshopping.dto.response;

import az.saida.eshopping.entity.Brands;
import az.saida.eshopping.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespCategories {
    private Long categoryId;
    private String categoryName;
    private RespProduct respProduct;
    private RespBrands respBrands;
}
