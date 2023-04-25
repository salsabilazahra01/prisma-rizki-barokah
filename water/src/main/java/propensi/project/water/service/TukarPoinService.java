package propensi.project.water.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import propensi.project.water.dto.TukarPoinDTO;
import propensi.project.water.model.PoinReward.TukarPoinModel;
import propensi.project.water.model.User.DonaturModel;

public interface TukarPoinService {
    List<TukarPoinModel> findAll();
    Page<TukarPoinModel> findAll(Pageable paging);
    Page<TukarPoinModel> findAllByDonatur(Pageable paging, DonaturModel donatur, Boolean status);
    TukarPoinModel findById(String id);
    TukarPoinModel add(TukarPoinDTO dto);
    TukarPoinModel update(TukarPoinModel model);
    TukarPoinModel delete(TukarPoinModel model);
    
}
