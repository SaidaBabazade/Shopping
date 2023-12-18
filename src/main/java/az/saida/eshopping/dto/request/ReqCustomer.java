package az.saida.eshopping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data

public class ReqCustomer {

    private Long customerId;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private Date dob;
    @JsonProperty(value = "token")
    private  ReqToken reqToken;

}
