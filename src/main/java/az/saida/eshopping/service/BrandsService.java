package az.saida.eshopping.service;

import az.saida.eshopping.dto.request.ReqBrands;
import az.saida.eshopping.dto.response.RespBrands;
import az.saida.eshopping.dto.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BrandsService {
    Response<List<RespBrands>> getBrandsList();

    Response<RespBrands> getBrandsById(Long brandsId);

    Response addBrands(ReqBrands reqBrands);

    Response updateBrands(ReqBrands reqBrands);

    Response deleteBrands(Long brandsId);
}
