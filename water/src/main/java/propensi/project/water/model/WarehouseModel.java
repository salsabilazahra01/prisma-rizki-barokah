package propensi.project.water.model;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@JsonIgnoreProperties(value={""}, allowSetters = true)
@Table(name = "warehouse")
public class WarehouseModel implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(name = "id", nullable = false)
    private String idItem;

    @NotNull
    @Size(max=50)
    @Column(name = "nama", nullable = false)
    private String namaItem;

    @NotNull
    @Column(name = "kuantitas_sampah", nullable = false)
    private int kuantitasSampah;

    @NotNull
    @Column(name = "kuantitas_olahan", nullable = false)
    private int kuantitasOlahan;

//    @NotNull
//    @Column(name = "harga_jual", nullable = false)
//    private int hargaJual;

    @NotNull
    @Column(name = "harga_beli", nullable = false)
    private int hargaBeli;

    @NotNull
    @Size(max = 50)
    @Column(name = "jenis", nullable = false)
    private String jenisItem; // id item

    // relasi dengan batch
    @OneToOne(mappedBy = "warehouse")
    private BatchModel batch;
}
