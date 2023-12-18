package az.saida.eshopping.repository;

import az.saida.eshopping.entity.Brands;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandsRepository extends JpaRepository<Brands,Long> {

    List<Brands> findAllByActive(Integer active);

    Brands findBrandsByIdAndActive( Long id, Integer active);
}
