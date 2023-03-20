package propensi.project.water.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.project.water.model.Transaksi.ProsesLainModel;
import propensi.project.water.model.Transaksi.ProsesPenawaranOlahanModel;
import propensi.project.water.model.Transaksi.ProsesPenawaranSampahModel;
import propensi.project.water.model.Transaksi.TransaksiModel;
import propensi.project.water.repository.TransaksiDb.ProsesPenawaranOlahanDb;
import propensi.project.water.repository.TransaksiDb.ProsesPenawaranSampahDb;
import propensi.project.water.repository.TransaksiDb.TransaksiDb;
import propensi.project.water.service.TransaksiService;
import propensi.project.water.repository.TransaksiDb.ProsesLainDb;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransaksiServiceImpl implements TransaksiService {
    @Autowired
    private TransaksiDb transaksiDb;

    @Autowired
    private ProsesLainDb prosesLainDb;

    @Autowired
    private ProsesPenawaranSampahDb prosesPenawaranSampahDb;

    @Autowired
    private ProsesPenawaranOlahanDb prosesPenawaranOlahanDb;

    @Override
    public void addTransaksi(TransaksiModel transaksi) {
        transaksiDb.save(transaksi);
    }

    @Override
    public void addTransaksiManual (ProsesLainModel transaksiManual) {
        prosesLainDb.save(transaksiManual);
    }

    @Override
    public List<TransaksiModel> retrieveAllTransaksi(){
        return transaksiDb.findAll();
    }

    @Override
    public List<TransaksiModel> retrieveAllTransaksi(Boolean jenis){
        return transaksiDb.findAllByJenisTransaksi(jenis);
    }

    @Override
    public TransaksiModel retrieveTransaksiById(String id){
        Optional<TransaksiModel> transaksi = transaksiDb.findById(id);
        if (transaksi.isPresent()){
            return transaksi.get();
        }
        return null;
    }

    @Override
    public ProsesPenawaranSampahModel getTransaksiPenawaranSampah(String idTransaksi){
        Optional<ProsesPenawaranSampahModel> transaksi =
                prosesPenawaranSampahDb.findProsesPenawaranSampahModelByIdTransaksi(idTransaksi);
        if (transaksi.isPresent()){
            return transaksi.get();
        }
        return null;
    }

    @Override
    public ProsesPenawaranOlahanModel getTransaksiPenawaranOlahan(String idTransaksi){
        Optional<ProsesPenawaranOlahanModel> transaksi =
                prosesPenawaranOlahanDb.findProsesPenawaranOlahanModelByIdTransaksi(idTransaksi);
        if (transaksi.isPresent()){
            return transaksi.get();
        }
        return null;
    }

    @Override
    public ProsesLainModel getTransaksiLain(String idTransaksi){
        Optional<ProsesLainModel> transaksi =
                prosesLainDb.findProsesLainModelByIdTransaksi(idTransaksi);
        if (transaksi.isPresent()){
            return transaksi.get();
        }
        return null;
    }

    @Override
    public void delete(TransaksiModel transaksi){
        transaksiDb.delete(transaksi);
    }

    @Override
    public TransaksiModel updateTransaksiSampahOlahan(TransaksiModel updatedTransaksi){
        TransaksiModel transaksi = retrieveTransaksiById(updatedTransaksi.getIdTransaksi());
        transaksi.setKeterangan(updatedTransaksi.getKeterangan());
        return transaksiDb.save(transaksi);
    }

    @Override
    public ProsesLainModel updateTransaksiProsesLain(ProsesLainModel updatedTransaksi){
        ProsesLainModel transaksi = getTransaksiLain(updatedTransaksi.getIdTransaksi());
        transaksi.setNamaProses(updatedTransaksi.getNamaProses());
        transaksi.setPerson(updatedTransaksi.getPerson());
        transaksi.setNominal(updatedTransaksi.getNominal());
        transaksi.setJenisTransaksi(updatedTransaksi.getJenisTransaksi());
        transaksi.setTanggalTransaksi(updatedTransaksi.getTanggalTransaksi());
        transaksi.setKeterangan(updatedTransaksi.getKeterangan());
        return transaksiDb.save(transaksi);
    }

}
