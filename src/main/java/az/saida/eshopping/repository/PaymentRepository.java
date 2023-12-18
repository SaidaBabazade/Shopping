package az.saida.eshopping.repository;

import az.saida.eshopping.entity.Customer;
import az.saida.eshopping.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository <Payment,Long>{

    List<Payment> findAllByActive(Integer active);

    Payment findPaymentByIdAndActive (Long id, Integer active);
}
