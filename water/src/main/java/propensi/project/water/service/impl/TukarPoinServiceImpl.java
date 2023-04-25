package propensi.project.water.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.val;
import propensi.project.water.dto.TukarPoinDTO;
import propensi.project.water.model.PoinReward.TukarPoinBillingModel;
import propensi.project.water.model.PoinReward.TukarPoinModel;
import propensi.project.water.model.User.DonaturModel;
import propensi.project.water.repository.donasi.TukarPoinBillingDb;
import propensi.project.water.repository.donasi.TukarPoinDb;
import propensi.project.water.service.DonaturService;
import propensi.project.water.service.TukarPoinService;

@Service
@AllArgsConstructor
public class TukarPoinServiceImpl implements TukarPoinService {

    @Autowired
    private final TukarPoinDb tukarPoinDb;
    @Autowired
    private final TukarPoinBillingDb tukarPoinBillingDb;
    
    @Autowired
    private final DonaturService donaturService;

    @Override
    public List<TukarPoinModel> findAll() {
        return tukarPoinDb.findAll();
    }

    @Override
    public Page<TukarPoinModel> findAll(Pageable paging) {
        return tukarPoinDb.findAll(paging);
    }

    @Override
    public TukarPoinModel findById(String id) {
        // TODO: Change this
        return tukarPoinDb.findById(id)
                .orElseThrow(() -> new RuntimeException(id));
    }

    @Override
    public TukarPoinModel add(TukarPoinDTO dto) {
        TukarPoinModel tukarPoin = TukarPoinModel.builder()
                .donatur(donaturService.getDonaturByUsername(dto.getUsername()))
                .reward(dto.getReward())
                .build();
        tukarPoinDb.save(tukarPoin);
//        if (tukarPoin.getReward().jenis().equals("Uang")) {
//            TukarPoinBillingModel tukarPoinBilling = TukarPoinBillingModel.builder()
//                    .namaBank(dto.getBank())
//                    .nomorRekening(dto.getNoRek())
//                    .namaRekening(dto.getNamaRek())
//                    .tukarPoin(tukarPoin)
//                    .build();
//            tukarPoinBillingDb.save(tukarPoinBilling);
//            tukarPoin.setBilling(tukarPoinBilling);
//        }
        return tukarPoin;
    }

    @Override
    public TukarPoinModel update(TukarPoinModel model) {
        return tukarPoinDb.save(model);
    }

    @Override
    public TukarPoinModel delete(TukarPoinModel model) {
        tukarPoinDb.delete(model);
        return model;
    }

    @Override
    public Page<TukarPoinModel> findAllByDonatur(Pageable paging, DonaturModel donatur, Boolean status) {
        TukarPoinModel tukarPoin = TukarPoinModel.builder()
                .donatur(donatur)
                .tanggal(null)
                .status(status)
                .build();
        Example<TukarPoinModel> example = Example.of(tukarPoin);
        return tukarPoinDb.findAll(example, paging);
    }
    
}
