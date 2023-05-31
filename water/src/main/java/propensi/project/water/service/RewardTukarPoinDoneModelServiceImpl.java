package propensi.project.water.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.project.water.model.PoinReward.RewardTukarPoinDoneModel;
import propensi.project.water.repository.PoinReward.RewardTukarPoinDoneDb;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RewardTukarPoinDoneModelServiceImpl implements RewardTukarPoinDoneModelService{

    @Autowired
    private RewardTukarPoinDoneDb rewardTukarPoinDoneDb;

    @Override
    public RewardTukarPoinDoneModel save(RewardTukarPoinDoneModel newReward) {
        return rewardTukarPoinDoneDb.save(newReward);
    }

    @Override
    public List<RewardTukarPoinDoneModel> findAllByIdTukarPoin(String idTukarPoin) {
        List<RewardTukarPoinDoneModel> listByIdTP = new ArrayList<>();
        List<RewardTukarPoinDoneModel> newList = rewardTukarPoinDoneDb.findAll();
        for (RewardTukarPoinDoneModel reward : newList){
            if(reward.getIdTukarPoin().getIdTukarPoin().equals(idTukarPoin)){
                listByIdTP.add(reward);
            }
        }
        return listByIdTP;
    }

}