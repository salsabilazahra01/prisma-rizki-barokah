package propensi.project.water.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import propensi.project.water.model.Transaksi.ProsesLainModel;
import propensi.project.water.model.Transaksi.ProsesPenawaranOlahanModel;
import propensi.project.water.model.Transaksi.ProsesPenawaranSampahModel;
import propensi.project.water.model.Transaksi.TransaksiModel;

public interface TransaksiService {

    // add transaksi automatically (integrated with other features)
    void addTransaksi(TransaksiModel transaksi);

    // add transaksi manually
    void addTransaksiManual(ProsesLainModel transaksiManual);
    Page<TransaksiModel> retrieveAllTransaksi(Pageable paging, Boolean jenis);
    Page<TransaksiModel> retrieveAllTransaksiIdContaining(String keyword, Pageable paging, Boolean jenis);
    TransaksiModel retrieveTransaksiById(String id);
    ProsesPenawaranSampahModel getTransaksiPenawaranSampah(String idTransaksi);
    ProsesPenawaranOlahanModel getTransaksiPenawaranOlahan(String idTransaksi);
    ProsesLainModel getTransaksiLain(String idTransaksi);

    //delete
    void delete(TransaksiModel transaksi);

    //update
    TransaksiModel updateTransaksiSampahOlahan(TransaksiModel transaksi);
    ProsesLainModel updateTransaksiProsesLain(ProsesLainModel transaksi);

}
