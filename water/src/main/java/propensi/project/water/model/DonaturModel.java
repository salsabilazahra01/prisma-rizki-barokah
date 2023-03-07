package propensi.project.water.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donatur")
public class DonaturModel extends UserModel {
    @NotNull
    @Column(name="poin", nullable=false, columnDefinition = "int default 0")
    private int poin;

    @NotNull
    @Column(name="alamat", nullable=false)
    private String alamat;

    // relasi dengan donasi
    @OneToMany(mappedBy = "donatur", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DonasiModel> listDonasi;

    // relasi dengan tukar poin
    @OneToMany(mappedBy = "donatur", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TukarPoinModel> listTukarPoin;
}
