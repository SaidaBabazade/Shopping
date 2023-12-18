package az.saida.eshopping.repository;

import az.saida.eshopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product>findAllByActive( Integer active);

    Product findProductByIdAndActive(Long id, Integer active);
}
