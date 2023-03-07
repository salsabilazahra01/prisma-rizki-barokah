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
@Table(name = "item_penawaran_olahan")
public class ItemPenawaranOlahanModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private ItemPenawaranOlahanIdModel id;

    @MapsId("idItem")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item", nullable = false)
    private WarehouseModel item;

    @MapsId("idPenawaranOlahan")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "penawaran_olahan", nullable = false)
    private PenawaranOlahanModel penawaranOlahan;

    @NotNull
    @Column(name = "kuantitas", nullable = false)
    private int kuantitas;
}
