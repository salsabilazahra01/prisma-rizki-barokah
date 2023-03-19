package propensi.project.water.service;

import propensi.project.water.model.Transaksi.ProsesLainModel;
import propensi.project.water.model.Transaksi.ProsesPenawaranOlahanModel;
import propensi.project.water.model.Transaksi.ProsesPenawaranSampahModel;
import propensi.project.water.model.Transaksi.TransaksiModel;

import java.util.List;

public interface TransaksiService {

    // add transaksi automatically (integrated with other features)
    void addTransaksi(TransaksiModel transaksi);

    // add transaksi manually
    void addTransaksiManual(ProsesLainModel transaksiManual);

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
