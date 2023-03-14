package propensi.project.water.repository.Transaksi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.water.model.Transaksi.TransaksiModel;

@Repository
public interface TransaksiDb extends JpaRepository<TransaksiModel, String> {
}
