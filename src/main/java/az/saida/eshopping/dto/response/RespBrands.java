package az.saida.eshopping.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespBrands {

    private Long brandsId;
    private String brandsName;
    private String description;
}
