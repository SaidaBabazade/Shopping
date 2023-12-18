package az.saida.eshopping.controller;

import az.saida.eshopping.dto.request.ReqCategories;
import az.saida.eshopping.dto.request.ReqPayment;
import az.saida.eshopping.dto.response.RespCustomer;
import az.saida.eshopping.dto.response.RespPayment;
import az.saida.eshopping.dto.response.Response;
import az.saida.eshopping.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payment")
@RequiredArgsConstructor

public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/GetPaymentList")
    public Response<List<RespPayment>> getPaymentList() {

        return paymentService.getPaymentList();
    }

    @PostMapping("/CreatePayment")
    public Response createPayment(@RequestBody ReqPayment reqPayment){
        return  paymentService.createPayment(reqPayment);
    }


}
