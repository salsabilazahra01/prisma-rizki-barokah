package propensi.project.water.model.Transaksi;

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

@Entity
@Table(name = "transaksi")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class TransaksiModel implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(name = "id_transaksi", nullable = false)
    private String idTransaksi;

//    @NotNull
//    @Size(max = 50)
//    @Column(name = "person", nullable = false)
//    private String person; // username customer

    @NotNull
    @Column(name = "jenis_transaksi", nullable = false)
    private Boolean jenisTransaksi; // pendapatan atau pengeluaran

    @NotNull
    @Column(name = "nominal", nullable = false)
    private Integer nominal;

    @NotNull
    @Column(name = "tanggal_transaksi", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalTransaksi;

    @Column(name = "keterangan", nullable = false)
    private String keterangan;

    @NotNull
    @Column(name = "proses", nullable = false)
    private Integer proses;
}
