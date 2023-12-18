package az.saida.eshopping.service;

import az.saida.eshopping.dto.request.ReqCustomer;
import az.saida.eshopping.dto.request.ReqToken;
import az.saida.eshopping.dto.response.RespCustomer;
import az.saida.eshopping.dto.response.Response;

import java.util.List;

public interface CustomerService {

    Response<List<RespCustomer>> getCustomerList(ReqToken reqToken) ;

    Response<RespCustomer> getCustomerById(ReqCustomer reqCustomer);

    Response addCustomer(ReqCustomer reqCustomer);

    Response updateCustomer(ReqCustomer reqCustomer);

    Response deleteCustomer(ReqCustomer reqCustomer);
}
