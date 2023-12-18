package az.saida.eshopping.service.impl;

import az.saida.eshopping.dto.request.ReqBrands;
import az.saida.eshopping.dto.response.RespBrands;
import az.saida.eshopping.dto.response.RespStatus;
import az.saida.eshopping.dto.response.Response;
import az.saida.eshopping.entity.Brands;
import az.saida.eshopping.entity.Customer;
import az.saida.eshopping.enums.EnumAvailableStatus;
import az.saida.eshopping.exception.EshopException;
import az.saida.eshopping.exception.ExceptionConstants;
import az.saida.eshopping.repository.BrandsRepository;
import az.saida.eshopping.service.BrandsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandsServiceImpl implements BrandsService {

      private  final BrandsRepository brandsRepository;

    @Override
    public Response<List<RespBrands>> getBrandsList() {
        Response<List<RespBrands>> response = new Response<>();
        try {
            List<Brands> brandsList = brandsRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if(brandsList.isEmpty()){
                throw  new EshopException(ExceptionConstants.BRANDS_NOT_FOUND, "Brands not found");
            }

            List<RespBrands> respBrandsList = brandsList.stream().map(this::mapping).collect(Collectors.toList());
            response.setT(respBrandsList);
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
    public Response<RespBrands> getBrandsById(Long brandsId) {
        Response<RespBrands> response = new Response<>();
        try {
            if (brandsId == null) {

                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Brands brands = brandsRepository.findBrandsByIdAndActive(brandsId, EnumAvailableStatus.ACTIVE.value);
            if (brands == null) {
                throw new EshopException(ExceptionConstants.BRANDS_NOT_FOUND, "Brands not found");
            }

            RespBrands respBrands = mapping(brands);
            response.setT(respBrands);
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
    public Response addBrands(ReqBrands reqBrands) {
        Response response = new Response();
        try {
            String brandsName = reqBrands.getBrandsName();
            String description = reqBrands.getDescription();
            if(brandsName == null ){
                throw  new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            Brands brands = Brands.builder().
                    brandsName(brandsName).
                    description(description).build();
           brandsRepository.save(brands);
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
    public Response updateBrands(ReqBrands reqBrands) {
        Response response = new Response<>();
        try {
            String brandsName = reqBrands.getBrandsName();
            String description = reqBrands.getDescription();
            Long brandsId =reqBrands.getBrandsId();
            if( brandsName == null ||  description == null ||brandsId == null){
                throw  new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            Brands brands = brandsRepository.findBrandsByIdAndActive( brandsId,EnumAvailableStatus.ACTIVE.value);
            if(brands == null){
                throw new EshopException( ExceptionConstants.BRANDS_NOT_FOUND, "Brands not found");
            }
            brands.setBrandsName(brandsName);
            brandsRepository.save(brands);
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
    public Response deleteBrands(Long brandsId) {
        Response response = new Response();
        try {
            if (brandsId== null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
           Brands  brands = brandsRepository.findBrandsByIdAndActive(brandsId,EnumAvailableStatus.ACTIVE.value);
            if(brands == null){
                throw  new EshopException(ExceptionConstants.BRANDS_NOT_FOUND,"Brands not found");
            }
            brands.setActive(EnumAvailableStatus.DEACTIVE.value);
            brandsRepository.save(brands);
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


    private  RespBrands mapping(Brands brands){
        RespBrands respBrands = RespBrands.builder().
                brandsId(brands.getId()).
               brandsName(brands.getBrandsName()).
                description(brands.getDescription())
                .build();
        return  respBrands;
    }
}
