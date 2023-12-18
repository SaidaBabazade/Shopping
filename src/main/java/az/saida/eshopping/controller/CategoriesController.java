package az.saida.eshopping.controller;

import az.saida.eshopping.dto.request.ReqCategories;
import az.saida.eshopping.dto.response.RespCategories;
import az.saida.eshopping.dto.response.Response;
import az.saida.eshopping.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor

public class CategoriesController {

     private  final CategoriesService categoriesService;

     @GetMapping("/GetCategoriesList")
    public Response<List<RespCategories>> getCategoriesList(){
        return categoriesService.getCategoriesList();
    }

    @GetMapping("/GetCategoriesById/{categoriesId}")
     public  Response <RespCategories> getCategoriesById(@PathVariable Long  categoriesId){
         return  categoriesService.getCategoriesById(categoriesId);
    }

    @PostMapping("/AddCategories")
    public Response addCategories(@RequestBody ReqCategories reqCategories) {
        return categoriesService.addCategories(reqCategories);

    }

    @PostMapping("/CreateCategories")
    public Response createCategories(@RequestBody ReqCategories reqCategories){
         return  categoriesService.createCategories(reqCategories);
    }

    @PutMapping("/UpdateCategories")
    public Response updateCategories(@RequestBody ReqCategories reqCategories){
         return  categoriesService.updateCategories(reqCategories);
    }

   @PutMapping("/DeleteCategories/{categoriesId}")
      public  Response deleteCategories(@PathVariable Long categoriesId){
         return  categoriesService.deleteCategories( categoriesId);
   }
}
