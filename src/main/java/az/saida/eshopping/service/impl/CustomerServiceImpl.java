package az.saida.eshopping.service.impl;

import az.saida.eshopping.Utility.Utility;
import az.saida.eshopping.dto.request.ReqCustomer;
import az.saida.eshopping.dto.request.ReqToken;
import az.saida.eshopping.dto.response.RespCustomer;
import az.saida.eshopping.dto.response.RespStatus;
import az.saida.eshopping.dto.response.Response;
import az.saida.eshopping.entity.Customer;
import az.saida.eshopping.enums.EnumAvailableStatus;
import az.saida.eshopping.exception.EshopException;
import az.saida.eshopping.exception.ExceptionConstants;
import az.saida.eshopping.repository.CustomerRepository;
import az.saida.eshopping.repository.UserRepository;
import az.saida.eshopping.repository.UserTokenRepository;
import az.saida.eshopping.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

     private  final Utility utility;

        @Override
      public Response<List<RespCustomer>> getCustomerList(ReqToken reqToken) {
        Response<List<RespCustomer>> response = new Response<>();
        try {
            utility.checkToken(reqToken);
            List<Customer> customerList = customerRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (customerList.isEmpty()) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }

            List<RespCustomer> respCustomerList = customerList.stream().map(this::mapping).collect(Collectors.toList());
            response.setT(respCustomerList);
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
    public Response<RespCustomer> getCustomerById(ReqCustomer reqCustomer) {
        Response<RespCustomer> response = new Response<>();
        try {
            Long customerId = reqCustomer.getCustomerId();
            utility.checkToken(reqCustomer.getReqToken());
            if (customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            RespCustomer respCustomer = mapping(customer);
            response.setT(respCustomer);
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
    public Response addCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        try {
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
           ReqToken reqToken = reqCustomer.getReqToken() ;
           utility.checkToken(reqToken);
           if (name == null || surname == null ) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = Customer.builder().
                    name(name).
                    surname(surname).
                    phone(reqCustomer.getPhone()).
                    email(reqCustomer.getEmail()).
                    dob(reqCustomer.getDob()).
                    build();
           customerRepository.save(customer);
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
    public Response updateCustomer(ReqCustomer reqCustomer) {
       Response response = new Response();
       try {
           String name = reqCustomer.getName();
           String surname = reqCustomer.getSurname();
           Long customerId = reqCustomer.getCustomerId();
           ReqToken reqToken = reqCustomer.getReqToken();
           utility.checkToken(reqToken);
           if (name == null || surname == null || customerId == null) {
               throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
           }
          Customer customer = customerRepository.findCustomerByIdAndActive(customerId,EnumAvailableStatus.ACTIVE.value);
        if(customer == null){
            throw  new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND,"Customer not found");
        }
        customer.setName(name);
        customer.setSurname(surname);
        customer.setEmail(reqCustomer.getEmail());
        customer.setPhone(reqCustomer.getPhone());
        customer.setDob(reqCustomer.getDob());
        customerRepository.save(customer);
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

    @Override
    public Response deleteCustomer(ReqCustomer reqCustomer) {
         Response response = new Response();
        Long customerId = reqCustomer.getCustomerId();
        ReqToken reqToken = reqCustomer.getReqToken();
        utility.checkToken(reqToken);
        try {
             if (customerId == null) {
                 throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
             }
             Customer customer = customerRepository.findCustomerByIdAndActive(customerId,EnumAvailableStatus.ACTIVE.value);
             if(customer == null){
                 throw  new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND,"Customer not found");
             }
          customer.setActive(EnumAvailableStatus.DEACTIVE.value);
          customerRepository.save(customer);
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

    private RespCustomer mapping(Customer customer) {
        RespCustomer respCustomer = RespCustomer.builder().
                customerId(customer.getId()).
                name(customer.getName()).
                surname(customer.getSurname()).
                email(customer.getEmail()).
                dob(customer.getDob()).
                build();

        return respCustomer;
    }
}
