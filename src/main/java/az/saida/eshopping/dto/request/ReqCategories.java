package az.saida.eshopping.dto.request;


import lombok.Data;

@Data

public class ReqCategories {

    private  Long categoriesId;
    private String categoryName;
    private  Long  productId;
    private Long brandsId;

}
