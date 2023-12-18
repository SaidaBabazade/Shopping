package az.saida.eshopping.service.impl;

import az.saida.eshopping.dto.request.ReqProduct;
import az.saida.eshopping.dto.response.*;
import az.saida.eshopping.entity.Customer;
import az.saida.eshopping.entity.Product;
import az.saida.eshopping.enums.EnumAvailableStatus;
import az.saida.eshopping.exception.EshopException;
import az.saida.eshopping.exception.ExceptionConstants;
import az.saida.eshopping.repository.CustomerRepository;
import az.saida.eshopping.repository.ProductRepository;
import az.saida.eshopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private  final ProductRepository productRepository;


    public Response<List<RespProduct>> getProductList(){
        Response<List<RespProduct>> response = new Response<>();
        //  List<RespProduct> respProductList = new ArrayList<>();
        try {
          List<Product> productList = productRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if(productList.isEmpty()){
                throw  new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND,"Product not found");
            }
            List<RespProduct> respProductList =productList.stream().map(this::mapping).collect(Collectors.toList());
            response.setT(respProductList);
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
    public Response<RespProduct> getProductById(Long productId) {
        Response<RespProduct> response = new Response<>();
        try {
            if(productId ==  null){
                throw  new EshopException( ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            Product product = productRepository.findProductByIdAndActive(productId,EnumAvailableStatus.ACTIVE.value);
            if(product == null){
                throw  new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            RespProduct respProduct = mapping(product);
            response.setT(respProduct);
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
    public Response addProduct(ReqProduct reqProduct) {
        Response response = new Response();

        try {
            String productName = reqProduct.getProductName();
            Integer quantity = reqProduct.getQuantity();
            Double price = reqProduct.getPrice();

            if (productName == null || quantity == null ||price == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }

            Product product = Product.builder().
                   productName(productName).
                    quantity(quantity).
                   price(price).
                    build();



            productRepository.save(product);
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
    public Response updateProduct(ReqProduct reqProduct) {
        Response response = new Response();
        try {
            String productName = reqProduct.getProductName();
            Integer quantity = reqProduct.getQuantity();
            Double price = reqProduct.getPrice();
            Long productId =reqProduct.getProductId();
            if( productName == null || price == null || quantity == null || productId == null){
                throw  new EshopException( ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Product product = productRepository.findProductByIdAndActive(  productId,EnumAvailableStatus.ACTIVE.value);
            if( product == null){
                throw  new EshopException( ExceptionConstants.PRODUCT_NOT_FOUND,"Product  not found");
            }
            product.setProductName(productName);
            product.setQuantity(quantity);
            product.setPrice(price);
            productRepository.save(product);
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
    public Response deleteProduct(Long productId) {

        Response response = new Response();
        try {
             if( productId == null){
                 throw new  EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");

             }
             Product product = productRepository.findProductByIdAndActive( productId, EnumAvailableStatus.ACTIVE.value);
              if(product == null) {
                  throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
              }
              product.setActive(EnumAvailableStatus.DEACTIVE.value);
              productRepository.save(product);
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

    private  RespProduct mapping(Product product){
        RespProduct respProduct = RespProduct.builder().
                productId(product.getId()).
                productName(product.getProductName()).
                quantity(product.getQuantity()).
                price(product.getPrice()).
                build();

        return  respProduct;
       }

}
