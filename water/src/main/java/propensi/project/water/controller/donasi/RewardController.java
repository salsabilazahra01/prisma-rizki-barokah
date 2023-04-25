package propensi.project.water.controller.donasi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import propensi.project.water.dto.RewardDTO;
import propensi.project.water.exceptions.donasi.RewardDuplicateJenisException;
import propensi.project.water.exceptions.donasi.RewardNotFoundException;
import propensi.project.water.model.PoinReward.RewardModel;
import propensi.project.water.service.RewardService;

@Slf4j
@Controller
@RequestMapping("/donasi/reward")
@AllArgsConstructor
public class RewardController {
    @Autowired
    private final RewardService rewardService;

    @GetMapping()
    public String index(Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<RewardModel> pageReward = rewardService.findAll(paging);
        int currentPage = pageReward.getNumber()+1;
        int firstItem = (currentPage-1) * size + 1;
        int lastItem = firstItem + pageReward.getContent().size() - 1;
        
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("firstItem", firstItem);
        model.addAttribute("lastItem", lastItem);
        model.addAttribute("totalItems", pageReward.getTotalElements());
        model.addAttribute("totalPages", pageReward.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("rewards", pageReward.getContent());
        return "/donasi/reward/index";
    }

    // Create form
    @GetMapping("/add")
    public String create(Model model) {
        model.addAttribute("reward", new RewardDTO());
        model.addAttribute("title", "Buat Jenis Reward");
        return "/donasi/reward/form";
    }

    @PostMapping("/add")
    public String create(Model model, RewardDTO reward) {
        try {
            rewardService.add(reward);
        } catch (Exception e) {
            log.error("Error while creating reward", e);
            model.addAttribute("reward", reward);
            model.addAttribute("title", "Buat Jenis Reward");
            return "/donasi/reward/form";
        }
        return "redirect:/donasi/reward";
    }

    /**
     * Edit form
     * 
     * @param model  Model to be passed to the view (Thymeleaf)
     * @param reward RewardModel to be edited
     * @param dto    RewardDTO to be passed to the view (Thymeleaf)
     * @return
     */
    private String edit(Model model, RewardDTO dto) {
        model.addAttribute("reward", dto);
        model.addAttribute("title", "Edit Jenis Reward ");
        return "/donasi/reward/form";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") String id) {
        try {
            val reward = rewardService.findById(id);
            return edit(model, RewardDTO.fromModel(reward));
        } catch (RewardNotFoundException err) {
            log.error("Error while editing reward", err);
            // Redirect to error 404 page
            return "redirect:/error/404";
        } catch (Exception e) {
            log.error("Error while editing reward", e);
            // Redirect to error 500 page
            return "redirect:/error/500";
        }
    }

    @PostMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") String id, RewardDTO reward) {
        try {
            val rewardModel = rewardService.findById(id);
            rewardService.update(reward, rewardModel);
            return "redirect:/donasi/reward";
        } catch (RewardDuplicateJenisException duplicate) {
            log.error("Error while editing reward", duplicate);
            model.addAttribute("error", duplicate.getMessage());
            return edit(model, reward);
        } catch (RewardNotFoundException notFound) {
            log.error("Error while editing reward", notFound);
            return "redirect:/error/404";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable("id") String id) {
        try {
            val reward = rewardService.findById(id);
            rewardService.delete(reward);
            return "redirect:/donasi/reward";
        } catch (RewardNotFoundException err) {
            log.error("Error while deleting reward", err);
            // Redirect to error 404 page
            return "redirect:/error/404";
        } catch (Exception e) {
            log.error("Error while deleting reward", e);
            // Redirect to error 500 page
            return "redirect:/error/500";
        }
    }

}
