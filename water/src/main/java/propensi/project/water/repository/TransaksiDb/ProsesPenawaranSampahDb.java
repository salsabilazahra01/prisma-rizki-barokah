package propensi.project.water.repository.TransaksiDb;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.project.water.model.Transaksi.ProsesPenawaranSampahModel;

import java.util.Optional;

public interface ProsesPenawaranSampahDb extends JpaRepository<ProsesPenawaranSampahModel, String> {
    Optional<ProsesPenawaranSampahModel> findProsesPenawaranSampahModelByIdTransaksi(String id);

}
