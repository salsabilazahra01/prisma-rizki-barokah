package propensi.project.water.model;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name = "bahan_baku", nullable = false)
    private String bahanBaku; // id item

    @NotNull
    @Column(name = "hasil", nullable = false)
    private String hasil; // id item

    @NotNull
    @Column(name = "kuantitas_bahan_baku", nullable = false)
    private int kuantitasBahanBaku;

    @NotNull
    @Column(name = "kuantitas_hasil", nullable = false)
    private int kuantitasHasil;

    @NotNull
    @Column(name = "tanggal_dibuat", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalDibuat;

    @NotNull
    @Column(name = "status", nullable = false)
    private int status;

    // relasi dengan warehouse
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_warehouse", referencedColumnName = "id")
    private WarehouseModel warehouse;
}
