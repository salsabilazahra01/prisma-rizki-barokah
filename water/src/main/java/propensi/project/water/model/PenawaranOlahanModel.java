package propensi.project.water.model;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
@Table(name = "penawaran_olahan")
public class PenawaranOlahanModel implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(name = "id", nullable = false)
    private String idPenawaranOlahan;

    @NotNull
    @Size(max = 50)
    @Column(name = "person", nullable = false)
    private String person; // username customer

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_pic", nullable = false)
    private String namaPic;

    @NotNull
    @Column(name = "kontak_pic", nullable = false)
    private String kontakPic;

    @NotNull
    @Column(name = "alamat_pic", nullable = false)
    private String alamatPic;

    @NotNull
    @Column(name = "is_picked_up", nullable = false)
    private Boolean isPickedUp;

    @NotNull
    @Column(name = "tanggal_dibuat", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalDibuat;

    @NotNull
    @Column(name = "status", nullable = false)
    private int status;

    @NotNull
    @Column(name = "keterangan", nullable = false)
    private String keterangan;

    @NotNull
    @Column(name = "harga", nullable = false)
    private int harga;

    // relasi dengan customer
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "username_customer", referencedColumnName = "username")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerModel customer;

    // relasi dengan transaksi
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_transaksi", referencedColumnName = "id")
    private TransaksiModel transaksiOlahan;
}
