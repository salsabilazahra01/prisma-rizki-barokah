package propensi.project.water.repository.TransaksiDb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import propensi.project.water.model.Transaksi.TransaksiModel;
public interface TransaksiDb extends JpaRepository<TransaksiModel, String> {
    Page<TransaksiModel> findAll(Pageable pageable);
    Page<TransaksiModel> findAllByJenisTransaksi(Boolean jenis, Pageable pageable);
    Page<TransaksiModel> findByIdTransaksiContainingIgnoreCase(String keyword, Pageable pageable);
    Page<TransaksiModel> findByIdTransaksiContainingIgnoreCaseAndJenisTransaksi(Boolean jenis, String keyword, Pageable pageable);

}
