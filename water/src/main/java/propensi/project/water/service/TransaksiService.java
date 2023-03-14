package propensi.project.water.service;

import propensi.project.water.model.Transaksi.TransaksiModel;

import java.util.List;

public interface TransaksiService {

    public List<TransaksiModel> retrieveAllTransaksi();

    public List<TransaksiModel> retrieveAllTransaksi(String jenis);

}
