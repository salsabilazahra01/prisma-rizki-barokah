package propensi.project.water.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import propensi.project.water.model.PenjualanHasilOlahan.PenawaranOlahanModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name="usernameCustomer")
public class CustomerModel extends UserModel {
    @NotNull
    @Column(name="nama_pic", nullable=false)
    private String namaPic;

    @NotNull
    @Column(name="alamat", nullable=false)
    private String alamat;

    // relasi dengan penawaran olahan
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PenawaranOlahanModel> listPenawaranOlahan;
}
