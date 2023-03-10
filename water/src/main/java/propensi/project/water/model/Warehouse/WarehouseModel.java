package propensi.project.water.model.Warehouse;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

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
    @Column(name = "kuantitas_sampah", nullable = false, columnDefinition = "int default 0")
    private Integer kuantitasSampah;

    @NotNull
    @Column(name = "kuantitas_olahan", nullable = false, columnDefinition = "int default 0" )
    private Integer kuantitasOlahan;

//    @NotNull
//    @Column(name = "harga_jual", nullable = false)
//    private int hargaJual;

    @NotNull
    @Column(name = "harga_beli", nullable = false)
    private Integer hargaBeli;

    @NotNull
    @Column(name = "jenis", nullable = false)
    private Integer jenisItem; // id item

    // relasi dengan batch
    @OneToOne(mappedBy = "warehouse")
    private BatchModel batch;
}
