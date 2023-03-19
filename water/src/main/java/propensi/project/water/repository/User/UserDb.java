package propensi.project.water.repository.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import propensi.project.water.model.User.UserModel;

@Repository
public interface UserDb extends JpaRepository<UserModel, String> {
    UserModel findByUsername(String username);
    List<UserModel> findAll();
    void delete(UserModel user);

    Optional<UserModel> findByEmailHp(String email);
}
