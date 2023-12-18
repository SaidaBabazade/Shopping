package az.saida.eshopping.service;

import az.saida.eshopping.dto.request.ReqPayment;
import az.saida.eshopping.dto.response.RespPayment;
import az.saida.eshopping.dto.response.RespProduct;
import az.saida.eshopping.dto.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PaymentService {
    Response<List<RespPayment>> getPaymentList();

    Response createPayment(ReqPayment reqPayment);
}
