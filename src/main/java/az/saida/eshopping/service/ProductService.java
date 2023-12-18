package az.saida.eshopping.service;

import az.saida.eshopping.dto.request.ReqProduct;
import az.saida.eshopping.dto.response.RespProduct;
import az.saida.eshopping.dto.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    Response<List<RespProduct>> getProductList();


    Response<RespProduct> getProductById(Long productId);

    Response addProduct(ReqProduct reqProduct);

    Response updateProduct(ReqProduct reqProduct);

    Response deleteProduct(Long productId);
}
