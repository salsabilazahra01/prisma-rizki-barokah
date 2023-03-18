package propensi.project.water.repository.TransaksiDb;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.project.water.model.Transaksi.ProsesPenawaranOlahanModel;

import java.util.Optional;

public interface ProsesPenawaranOlahanDb extends JpaRepository<ProsesPenawaranOlahanModel, String> {
    Optional<ProsesPenawaranOlahanModel> findProsesPenawaranOlahanModelByIdTransaksi(String id);

}
