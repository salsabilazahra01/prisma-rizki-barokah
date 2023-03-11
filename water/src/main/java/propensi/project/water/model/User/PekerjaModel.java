package propensi.project.water.model.User;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "pekerja")
@PrimaryKeyJoinColumn(name="usernamePekerja")
public class PekerjaModel extends UserModel {
}
