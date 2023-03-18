package propensi.project.water.repository.TransaksiDb;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.project.water.model.Transaksi.TransaksiModel;

import java.util.List;

public interface TransaksiDb extends JpaRepository<TransaksiModel, String> {
    List<TransaksiModel> findAllByJenisTransaksi(Boolean jenis);
}
