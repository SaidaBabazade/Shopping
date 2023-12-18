package az.saida.eshopping.repository;

import az.saida.eshopping.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository  extends JpaRepository<Categories, Long> {
    List<Categories> findAllByActive (Integer active);

    Categories findCategoriesByIdAndActive(Long id, Integer active);
}
