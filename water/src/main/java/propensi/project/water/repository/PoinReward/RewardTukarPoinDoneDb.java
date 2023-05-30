package propensi.project.water.repository.PoinReward;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.water.model.PoinReward.RewardTukarPoinDoneModel;
import propensi.project.water.model.PoinReward.RewardTukarPoinModel;
import propensi.project.water.model.Transaksi.ProsesTukarPoinModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardTukarPoinDoneDb extends JpaRepository<RewardTukarPoinDoneModel, String> {
//    List<RewardTukarPoinDoneModel> findAll();
}
