package propensi.project.water.repository.TransaksiDb;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.project.water.model.Transaksi.ProsesLainModel;

import java.util.Optional;

public interface ProsesLainDb extends JpaRepository<ProsesLainModel, String> {
    Optional<ProsesLainModel> findProsesLainModelByIdTransaksi(String id);

}
