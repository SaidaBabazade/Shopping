package az.saida.eshopping.service;

import az.saida.eshopping.dto.request.ReqLogin;
import az.saida.eshopping.dto.request.ReqToken;
import az.saida.eshopping.dto.response.RespUser;
import az.saida.eshopping.dto.response.Response;

public interface UserService {
    Response<RespUser> login(ReqLogin reqLogin);

    Response logout(ReqToken reqToken);
}
