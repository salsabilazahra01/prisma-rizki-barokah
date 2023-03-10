package propensi.project.water.model.PoinReward;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@JsonIgnoreProperties(value={""}, allowSetters = true)
@Table(name = "reward")
public class RewardModel implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(name = "id", nullable = false)
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
