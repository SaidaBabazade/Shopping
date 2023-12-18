package az.saida.eshopping.service.impl;

import az.saida.eshopping.dto.request.ReqCategories;
import az.saida.eshopping.dto.response.*;
import az.saida.eshopping.entity.Brands;
import az.saida.eshopping.entity.Categories;
import az.saida.eshopping.entity.Product;
import az.saida.eshopping.enums.EnumAvailableStatus;
import az.saida.eshopping.exception.EshopException;
import az.saida.eshopping.exception.ExceptionConstants;
import az.saida.eshopping.repository.BrandsRepository;
import az.saida.eshopping.repository.CategoriesRepository;
import az.saida.eshopping.repository.ProductRepository;
import az.saida.eshopping.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private   final CategoriesRepository categoriesRepository;
     private   final BrandsRepository brandsRepository;

     private  final ProductRepository productRepository;

    @Override
    public Response<List<RespCategories>> getCategoriesList() {
        Response<List<RespCategories>> response = new Response<>();
        try {
            List<Categories> categoriesList = categoriesRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if(categoriesList.isEmpty()){
                throw  new EshopException(ExceptionConstants.CATEGORIES_NOT_FOUND, "Categories not found");
            }

            List<RespCategories> respCategoriesList= categoriesList.stream().map(this::mapping).collect(Collectors.toList());
            response.setT(respCategoriesList);
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
    public Response<RespCategories> getCategoriesById(Long categoriesId) {
        Response<RespCategories> response = new Response<>();
        try {
            if (categoriesId == null) {

                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
           Categories categories = categoriesRepository.findCategoriesByIdAndActive(categoriesId, EnumAvailableStatus.ACTIVE.value);
            if (categories == null) {
                throw new EshopException(ExceptionConstants.CATEGORIES_NOT_FOUND, "Categories not found");
            }

           RespCategories respCategories = mapping(categories);
            response.setT(respCategories);
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
    public Response addCategories(ReqCategories reqCategories) {
        Response response = new Response();
        try {
            String categoriesName = reqCategories.getCategoryName();
            if(categoriesName == null){
                throw  new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            Categories categories = Categories.builder().
                    categoriesName(categoriesName).build();
            categoriesRepository.save(categories);
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
    public Response updateCategories(ReqCategories reqCategories) {
        Response response = new Response<>();
        try {
            String categoriesName= reqCategories.getCategoryName();
            Long categoriesId =reqCategories.getCategoriesId();
            if( categoriesName == null || categoriesId == null){
                throw  new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            Categories categories = categoriesRepository.findCategoriesByIdAndActive( categoriesId,EnumAvailableStatus.ACTIVE.value);
            if(categories == null){
                throw new EshopException( ExceptionConstants.CATEGORIES_NOT_FOUND, "Categories not found");
            }
           categories.setCategoriesName(categoriesName);
           categoriesRepository.save(categories);
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
    public Response deleteCategories(Long categoriesId) {
        Response response = new Response();
        try {
            if (categoriesId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
           Categories  categories = categoriesRepository.findCategoriesByIdAndActive(categoriesId,EnumAvailableStatus.ACTIVE.value);
            if(categories == null){
                throw  new EshopException(ExceptionConstants.CATEGORIES_NOT_FOUND,"Categories not found");
            }
            categories.setActive(EnumAvailableStatus.DEACTIVE.value);
            categoriesRepository.save(categories);
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
    public Response createCategories(ReqCategories reqCategories) {
        Response  response = new Response<>();
        try {
            String categoriesName = reqCategories.getCategoryName();
            Long productId = reqCategories.getProductId();
            Long brandsId = reqCategories.getBrandsId();

            if(  categoriesName == null ||  brandsId == null || productId ==null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
             Brands brands = brandsRepository.findBrandsByIdAndActive(brandsId, EnumAvailableStatus.ACTIVE.value);
            if( brands == null){
                throw new EshopException(ExceptionConstants.BRANDS_NOT_FOUND, "Brands not found");
            }
            Product product = productRepository.findProductByIdAndActive(productId, EnumAvailableStatus.ACTIVE.value);
            if( product == null){
                throw  new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND,"Product not found");
            }
            Categories categories = Categories.builder().
                    categoriesName(categoriesName).
                    product(product).
                    brands(brands).build();
            categoriesRepository.save(categories);
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

    private  RespCategories mapping (Categories categories){
        RespProduct respProduct = RespProduct.builder()
                .productName(categories.getProduct().getProductName())
                .price(categories.getProduct().getPrice())
                .quantity(categories.getProduct().getQuantity())
                .build();
        RespBrands respBrands = RespBrands.builder()
                .brandsName(categories.getBrands().getBrandsName())
                .description(categories.getBrands().getDescription())
                .build();
        RespCategories respCategories = RespCategories.builder().
                categoryId(categories.getId()).
                categoryName(categories.getCategoriesName()).
                respProduct(respProduct).
                respBrands(respBrands)
                .build();
        return  respCategories;
    }
}
