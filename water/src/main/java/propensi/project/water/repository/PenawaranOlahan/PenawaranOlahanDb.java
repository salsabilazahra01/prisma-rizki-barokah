package propensi.project.water.repository.PenawaranOlahan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.water.model.PenjualanHasilOlahan.PenawaranOlahanModel;
import propensi.project.water.model.Transaksi.TransaksiModel;

@Repository
public interface PenawaranOlahanDb  extends JpaRepository<PenawaranOlahanModel, String> {
    Page<PenawaranOlahanModel> findAllByStatusOrderByTanggalDibuat(Integer status, Pageable pageable);

}
