package az.saida.eshopping.service.impl;

import az.saida.eshopping.Utility.Utility;
import az.saida.eshopping.dto.request.ReqLogin;
import az.saida.eshopping.dto.request.ReqToken;
import az.saida.eshopping.dto.response.RespStatus;
import az.saida.eshopping.dto.response.RespToken;
import az.saida.eshopping.dto.response.RespUser;
import az.saida.eshopping.dto.response.Response;
import az.saida.eshopping.entity.User;
import az.saida.eshopping.entity.UserToken;
import az.saida.eshopping.enums.EnumAvailableStatus;
import az.saida.eshopping.exception.EshopException;
import az.saida.eshopping.exception.ExceptionConstants;
import az.saida.eshopping.repository.UserRepository;
import az.saida.eshopping.repository.UserTokenRepository;
import az.saida.eshopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private  final Utility utility;

    private  final UserRepository userRepository;

    private  final UserTokenRepository userTokenRepository;

    @Override
    public Response<RespUser> login(ReqLogin reqLogin) {
        Response<RespUser> response = new Response<>();
        RespUser respUser = new RespUser();
     try {
       String username = reqLogin.getUsername();
       String password = reqLogin.getPassword();
       if(username == null || password == null){
           throw  new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
       }
      User user = userRepository.findUserByUsernameAndPasswordAndActive(username,password, EnumAvailableStatus.ACTIVE.value);
        if (user == null){
            throw new EshopException(ExceptionConstants.USER_NOT_FOUND,"User not found");
        }
        String token = UUID.randomUUID().toString();
         UserToken userToken = UserToken.builder()
                 .user(user)
                 .token(token)
                 .build();
         userTokenRepository.save(userToken);
           respUser.setUsername(username);
          respUser.setRespToken(new RespToken(user.getId(),token));
          response.setT(respUser);
          response.setStatus(RespStatus.getSuccessMessage());
     } catch (EshopException ex) {
         response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
         ex.printStackTrace();
     } catch (Exception ex) {
         response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception"));
         ex.printStackTrace();
     }
        return response;
    }

    @Override
    public Response logout(ReqToken reqToken) {
        Response response = new Response();
      try{
         UserToken userToken = utility.checkToken(reqToken);
         userToken.setActive(EnumAvailableStatus.DEACTIVE.value);
         userTokenRepository.save(userToken);
         response.setStatus(RespStatus.getSuccessMessage());
      }catch (EshopException ex) {
          response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
          ex.printStackTrace();
      } catch (Exception ex) {
          response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception"));
          ex.printStackTrace();
      }
        return response;
    }
}
