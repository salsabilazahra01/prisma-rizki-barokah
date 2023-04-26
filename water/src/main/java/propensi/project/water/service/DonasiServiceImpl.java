package propensi.project.water.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import propensi.project.water.model.Donasi.DonasiModel;
import propensi.project.water.model.Transaksi.TransaksiModel;
import propensi.project.water.model.User.DonaturModel;
import propensi.project.water.model.User.UserModel;
import propensi.project.water.repository.Donasi.DonasiDb;
import propensi.project.water.repository.Donasi.ItemDonasiDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DonasiServiceImpl implements DonasiService {

    @Autowired
    private DonasiDb donasiDb;

    @Autowired
    private ItemDonasiDb itemDonasiDb;

    @Override
    public void addDonasi(DonasiModel donasi) {
        donasiDb.save(donasi);
    }

    @Override
    public void setDefaultDonatur(DonasiModel donasi, UserModel user) {
        donasi.setDonatur((DonaturModel) user);
        donasi.setNamaPic(user.getNama());
        donasi.setKontakPic(user.getHp());
        donasi.setAlamatPic(((DonaturModel) user).getAlamat());
    }

    @Override
    public List<DonasiModel> findAll() {
        return donasiDb.findAll();
    }

    @Override
    public List<DonasiModel> findAll(UserModel user) {
        List<DonasiModel> listDonasiUser = donasiDb.findAll();
        for(DonasiModel donasi:listDonasiUser) {
            if (donasi.getDonatur().getUsername() != user.getUsername()) {
                listDonasiUser.remove(donasi);
            }
        }
        return listDonasiUser;
    }

    @Override
    public Page<DonasiModel> retrievePage(Integer fragmentStatus, Pageable paging){
        if (fragmentStatus == null){
            return donasiDb.findAllByIdDonasiIsNotNullOrderByTanggalDibuat(paging);
        }
        return donasiDb.findAllByStatusOrderByTanggalDibuat(fragmentStatus, paging);
    }

    @Override
    public Page<DonasiModel> retrievePage(DonaturModel donatur, Integer fragmentStatus, Pageable paging){
        if (fragmentStatus == null){
            return donasiDb.findAllByDonaturOrderByTanggalDibuat(donatur, paging);
        }
        return donasiDb.findAllByDonaturAndStatusOrderByTanggalDibuat(donatur, fragmentStatus, paging);
    }

    @Override
    public DonasiModel findByIdDonasi(String idDonasi) { return donasiDb.findByIdDonasi(idDonasi).orElse(null);}

    @Override
    public void deleteDonasi(DonasiModel donasi) {
        donasiDb.delete(donasi);
    }

    @Override
    public void updateDonasi(DonasiModel updatedDonasi) {
        DonasiModel donasi = findByIdDonasi(updatedDonasi.getIdDonasi());
        donasi.setNamaPic(updatedDonasi.getNamaPic());
        donasi.setAlamatPic(updatedDonasi.getAlamatPic());
        donasi.setKontakPic(updatedDonasi.getKontakPic());
        donasi.setIsPickedUp(updatedDonasi.getIsPickedUp());
        donasi.setBeratSebelum(updatedDonasi.getBeratSebelum());
        donasi.setListItemDonasi(updatedDonasi.getListItemDonasi());
        donasiDb.save(donasi);}

}
