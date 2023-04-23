package propensi.project.water.service;

import propensi.project.water.model.PoinReward.RewardModel;

import java.util.List;

public interface RewardService {
    RewardModel addReward(RewardModel reward);
    public RewardModel getRewardById(String id);
    List<RewardModel> getListReward();
    void deleteReward(RewardModel reward);
    RewardModel updateReward(RewardModel reward);
}
