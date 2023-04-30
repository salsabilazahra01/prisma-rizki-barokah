package propensi.project.water.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.project.water.dto.RewardDTO;
import propensi.project.water.exceptions.donasi.RewardDuplicateJenisException;
import propensi.project.water.exceptions.donasi.RewardNotFoundException;
import propensi.project.water.model.PoinReward.RewardModel;
import propensi.project.water.model.PoinReward.TukarPoinModel;
import propensi.project.water.service.RewardService;
import propensi.project.water.service.TukarPoinService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/reward")
@AllArgsConstructor
public class RewardController {
    @Autowired
    private final RewardService rewardService;

    @Autowired
    private TukarPoinService tukarPoinService;

    @GetMapping("/viewall")
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
        return "/reward/viewall-reward";
    }

    // Create form
    @GetMapping("/add")
    public String create(Model model) {
        model.addAttribute("reward", new RewardDTO());
        model.addAttribute("title", "Buat Jenis Reward");
        return "/reward/form";
    }

    @PostMapping("/add")
    public String create(Model model, RewardDTO reward, RedirectAttributes redirectAttrs) {
        try {
            rewardService.add(reward);
        } catch (Exception e) {
            log.error("Error while creating reward", e);
            model.addAttribute("reward", reward);
            model.addAttribute("title", "Buat Jenis Reward");
            return "/reward/form";
        }
        redirectAttrs.addFlashAttribute("success","Reward baru berhasil ditambahkan");
        return "redirect:/reward/viewall";
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
        return "/reward/form";
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
    public String edit(Model model, @PathVariable("id") String id, RewardDTO reward, RedirectAttributes redirectAttrs) {
        try {
            val rewardModel = rewardService.findById(id);
            rewardService.update(reward, rewardModel);

            List<TukarPoinModel> listPenukaran = tukarPoinService.findAll();
            for (int i = 0; i < listPenukaran.size(); i++) {
                if (listPenukaran.get(i).getReward().getIdReward().equals(id)) {
                    if (listPenukaran.get(i).getStatus().equals(false)) {
                        return "redirect:/error/404";
                    }
                }
            }

            return "redirect:/reward/viewall";
        } catch (RewardDuplicateJenisException duplicate) {
            log.error("Error while editing reward", duplicate);
            model.addAttribute("error", duplicate.getMessage());
            return edit(model, reward);
        } catch (RewardNotFoundException notFound) {
            log.error("Error while editing reward", notFound);
            redirectAttrs.addFlashAttribute("failed", "Reward gagal diupdate karena masih terdapat penukaran poin dengan reward ini");
            return "redirect:/reward/form";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable("id") String id, RedirectAttributes redirectAttrs) {
        try {
            val reward = rewardService.findById(id);

            List<TukarPoinModel> listPenukaran = tukarPoinService.findAll();
            for (int i = 0; i < listPenukaran.size(); i++) {
                if (listPenukaran.get(i).getReward().getIdReward().equals(id)) {
                    if (listPenukaran.get(i).getStatus().equals(false)) {
                        return "redirect:/error/404";
                    }
                }
            }

            rewardService.delete(reward);
            redirectAttrs.addFlashAttribute("success","Reward berhasil dihapus");
            return "redirect:/reward/viewall";
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
