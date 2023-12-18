package az.saida.eshopping.repository;

import az.saida.eshopping.entity.User;
import az.saida.eshopping.entity.UserToken;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken ,Long> {

    UserToken findUserTokenByUserAndTokenAndActive(User user,String Token,  Integer active);

}
