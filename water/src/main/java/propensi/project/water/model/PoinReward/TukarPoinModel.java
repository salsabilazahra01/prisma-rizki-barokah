package propensi.project.water.model.PoinReward;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
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

@Entity
@Table(name = "tukar_poin")
@Setter
@Getter
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
    private Boolean status;

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
}
