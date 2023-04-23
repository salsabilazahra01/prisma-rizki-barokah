package propensi.project.water.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import propensi.project.water.model.PoinReward.RewardModel;
import propensi.project.water.service.RewardService;

@Slf4j
@Controller
@RequestMapping("/reward")
public class RewardController {
    @Qualifier("rewardServiceImpl")
    @Autowired
    private RewardService rewardService;

    @GetMapping(value = "/add")
    public String addRewardForm(Model model) {
        RewardModel reward = new RewardModel();
        model.addAttribute("reward", reward);

        return "reward/form-add-reward";
    }

    @PostMapping(value = "/add")
    public String addRewardSubmit(@ModelAttribute RewardModel reward, Model model) {
        rewardService.addReward(reward);
        model.addAttribute("reward", reward);

        return "reward/success-add-reward";
    }

    @GetMapping(value = "/viewall")
    public String viewAllReward(Model model) {
        List<RewardModel> listReward = rewardService.getListReward();
        model.addAttribute("listReward", listReward);

        return "reward/viewall-reward";
    }

    @GetMapping(value = "/update/{id}")
    public String updateRewardForm(Model model, @PathVariable String id) {
        RewardModel reward = rewardService.getRewardById(id);
        model.addAttribute("reward", reward);

        return "reward/form-update-reward";
    }

    @PostMapping(value = "/update")
    public String updateRewardSubmit(Model model, @ModelAttribute RewardModel reward) {
        RewardModel updatedReward = rewardService.updateReward(reward);
        model.addAttribute("reward", updatedReward);

        return "reward/success-update-reward";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteReward(Model model, @PathVariable String id) {
        RewardModel rewardTarget = rewardService.getRewardById(id);
        rewardService.deleteReward(rewardTarget);

        return "reward/success-delete-reward";
    }
}
