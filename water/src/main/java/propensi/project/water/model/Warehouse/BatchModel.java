package propensi.project.water.model.Warehouse;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@JsonIgnoreProperties(value={""}, allowSetters = true)
@Table(name = "batch")
public class BatchModel implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(name = "id", nullable = false)
    private String idBatch;

    @NotNull
    @Column(name = "kuantitas_bahan_baku", nullable = false)
    private Integer kuantitasBahanBaku;

    @NotNull
    @Column(name = "kuantitas_hasil", nullable = false, columnDefinition = "int default 0")
    private Integer kuantitasHasil;

    @NotNull
    @Column(name = "tanggal_dibuat", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalDibuat;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    // relasi dengan warehouse
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_item", referencedColumnName = "id")
    private WarehouseModel warehouse;
}
