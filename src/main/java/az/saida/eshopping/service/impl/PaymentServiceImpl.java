package az.saida.eshopping.service.impl;

import az.saida.eshopping.dto.request.ReqPayment;
import az.saida.eshopping.dto.response.*;
import az.saida.eshopping.entity.Categories;
import az.saida.eshopping.entity.Customer;
import az.saida.eshopping.entity.Payment;
import az.saida.eshopping.enums.EnumAvailableStatus;
import az.saida.eshopping.exception.EshopException;
import az.saida.eshopping.exception.ExceptionConstants;
import az.saida.eshopping.repository.CategoriesRepository;
import az.saida.eshopping.repository.CustomerRepository;
import az.saida.eshopping.repository.PaymentRepository;
import az.saida.eshopping.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private  final CustomerRepository customerRepository;
    private final CategoriesRepository categoriesRepository;

    @Override
    public Response<List<RespPayment>> getPaymentList() {

        Response<List<RespPayment>> response = new Response<>();
        try {
            List<Payment> paymentList = paymentRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (paymentList.isEmpty()) {
                throw new EshopException(ExceptionConstants.PAYMENT_NOT_FOUND, "Payment not found");
            }
            List<RespPayment> respPaymentList = paymentList.stream().map(this::mapping).collect(Collectors.toList());
            response.setT(respPaymentList);
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
    public Response createPayment(ReqPayment reqPayment) {
        Response response = new Response<>();
        try {
            Double amount = reqPayment.getAmount();
            Long customerId = reqPayment.getCustomerId();
            Long categoriesId = reqPayment.getCategoriesId();
            if (amount == null || customerId == null || categoriesId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null){
                throw  new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }

            Categories categories = categoriesRepository.findCategoriesByIdAndActive(categoriesId, EnumAvailableStatus.ACTIVE.value);
            if (categories == null) {
                throw new EshopException(ExceptionConstants.CATEGORIES_NOT_FOUND, "Categories not found");
            }

            Payment payment = Payment.builder()
                    .amount( amount)
                    .customer(customer)
                    .categories(categories)
                    .build();
            paymentRepository.save(payment);
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

    private RespPayment mapping(Payment payment) {
        RespCustomer  respCustomer = RespCustomer.builder()
                .name(payment.getCustomer().getName())
                .surname(payment.getCustomer().getSurname())
                .email(payment.getCustomer().getEmail())
                .dob(payment.getCustomer().getDob())
                .build();
        RespProduct  respProduct = RespProduct.builder()
                .productName(payment.getCategories().getProduct().getProductName())
                .price(payment.getCategories().getProduct().getPrice())
                .quantity(payment.getCategories().getProduct().getQuantity())
                .build();
         RespBrands respBrands = RespBrands.builder()
                 .brandsName(payment.getCategories().getBrands().getBrandsName())
                 .description(payment.getCategories().getBrands().getDescription())
                 .build();
        RespCategories respCategories = RespCategories.builder()
                .categoryName(payment.getCategories().getCategoriesName())
                .respProduct(respProduct)
                .respBrands(respBrands)
                .build();
        RespPayment respPayment = RespPayment.builder()
                .paymentId(payment.getId())
                .amount(payment.getAmount())
                .respCustomer(respCustomer)
                .respCategories(respCategories)
                .build();

        return respPayment;

    }
}