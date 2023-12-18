package az.saida.eshopping.Utility;

import az.saida.eshopping.dto.request.ReqToken;
import az.saida.eshopping.entity.User;
import az.saida.eshopping.entity.UserToken;
import az.saida.eshopping.enums.EnumAvailableStatus;
import az.saida.eshopping.exception.EshopException;
import az.saida.eshopping.exception.ExceptionConstants;
import az.saida.eshopping.repository.UserRepository;
import az.saida.eshopping.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Utility {
     private  final UserTokenRepository userTokenRepository;

     private  final UserRepository userRepository;


    public UserToken checkToken(ReqToken reqToken){
        Long userId = reqToken.getUserId();
        String token = reqToken.getToken();
        if(userId == null || token == null){
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
        }
        User user = userRepository.findUserByIdAndActive(userId,EnumAvailableStatus.ACTIVE.value);
        if(user== null){
            throw  new EshopException(ExceptionConstants.USER_NOT_FOUND,"User not found");
        }
        UserToken userToken = userTokenRepository.findUserTokenByUserAndTokenAndActive(user, token, EnumAvailableStatus.ACTIVE.value);
        if(userToken == null){
            throw  new EshopException(ExceptionConstants.INVALID_TOKEN,"Invalid token");
        }
        return userToken;
    }
}
