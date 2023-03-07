package propensi.project.water.model;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@JsonIgnoreProperties(value={""}, allowSetters = true)
@Table(name = "partner")
public class PartnerModel extends UserModel {
    @NotNull
    @Column(name= "nama_pic", nullable=false)
    private String namaPic;

    @NotNull
    @Column(name= "alamat", nullable=false)
    private String alamat;

    // relasi dengan penawaran sampah
    @OneToMany(mappedBy = "partner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PenawaranSampahModel> listPenawaranSampah;
}
