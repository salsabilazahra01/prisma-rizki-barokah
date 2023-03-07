package propensi.project.water.model;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tukar_poin", referencedColumnName = "id")
    private TukarPoinModel tukarPoin;
}
