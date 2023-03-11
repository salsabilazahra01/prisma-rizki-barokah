package propensi.project.water.repository.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import propensi.project.water.model.User.SupervisorModel;

@Repository
public interface SupervisorDb extends JpaRepository<SupervisorModel, String> {
    SupervisorModel findByUsername(String username);
    List<SupervisorModel> findAll();
    void delete(SupervisorModel supervisor);
}
