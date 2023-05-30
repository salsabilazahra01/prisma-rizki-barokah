package propensi.project.water.model.PoinReward;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import propensi.project.water.controller.FileUploadUtil;
import propensi.project.water.model.PenjualanHasilOlahan.ItemPenawaranOlahanIdModel;
import propensi.project.water.model.PenjualanHasilOlahan.PenawaranOlahanModel;
import propensi.project.water.model.StringPrefixedSequenceIdGenerator;
import propensi.project.water.model.Transaksi.ProsesTukarPoinModel;
import propensi.project.water.model.User.DonaturModel;
import propensi.project.water.model.Warehouse.WarehouseModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reward_tukar_poin_done")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardTukarPoinDoneModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reward_tukar_poin_done_seq")
    @GenericGenerator(name = "reward_tukar_poin_done_seq",
            strategy = "propensi.project.water.model.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "RTPD-")
            }
    )
    @Column(name = "id_reward_done", nullable = false)
    private String idRewardDone;

    @NotNull
    @Column(name = "jenis_reward", nullable = false)
    private String jenisReward;

    @NotNull
    @Column(name = "poin", nullable = false)
    private Integer poin;

    @NotNull
    @Column(name = "jumlah", nullable = false)
    private Integer jumlah;

    @NotNull
    @Column(name = "poin_ditukar", nullable = false)
    private Integer poinDitukar;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tukar_poin", referencedColumnName = "id_tukar_poin", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TukarPoinModel idTukarPoin;

}
