package propensi.project.water.repository.TransaksiDb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.water.model.Transaksi.TransaksiModel;

@Repository
public interface TransaksiDb extends JpaRepository<TransaksiModel, String> {
    Page<TransaksiModel> findAllByIdTransaksiIsNotNullOrderByTanggalDibuat(Pageable pageable);
    Page<TransaksiModel> findAllByJenisTransaksiOrderByTanggalDibuat(Boolean jenis, Pageable pageable);
    Page<TransaksiModel> findByIdTransaksiContainingIgnoreCaseOrderByTanggalDibuat(String keyword, Pageable pageable);
    Page<TransaksiModel> findByIdTransaksiContainingIgnoreCaseAndJenisTransaksiOrderByTanggalDibuat(Boolean jenis, String keyword, Pageable pageable);

}
