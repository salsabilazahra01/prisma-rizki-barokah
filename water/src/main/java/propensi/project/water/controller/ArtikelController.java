package propensi.project.water.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.project.water.model.User.DonaturModel;
import propensi.project.water.model.User.UserModel;
import propensi.project.water.model.artikel.ArtikelModel;
import propensi.project.water.service.ArtikelService;
import propensi.project.water.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/artikel")
public class ArtikelController {

    @Autowired
    private UserService userService;
    @Autowired
    private ArtikelService artikelService;

    @GetMapping("/viewall")
    public String viewAllArtikel(Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

        UserModel user = userService.getUserByUsername(request.getRemoteUser()) == null ?
                null : userService.getUserByUsername(request.getRemoteUser());
        if (user!=null) {
            if (user.getRole().toString().equals("DONATUR")) {
                DonaturModel donatur = (DonaturModel) user;
                model.addAttribute("poin", donatur.getPoin());
            }
        }

        List<ArtikelModel> listArtikel = artikelService.getListArtikel();
        model.addAttribute("listArtikel", listArtikel);
        return "artikel/artikel-viewall";
    }

    @GetMapping("/add")
    public String addArtikelForm(Model model, RedirectAttributes redirectAttrs) {
        ArtikelModel artikelBaru = new ArtikelModel();
        model.addAttribute("artikel", artikelBaru);
        return "artikel/artikel-add";
    }


    @PostMapping(value = "/add")
    public String addArtikelSubmit(Model model, @ModelAttribute ArtikelModel artikel,
                                   @RequestParam("image") MultipartFile file,
                                   RedirectAttributes redirectAttrs
    ) throws SQLException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (file.isEmpty() || artikel.getContent().equals("")) {
            redirectAttrs.addFlashAttribute("failed", "Lengkapi seluruh field");
            model.addAttribute("artikel", artikel);
            return "redirect:/artikel/add";
        } else {
            artikel.setImageTitle(FileUploadUtil.encodePicture(file));
            artikel.setCreatedAt(LocalDateTime.now());
            artikelService.addArtikel(artikel);

            redirectAttrs.addFlashAttribute("success", "Artikel baru berhasil dibuat");
            return "redirect:/artikel/viewall";
        }
    }

    @GetMapping("/view/{idArtikel}")
    private String viewArtikel(Model model,
                               @PathVariable(name = "idArtikel") String idArtikel,
                               Principal principal,
                               HttpServletRequest request
    ) {

        UserModel user = userService.getUserByUsername(request.getRemoteUser()) == null ?
                null : userService.getUserByUsername(request.getRemoteUser());
        if (user!=null) {
            if (user.getRole().toString().equals("DONATUR")) {
                DonaturModel donatur = (DonaturModel) user;
                model.addAttribute("poin", donatur.getPoin());
            }
        }

        ArtikelModel artikel = artikelService.findByIdArtikel(idArtikel);
        model.addAttribute("artikel", artikel);
        return "artikel/artikel-view";
    }

    @GetMapping(value = "/delete/{idArtikel}")
    private String deleteArtikel(@PathVariable String idArtikel,
                                 RedirectAttributes redirectAttrs) {
        ArtikelModel artikel = artikelService.findByIdArtikel(idArtikel);
        String folderPath = "./src/main/resources/static/images/" + artikel.getIdArtikel();
        File folder = new File(folderPath);
        artikelService.deleteFolder(folder);
        artikelService.deleteArtikel(artikel);
        redirectAttrs.addFlashAttribute("success",
                String.format("Artikel dengan ID %s berhasil dihapus", idArtikel));

        return "redirect:/artikel/viewall";
    }

    @GetMapping("/update/{idArtikel}")
    private String updateArtikelForm(@PathVariable String idArtikel,
                                     Model model,
                                     Principal principal) {

        ArtikelModel artikel = artikelService.findByIdArtikel(idArtikel);
        model.addAttribute("artikel", artikel);
        return "artikel/artikel-update";
    }

    @PostMapping(value = "/update", params = {"save"})
    private String updateArtikelSubmit(
            @ModelAttribute ArtikelModel artikel, @RequestParam("image") MultipartFile file,
            RedirectAttributes redirectAttributes) throws IOException, SQLException {

        String idArtikel = artikel.getIdArtikel();
        ArtikelModel artikelLama = artikelService.findByIdArtikel(idArtikel);

        if (file.isEmpty()) {
            artikel.setImageTitle(artikelLama.getImageTitle());
        } else {
            artikel.setImageTitle(FileUploadUtil.encodePicture(file));
        }
        artikel.setLastEdited(LocalDateTime.now());
        artikel.setIsEdited(true);
        artikelService.updateArtikel(artikel);
        redirectAttributes.addFlashAttribute("success", "Berhasil memperbarui artikel!");
        return "redirect:/artikel/viewall";
    }


}
