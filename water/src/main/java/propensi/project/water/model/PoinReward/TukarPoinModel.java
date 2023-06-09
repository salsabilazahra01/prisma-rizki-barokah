package propensi.project.water.model.PoinReward;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import propensi.project.water.controller.FileUploadUtil;
import propensi.project.water.model.PenjualanHasilOlahan.ItemPenawaranOlahanModel;
import propensi.project.water.model.StringPrefixedSequenceIdGenerator;
import propensi.project.water.model.Transaksi.ProsesPenawaranOlahanModel;
import propensi.project.water.model.Transaksi.ProsesTukarPoinModel;
import propensi.project.water.model.User.DonaturModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "tukar_poin")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TukarPoinModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tukar_poin_seq")
    @GenericGenerator(name = "tukar_poin_seq",
            strategy = "propensi.project.water.model.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "TP-")
            }
    )
    @Column(name = "id_tukar_poin", nullable = false)
    private String idTukarPoin;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Column(name = "tanggal_tukar_poin", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalDibuat;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_donatur", nullable = false)
    private String namaDonatur;

    @Column(name = "email")
    private String email;

    @Column(name = "hp")
    private String hp;

    @Column(name = "bank", nullable = false)
    private String bank;

    @Column(name = "noRekening", nullable = false)
    private String noRekening;

    @Column(name = "namaRekening", nullable = false)
    private String namaRekening;

    @Lob
    @NotNull
    @Column(name = "foto_rekening", nullable = false)
    private Blob fotoRekening;

    @Column(name = "alamat_pic")
    private String alamatDonatur;

    @NotNull
    @Column(name = "is_delivered")
    private Boolean isDelivered;

    @Column(name = "keterangan_tolak")
    private String keteranganTolak;

    @Lob
    @Column(name = "bukti_kirim")
    private Blob buktiKirim;

    // relasi dengan donatur
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "username_donatur", referencedColumnName = "username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DonaturModel donatur;

    //relasi dengan item reward
    @OneToMany(mappedBy = "idReward", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RewardTukarPoinModel> listReward;

    //relasi dengan item reward when done
    @OneToMany(mappedBy = "idRewardDone", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RewardTukarPoinDoneModel> listRewardDone;

    // relasi dengan transaksi
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_transaksi", referencedColumnName = "id_transaksi")
    private ProsesTukarPoinModel transaksiTukarPoin;

    public String statusStr() {
        if(status == 0){
            return "Menunggu Konfirmasi Perusahaan";
        } else if(status == 1){
            return "Diproses";
        } else if(status == 2){
            return "Menunggu Konfirmasi Penerimaan";
        } else if(status == 3){
            return "Selesai";
        } else {
            return "Batal";
        }
    }

    @Transient
    public String getBuktiKirimPath() {
        if (buktiKirim == null || idTukarPoin == null) return null;
        return "data:image/jpeg;base64," + FileUploadUtil.decodePicture(buktiKirim);
    }

    @Transient
    public String getFotoRekeningPath() {
        if (fotoRekening == null || idTukarPoin == null) return null;
        return "data:image/jpeg;base64," + FileUploadUtil.decodePicture(fotoRekening);
    }
}
