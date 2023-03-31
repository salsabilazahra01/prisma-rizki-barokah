package propensi.project.water.model.PoinReward;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;
import propensi.project.water.model.StringPrefixedSequenceIdGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "reward")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RewardModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reward_seq")
    @GenericGenerator(name = "reward_seq",
            strategy = "propensi.project.water.model.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "RWD-")
            }
    )
    @Column(name = "id_reward", nullable = false)
    private String idReward;

    @NotNull
    @Column(name = "jenis", nullable = false, unique = true)
    private String jenisReward; // id reward

    @NotNull
    @Column(name = "poin", nullable = false)
    private int poin;

    // relasi dengan tukar poin
    @OneToMany(mappedBy = "reward", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TukarPoinModel> listTukarPoin;
}
