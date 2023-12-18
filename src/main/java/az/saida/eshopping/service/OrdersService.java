package az.saida.eshopping.service;

import az.saida.eshopping.dto.request.ReqOrders;
import az.saida.eshopping.dto.response.RespOrders;
import az.saida.eshopping.dto.response.Response;

import java.util.List;

public interface OrdersService {
    Response<List<RespOrders>> getOrdersListByCustomerId(Long customerId);

    Response createOrders(ReqOrders reqOrders);
}
