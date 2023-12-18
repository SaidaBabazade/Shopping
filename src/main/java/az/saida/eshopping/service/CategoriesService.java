package az.saida.eshopping.service;

import az.saida.eshopping.dto.request.ReqCategories;
import az.saida.eshopping.dto.response.RespCategories;
import az.saida.eshopping.dto.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoriesService {
    Response<List<RespCategories>> getCategoriesList();

    Response<RespCategories> getCategoriesById(Long categoriesId);

    Response addCategories(ReqCategories reqCategories);

    Response updateCategories(ReqCategories reqCategories);

    Response deleteCategories(Long categoriesId);

    Response createCategories(ReqCategories reqCategories);
}
