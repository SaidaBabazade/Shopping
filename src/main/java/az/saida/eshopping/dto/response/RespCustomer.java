package az.saida.eshopping.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RespCustomer {

 private  Long customerId;
 private String name;
 private String  surname;
 private String email;
 private Date dob;

}
