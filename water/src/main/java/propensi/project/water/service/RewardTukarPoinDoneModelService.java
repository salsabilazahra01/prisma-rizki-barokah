package propensi.project.water.service;

import propensi.project.water.model.PembelianSampah.PenawaranSampahModel;
import propensi.project.water.model.PoinReward.RewardTukarPoinDoneModel;

import java.util.List;

public interface RewardTukarPoinDoneModelService {
    RewardTukarPoinDoneModel save(RewardTukarPoinDoneModel newReward);
    List<RewardTukarPoinDoneModel> findAllByIdTukarPoin(String idTukarPoin);
}
