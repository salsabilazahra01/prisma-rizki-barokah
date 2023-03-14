package propensi.project.water.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import propensi.project.water.model.Transaksi.TransaksiModel;
import propensi.project.water.repository.Transaksi.TransaksiDb;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransaksiServiceImpl implements TransaksiService{
    @Autowired
    private TransaksiDb transaksiDb;

    @Override
    public List<TransaksiModel> retrieveAllTransaksi(){
        return transaksiDb.findAll();
    }

    public List<TransaksiModel> retrieveAllTransaksi(String jenis){
        List<TransaksiModel> listAllTransaksi = transaksiDb.findAll();
        List<TransaksiModel> listTransaksiByJenis = new ArrayList<>();

        for(TransaksiModel transaksi : listAllTransaksi){
            if(transaksi.getJenisTransaksi().equals(jenis)){
                listTransaksiByJenis.add(transaksi);
            }
        }

        return listTransaksiByJenis;
    }

}
