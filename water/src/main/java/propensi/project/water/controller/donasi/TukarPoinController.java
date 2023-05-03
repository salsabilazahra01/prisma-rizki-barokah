package propensi.project.water.controller.donasi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import propensi.project.water.dto.TukarPoinDTO;
import propensi.project.water.model.PoinReward.TukarPoinModel;
import propensi.project.water.model.User.DonaturModel;
import propensi.project.water.model.User.UserModel;
import propensi.project.water.service.DonaturService;
import propensi.project.water.service.RewardService;
import propensi.project.water.service.TukarPoinService;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/donasi/penukaran-poin")
@AllArgsConstructor
public class TukarPoinController {
    @Autowired
    private final TukarPoinService tukarPoinService;
    @Autowired
    private final RewardService rewardService;
    @Autowired
    private final DonaturService donaturService;

    @GetMapping()
    public String index(Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) Boolean status,
            Principal principal
            ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        DonaturModel donatur = donaturService.getDonaturByUsername(userDetail.getUsername());
        log.info("Donatur: {}\n\n\n\n\n", donatur);
        val pageModel = tukarPoinService.findAllByDonatur(PageRequest.of(page - 1, size), donatur, status);
        int currentPage = pageModel.getNumber()+1;
        int firstItem = (currentPage-1) * size + 1;
        int lastItem = firstItem + pageModel.getContent().size() - 1;

        if(donatur != null){
            DonaturModel donaturModel = donaturService.getDonaturByUsername(principal.getName());
            model.addAttribute("poin", donaturModel.getPoin());
        }
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("firstItem", firstItem);
        model.addAttribute("lastItem", lastItem);
        model.addAttribute("totalItems", pageModel.getTotalElements());
        model.addAttribute("totalPages", pageModel.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("rewards", pageModel.getContent());
        return "/donasi/penukaran-poin/index";
    }

    // Create form
    @GetMapping("/add")
    public String create(Model model, Principal principal) {
        model.addAttribute("form", new TukarPoinDTO());
        model.addAttribute("title", "Tukar Poin");
        model.addAttribute("rewards", rewardService.findAll());
        DonaturModel donaturModel = donaturService.getDonaturByUsername(principal.getName());
        model.addAttribute("poin", donaturModel.getPoin());
        return "/donasi/penukaran-poin/form";
    }
    
    // Create form
    @PostMapping("/add")
    public String create(Model model, TukarPoinDTO form) {
        tukarPoinService.add(form);
        return "redirect:/donasi/penukaran-poin";
    }

    
    @GetMapping("/{item}/detail")
    public String detail(Model model, @PathVariable("item") TukarPoinModel item, Principal principal) {
        DonaturModel donaturModel = donaturService.getDonaturByUsername(principal.getName());
        if(donaturModel != null){
            model.addAttribute("poin", donaturModel.getPoin());
        }
        model.addAttribute("item", item);
        return "/donasi/penukaran-poin/detail";
    }

    
    @PostMapping("/{item}/detail")
    public String update(Model model, @PathVariable("item") TukarPoinModel item) {
        item.setStatus(true);
        tukarPoinService.update(item);
        return "/donasi/penukaran-poin/detail";
    }

}
