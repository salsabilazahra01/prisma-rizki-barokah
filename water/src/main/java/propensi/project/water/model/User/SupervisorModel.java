package propensi.project.water.model.User;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "supervisor")
@PrimaryKeyJoinColumn(name="usernameSupervisor")
public class SupervisorModel extends UserModel {
}
