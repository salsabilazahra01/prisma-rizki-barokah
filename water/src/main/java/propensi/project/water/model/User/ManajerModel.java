package propensi.project.water.model.User;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "manajer")
@PrimaryKeyJoinColumn(name="usernameManajer")
public class ManajerModel extends UserModel {
}
