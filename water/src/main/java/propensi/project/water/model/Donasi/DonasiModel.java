package propensi.project.water.model.Donasi;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import propensi.project.water.model.User.DonaturModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "donasi")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DonasiModel implements Serializable {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(name = "id_donasi", nullable = false)
    private String idDonasi;

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
    private Integer status;

    @Column(name = "keterangan", nullable = false)
    private String keterangan;

    @NotNull
    @Column(name = "berat_sebelum", nullable = false)
    private Integer beratSebelum;

    @NotNull
    @Column(name = "berat_setelah", nullable = false, columnDefinition = "int default -1")
    private Integer beratSetelah;

    @NotNull
    @Column(name = "poin_earned", nullable = false, columnDefinition = "int default -1")
    private Integer poinEarned;

    // relasi dengan donatur
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "username_donatur", referencedColumnName = "username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DonaturModel donatur;

    // relasi dengan item
//    @OneToMany(mappedBy = "donasi", cascade = CascadeType.ALL)
//    List<ItemDonasiModel> itemDonasi = new ArrayList<ItemDonasiModel>();
}
