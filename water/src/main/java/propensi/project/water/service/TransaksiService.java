package propensi.project.water.service;

import propensi.project.water.model.Transaksi.ProsesLainModel;
import propensi.project.water.model.Transaksi.ProsesPenawaranOlahanModel;
import propensi.project.water.model.Transaksi.ProsesPenawaranSampahModel;
import propensi.project.water.model.Transaksi.TransaksiModel;

import java.util.List;

public interface TransaksiService {

    // retrieve data
    List<TransaksiModel> retrieveAllTransaksi();
    List<TransaksiModel> retrieveAllTransaksi(Boolean jenis);
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
