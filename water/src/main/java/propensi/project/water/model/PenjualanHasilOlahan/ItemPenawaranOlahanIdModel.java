package propensi.project.water.model.PenjualanHasilOlahan;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ItemPenawaranOlahanIdModel implements Serializable {
    private static final long serialVersionUID = -3182763128764187L;

    @NotNull
    @Column(name = "id_item", nullable = false)
    private String idItem; // sampah

    @NotNull
    @Column(name = "id_penawaran_olahan", nullable = false)
    private String idPenawaranOlahan;
}
