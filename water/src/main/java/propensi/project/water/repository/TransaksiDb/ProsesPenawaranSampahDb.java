package propensi.project.water.repository.TransaksiDb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.water.model.PembelianSampah.PenawaranSampahModel;
import propensi.project.water.model.Transaksi.ProsesPenawaranSampahModel;

import java.util.Optional;

@Repository
public interface ProsesPenawaranSampahDb extends JpaRepository<ProsesPenawaranSampahModel, String> {
    Optional<ProsesPenawaranSampahModel> findProsesPenawaranSampahModelByIdTransaksi(String id);
    Optional<ProsesPenawaranSampahModel> findProsesPenawaranSampahModelByPenawaranSampah(PenawaranSampahModel penawaranSampahModel);

}
