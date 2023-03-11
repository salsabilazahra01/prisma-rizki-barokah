package propensi.project.water.model.User;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "teknisi")
@PrimaryKeyJoinColumn(name="usernameTeknisi")
public class TeknisiModel extends UserModel {
}
