package propensi.project.water.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.project.water.model.User.UserModel;
import propensi.project.water.service.MengelolaKaryawanService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/manajemen-user")
public class MengelolaKaryawanController {

    @Qualifier("mengelolaKaryawanServiceImpl")
    @Autowired
    private MengelolaKaryawanService mengelolaKaryawanService;

    @GetMapping({"/viewall", "/viewall/{role}"})
    private String retrieveListUser(
            Model model,
            @PathVariable(required = false, name="role") String role
    ){
        List<UserModel> listUser = mengelolaKaryawanService.retrieveListUser(role);

        if (role==null){
            role = "semua";
        }

        model.addAttribute("role", role);
        model.addAttribute("listUser", listUser);

        return "mengelola-karyawan/viewall-user";
    }

    @GetMapping("/view/{username}")
    private String retrieveUserDetail(
            Model model,
            @PathVariable(name="username") String username,
            String role
    ){
        UserModel user = mengelolaKaryawanService.retrieveUserDetail(username);

        model.addAttribute("role", role);
        model.addAttribute("user", user);

        return "mengelola-karyawan/view-user";
    }

    @GetMapping("/add")
    private String addKaryawanForm(Model model) {
        UserModel user = new UserModel();
        model.addAttribute("user", user);
        return "mengelola-karyawan/add-karyawan";
    }

    @PostMapping("/add")
    private String addKaryawanSubmit(
            @ModelAttribute UserModel user,
            RedirectAttributes redirectAttributes
    ){
        if (mengelolaKaryawanService.uniqueValueConstraint(user)) {
            redirectAttributes.addFlashAttribute("error", "Username and email has to be unique");
            return "redirect:add";
        } else {
            mengelolaKaryawanService.addKaryawan(user);
            return "redirect:viewall";
        }
    }

    @GetMapping("/update")
    public String updateKaryawanPage(
            @RequestParam(value="username") String username,
            Model model
    ){
        UserModel user = mengelolaKaryawanService.retrieveUserDetail(username);
        model.addAttribute("user", user);

        return "mengelola-karyawan/update-karyawan";
    }


    @PostMapping("/update")
    private String updateKaryawanSubmit(
            @ModelAttribute UserModel user,
            RedirectAttributes redirectAttributes
            ){
        boolean uniqueValueConstraintIsTrue = mengelolaKaryawanService.uniqueValueConstraintUpdate(user);

        // new kontak is not unique
        if (!uniqueValueConstraintIsTrue) {
            System.out.println("masuk di false controller");
            redirectAttributes.addFlashAttribute("failedUpdate",
                    String.format("Email dan Nomor Telepon harus unik"));
        } else {
            redirectAttributes.addFlashAttribute(
                    "success", "Data karyawan berhasil diubah");
        }

        return "redirect:view/" + user.getUsername();
    }

    @GetMapping(value = "/delete/{username}")
    private String deleteKaryawanSubmit(@PathVariable String username,
                                             RedirectAttributes redirectAttrs){

        UserModel karyawan = mengelolaKaryawanService.retrieveUserDetail(username);
        mengelolaKaryawanService.deleteUser(karyawan);

        redirectAttrs.addFlashAttribute("success",
                String.format("Karyawan dengan username %s berhasil dihapus", username));

        return "redirect:/manajemen-user/viewall/";
    }

    @PostMapping("/warehouse/delete")
    public String deleteItem(
            @RequestParam(value="username") String username
    ){
        UserModel user = mengelolaKaryawanService.retrieveUserDetail(username);
        mengelolaKaryawanService.deleteUser(user);
        return "redirect:/warehouse/laporan";
    }


}
