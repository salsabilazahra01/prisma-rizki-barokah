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
import propensi.project.water.model.StringPrefixedSequenceIdGenerator;
import propensi.project.water.model.User.DonaturModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
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
    @Builder.Default
    private Boolean status = false;

    // relasi dengan donatur
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "username_donatur", referencedColumnName = "username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DonaturModel donatur;

    // relasi dengan reward
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "reward", referencedColumnName = "jenis")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RewardModel reward;

    // Date created with default value
    @NotNull
    @Column(name = "tanggal", nullable = false)
    @Builder.Default
    private Timestamp tanggal = new Timestamp(System.currentTimeMillis());

    
    


    // relasi dengan billing, optional
    @OneToOne(mappedBy = "tukarPoin", fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
    private TukarPoinBillingModel billing;

    public String statusStr() {
        return status ? "Sudah Dikirim" : "Belum Dikirim";
    }
}
