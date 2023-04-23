package propensi.project.water.service;

import propensi.project.water.model.PoinReward.RewardModel;
import propensi.project.water.repository.PoinReward.RewardDb;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RewardServiceImpl implements RewardService {
    @Autowired
    private RewardDb rewardDb;

    @Override
    public RewardModel addReward(RewardModel reward) {
        rewardDb.save(reward);

        return reward;
    }

    @Override
    public RewardModel getRewardById (String id) {
        Optional<RewardModel> reward = rewardDb.findById(id);
        if (reward.isPresent()) {
            return reward.get();
        }

        return null;
    }

    @Override
    public List<RewardModel> getListReward() {
        return rewardDb.findAll();
    }

    @Override
    public void deleteReward(RewardModel reward) {
        rewardDb.delete(reward);
    }

    @Override
    public RewardModel updateReward(RewardModel reward) {
        rewardDb.save(reward);

        return reward;
    }
}
