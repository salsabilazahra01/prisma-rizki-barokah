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
@Table(name = "tukar_poin")
public class TukarPoinModel implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(name = "id", nullable = false)
    private String idTukarPoin;

    @NotNull
    @Column(name = "bentuk", nullable = false)
    private int bentukReward;

    @NotNull
    @Column(name = "jenis", nullable = false, unique = true)
    private String jenisReward; // id reward

    @NotNull
    @Column(name = "status", nullable = false)
    private boolean status;

    // relasi dengan donatur
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "username_donatur", referencedColumnName = "username")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DonaturModel donatur;

    // relasi dengan reward
    @OneToOne(mappedBy = "tukarPoin")
    private RewardModel reward;
}
