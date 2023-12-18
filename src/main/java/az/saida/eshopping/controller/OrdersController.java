package az.saida.eshopping.controller;

import az.saida.eshopping.dto.request.ReqOrders;
import az.saida.eshopping.dto.response.RespOrders;
import az.saida.eshopping.dto.response.Response;
import az.saida.eshopping.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/GetOrdersListByCustomerId/{customerId}")
    public Response<List<RespOrders>> getOrdersListByCustomerId(@PathVariable Long customerId){
         return  ordersService.getOrdersListByCustomerId(customerId);
    }

    @PostMapping("/CreateOrders")
    public  Response createOrders(@RequestBody ReqOrders reqOrders){

        return  ordersService.createOrders(reqOrders);
    }

}
