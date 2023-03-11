package propensi.project.water.model.PembelianSampah;

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
@Table(name = "item_penawaran_sampah")
public class ItemPenawaranSampahModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private ItemPenawaranSampahIdModel id;

    @MapsId("idItem")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item", nullable = false)
    private WarehouseModel item;

    @MapsId("idPenawaranSampah")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "penawaran_sampah", nullable = false)
    private PenawaranSampahModel penawaranSampah;

    @NotNull
    @Column(name = "kuantitas", nullable = false)
    private Integer kuantitas;
}
