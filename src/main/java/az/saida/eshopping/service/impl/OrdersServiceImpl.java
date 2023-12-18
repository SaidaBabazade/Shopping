package az.saida.eshopping.service.impl;

import az.saida.eshopping.dto.request.ReqOrders;
import az.saida.eshopping.dto.response.*;
import az.saida.eshopping.entity.Customer;
import az.saida.eshopping.entity.Orders;
import az.saida.eshopping.entity.Payment;
import az.saida.eshopping.enums.EnumAvailableStatus;
import az.saida.eshopping.exception.EshopException;
import az.saida.eshopping.exception.ExceptionConstants;
import az.saida.eshopping.repository.CustomerRepository;
import az.saida.eshopping.repository.OrdersRepository;
import az.saida.eshopping.repository.PaymentRepository;
import az.saida.eshopping.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;

    private final CustomerRepository customerRepository;

    private  final PaymentRepository paymentRepository;

    @Override
    public Response<List<RespOrders>> getOrdersListByCustomerId(Long customerId) {
        Response<List<RespOrders>> response = new Response<>();
        try {
            if (customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            List<Orders> ordersList = ordersRepository.findAllByCustomerAndActive(customer, EnumAvailableStatus.ACTIVE.value);
            if (ordersList.isEmpty()) {
                throw new EshopException(ExceptionConstants.ORDERS_NOT_FOUND, "Orders not found");
            }
            List<RespOrders> respOrdersList = ordersList.stream().map(this:: mapping).collect(Collectors.toList());
           response.setT(respOrdersList);
           response.setStatus(RespStatus.getSuccessMessage());
            // orders list den melumatlari goturub geri qaytariq.
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
    public Response createOrders(ReqOrders reqOrders) {
        Response response = new Response<>();
        try {
             String  ordersNumber = reqOrders.getOrdersNumber();
             Long customerId = reqOrders.getCustomerId();
             Long paymentId = reqOrders.getPaymentId();

             if (ordersNumber == null || customerId == null || paymentId ==null){
                 throw  new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
             }
             Customer customer = customerRepository.findCustomerByIdAndActive(customerId,EnumAvailableStatus.ACTIVE.value);
              if(customer == null){
                  throw  new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND,"Customer not found");
              }
              Payment payment = paymentRepository.findPaymentByIdAndActive(paymentId,EnumAvailableStatus.ACTIVE.value);
              if (payment == null){
                  throw  new EshopException(ExceptionConstants.PAYMENT_NOT_FOUND,"Payment not found");
              }
              Orders orders = Orders.builder().
                      ordersNumber(ordersNumber)
                      .customer(customer)
                      .payment(payment).build();
               ordersRepository.save(orders);
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

    private  RespOrders mapping (Orders orders){
        RespCustomer  respCustomer = RespCustomer.builder()
                .name(orders.getCustomer().getName())
                .surname(orders.getCustomer().getSurname())
                .email(orders.getCustomer().getEmail())
                .dob(orders.getCustomer().getDob())
                .build();
        RespPayment respPayment = RespPayment.builder()
                .amount(orders.getPayment().getAmount())
                .build();
//        RespProduct  respProduct = RespProduct.builder()
//                .productName(orders.getCategories().getProduct().getProductName())
//                .price(orders.getCategories().getProduct().getPrice())
//                .quantity(orders.getCategories().getProduct().getQuantity())
//                .build();
//        RespBrands respBrands = RespBrands.builder()
//                .brandsName(orders.getCategories().getBrands().getBrandsName())
//                .description(orders.getCategories().getBrands().getDescription())
//                .build();
//        RespCategories respCategories = RespCategories.builder()
//                .categoryName(orders.getCategories().getCategoriesName())
//                .respProduct(respProduct)
//                .respBrands(respBrands)
//                .build();
        RespOrders respOrders = RespOrders.builder()
                .ordersNumber(orders.getOrdersNumber())
                .respCustomer(respCustomer)
                .respPayment(respPayment).build();
        return  respOrders;
    }

}
