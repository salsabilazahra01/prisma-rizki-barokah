package propensi.project.water.model.Transaksi;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import propensi.project.water.model.PenjualanHasilOlahan.PenawaranOlahanModel;
import propensi.project.water.model.PembelianSampah.PenawaranSampahModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@JsonIgnoreProperties(value={""}, allowSetters = true)
@Table(name = "transaksi")
public class TransaksiModel implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(name = "id", nullable = false)
    private String idTransaksi;

    @NotNull
    @Size(max = 50)
    @Column(name = "person", nullable = false)
    private String person; // username customer

    @NotNull
    @Size(max = 50)
    @Column(name = "proses", nullable = false)
    private String proses; // id penawaran -> kl input manual?

    @NotNull
    @Column(name = "jenis_transaksi", nullable = false)
    private Boolean jenisTransaksi;

    @NotNull
    @Column(name = "nominal", nullable = false)
    private Integer nominal;

    @NotNull
    @Column(name = "tanggal_transaksi", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalTransaksi;

    @Column(name = "keterangan", nullable = false)
    private String keterangan;

    // relasi dengan penawaran sampah
    @OneToOne(mappedBy = "transaksiSampah")
    private PenawaranSampahModel penawaranSampah;

    // relasi dengan penawaran olahan
    @OneToOne(mappedBy = "transaksiOlahan")
    private PenawaranOlahanModel penawaranOlahan;
}
