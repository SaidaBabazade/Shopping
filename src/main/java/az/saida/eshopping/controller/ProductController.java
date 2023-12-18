package az.saida.eshopping.controller;

import az.saida.eshopping.dto.request.ReqProduct;
import az.saida.eshopping.dto.response.RespProduct;
import az.saida.eshopping.dto.response.Response;
import az.saida.eshopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
@RequiredArgsConstructor
public class ProductController {

    public final ProductService productService;

    @GetMapping("/GetProductList")
    public Response<List<RespProduct>> getProductList(){

        return productService.getProductList();
    }
     @GetMapping("/GetProductById/{productId}")
    public  Response<RespProduct> getProductById(@PathVariable Long productId){
        return  productService.getProductById(productId);

    }
    @PostMapping("/AddProduct")
    public  Response addProduct(@RequestBody ReqProduct reqProduct){

        return  productService.addProduct(reqProduct);
    }

    @PutMapping("/UpdateProduct")
    public Response updateProduct(@RequestBody ReqProduct reqProduct){
        return  productService.updateProduct(reqProduct);
    }

    @PutMapping("/DeleteProduct /{productId}")
    public Response deleteProduct (@PathVariable Long productId){
        return  productService.deleteProduct(productId);
    }
}
