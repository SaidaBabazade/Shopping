package az.saida.eshopping.controller;

import az.saida.eshopping.dto.request.ReqBrands;
import az.saida.eshopping.dto.response.RespBrands;
import az.saida.eshopping.dto.response.Response;
import az.saida.eshopping.service.BrandsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandsController {

     private  final BrandsService brandsService;

     @GetMapping("/GetBrandsList")
    public Response<List<RespBrands>> getBrandsList(){
        return   brandsService. getBrandsList();
    }


    @GetMapping("/GetBrandsById/{brandsId}")
    public Response <RespBrands> getBrandsById(@PathVariable Long brandsId){
         return  brandsService.getBrandsById(brandsId);
    }

    @PostMapping("/AddBrands")
    public  Response  addBrands(@RequestBody ReqBrands reqBrands){
         return  brandsService.addBrands(reqBrands);
    }

    @PutMapping("/UpdateBrands")
    public  Response updateBrands(@RequestBody ReqBrands reqBrands){
         return  brandsService.updateBrands(reqBrands);

    }

    @PutMapping("/DeleteBrands/{brandsId}")
     public  Response deleteBrands (@PathVariable Long brandsId){
         return brandsService.deleteBrands(brandsId);
    }
}
