package propensi.project.water.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ItemPenawaranSampahIdModel implements Serializable {
    private static final long serialVersionUID = -3182763128764187L;

    @NotNull
    @Column(name = "id_item", nullable = false)
    private String idItem; // sampah

    @NotNull
    @Column(name = "id_penawaran_sampah", nullable = false)
    private String idPenawaranSampah;
}
