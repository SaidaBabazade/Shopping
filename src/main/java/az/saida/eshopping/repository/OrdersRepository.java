package az.saida.eshopping.repository;


import az.saida.eshopping.entity.Customer;
import az.saida.eshopping.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAllByCustomerAndActive(Customer customer ,Integer active);

    Orders findOrdersByIdAndActive(Long ordersId, Integer active);
}
