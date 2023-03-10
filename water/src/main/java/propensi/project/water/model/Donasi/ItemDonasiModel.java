package propensi.project.water.model.Donasi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import propensi.project.water.model.Warehouse.WarehouseModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private Integer kuantitas;
}
