package az.saida.eshopping.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EshopException extends  RuntimeException{
 private   Integer code;
    public EshopException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public   Integer getCode(){
        return  code;
    }
}
