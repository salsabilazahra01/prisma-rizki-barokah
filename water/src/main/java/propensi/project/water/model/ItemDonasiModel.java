package propensi.project.water.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item_donasi")
public class ItemDonasiModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private ItemDonasiIdModel id;

    @MapsId("idItem")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item", nullable = false)
    private WarehouseModel item;

    @MapsId("idDonasi")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "donasi", nullable = false)
    private DonasiModel donasi;

    @NotNull
    @Column(name = "kuantitas", nullable = false)
    private int kuantitas;
}
