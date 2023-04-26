package propensi.project.water.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.project.water.model.Donasi.DonasiModel;
import propensi.project.water.model.Donasi.ItemDonasiModel;
import propensi.project.water.model.Donasi.DonasiModel;
import propensi.project.water.model.User.DonaturModel;
import propensi.project.water.model.User.UserModel;
import propensi.project.water.model.Warehouse.WarehouseModel;
import propensi.project.water.service.DonasiService;
import propensi.project.water.service.UserService;
import propensi.project.water.service.WarehouseService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/donasi")
public class DonasiController {

    @Qualifier("donasiServiceImpl")
    @Autowired
    private DonasiService donasiService;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Qualifier("warehouseServiceImpl")
    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/add")
    private String addDonasiForm(
            Model model,
            Principal principal) {
        UserModel userSession = userService.getUserByUsername(principal.getName());
        DonasiModel donasi = new DonasiModel();

        donasiService.setDefaultDonatur(donasi, userSession);

        model.addAttribute("userSession", userSession);
        model.addAttribute("donasi", donasi);
        return "donasi/add-donasi";
    }

    @PostMapping(value="/add", params={"save"})
    private String addDonasiSubmit(
            @ModelAttribute DonasiModel donasi,
            RedirectAttributes redirectAttributes) {
        if (donasi.getListItemDonasi()==null) {
            donasi.setListItemDonasi(new ArrayList<>());
        } else{
            for (ItemDonasiModel itemDonasi:donasi.getListItemDonasi()) {
                itemDonasi.setIdDonasi(donasi);
                itemDonasi.setKuantitas(-1);
            }
        }
        donasi.setTanggalDibuat(LocalDateTime.now());
        donasi.setStatus(0);
        donasi.setBeratSetelah(-1);
        donasi.setPoinEarned(-1);
        donasiService.addDonasi(donasi);
        redirectAttributes.addFlashAttribute("success", "Berhasil menambahkan donasi");
        return "redirect:viewall";
    }

    @PostMapping(value="/add", params={"addRow"})
    private String addRowListItemDonasi(
            @ModelAttribute DonasiModel donasi,
            Model model) {
        if (donasi.getListItemDonasi()==null || donasi.getListItemDonasi().size()==0) {
            donasi.setListItemDonasi(new ArrayList<>());
        }
        donasi.getListItemDonasi().add(new ItemDonasiModel());
        List<WarehouseModel> listItemWarehouse = warehouseService.getListItemWarehouse();

        System.out.println(listItemWarehouse.get(0).getIdItem());

        model.addAttribute("donasi", donasi);
        model.addAttribute("listItemWarehouse", listItemWarehouse);

        return "donasi/add-donasi";
    }

    @PostMapping(value="/add", params={"deleteRow"})
    private String deleteRowListItemDonasi(
            @ModelAttribute DonasiModel donasi,
            @RequestParam("deleteRow") Integer row,
            Model model) {
        final Integer rowId = Integer.valueOf(row);
        donasi.getListItemDonasi().remove(rowId.intValue());

        List<WarehouseModel> listItemWarehouse = warehouseService.getListItemWarehouse();

        model.addAttribute("donasi",donasi);
        model.addAttribute("listItemWarehouse", listItemWarehouse);

        return "donasi/add-donasi";
    }
    @GetMapping({"/viewall", "/viewall/status={fragmentStatus}"})
    private String viewAllDonasi(Model model,
                                    Principal principal,
                                    @PathVariable(required = false, name="fragmentStatus") Integer fragmentStatus,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "5") int size) {

        try{
            UserModel userSession = userService.getUserByUsername(principal.getName());
            Pageable paging = PageRequest.of(page - 1, size);

            Page<DonasiModel> pageDonasi;
            // case: userSession=donatur
            if (userSession.getRole().toString().equals("DONATUR")) {
                // case: fragment=semua
                if (fragmentStatus == null){
                    pageDonasi = donasiService.retrievePage((DonaturModel) userSession, fragmentStatus, paging);
                } else {
                    pageDonasi = donasiService.retrievePage((DonaturModel) userSession, fragmentStatus, paging);
                }
            }
            // case: userSession=internal
            else {
                if (fragmentStatus == null){
                    pageDonasi = donasiService.retrievePage(fragmentStatus, paging);
                } else {
                    pageDonasi = donasiService.retrievePage(fragmentStatus, paging);
                }
            }

            List<DonasiModel> listDonasi = pageDonasi.getContent();

            Integer firstItem = (pageDonasi.getNumber() + 1)*size-size+1;
            Integer lastItem = firstItem + listDonasi.size() -1;

            model.addAttribute("currentPage", pageDonasi.getNumber() + 1);
            model.addAttribute("firstItem", firstItem);
            model.addAttribute("lastItem", lastItem);
            model.addAttribute("totalItems", pageDonasi.getTotalElements());
            model.addAttribute("totalPages", pageDonasi.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("listDonasi", listDonasi);
            model.addAttribute("fragmentStatus", fragmentStatus);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "donasi/viewall-donasi";
    }

    @GetMapping("/view/{idDonasi}")
    private String viewDonasi(Model model,
                              @PathVariable(name="idDonasi") String idDonasi,
                              Principal principal
    ){
        UserModel userSession = userService.getUserByUsername(principal.getName());
        DonasiModel donasi = donasiService.findByIdDonasi(idDonasi);

        model.addAttribute("donasi", donasi);
        model.addAttribute("userSession", userSession);

        return "donasi/view-donasi";
    }

    @GetMapping(value = "/delete/{idDonasi}")
    private String deleteKaryawanSubmit(@PathVariable String idDonasi,
                                        RedirectAttributes redirectAttrs){

        DonasiModel donasi = donasiService.findByIdDonasi(idDonasi);
        donasiService.deleteDonasi(donasi);

        redirectAttrs.addFlashAttribute("success",
                String.format("Donasi dengan ID %s berhasil dihapus", idDonasi));

        return "redirect:/donasi/viewall/";
    }


    @GetMapping("/update/{idDonasi}")
    private String updateDonasiForm(
            @PathVariable String idDonasi,
            Model model
    ){
        DonasiModel donasi = donasiService.findByIdDonasi(idDonasi);

        List<WarehouseModel> listItemWarehouse = warehouseService.getListItemWarehouse();

        model.addAttribute("listItemWarehouse", listItemWarehouse);
        model.addAttribute("donasi", donasi);

        return "donasi/update-donasi";
    }

    @PostMapping(value="/update", params="save")
    private String updateDonasiSubmit(
            @ModelAttribute DonasiModel donasi,
            RedirectAttributes redirectAttributes
    ){
        for (ItemDonasiModel itemDonasi:donasi.getListItemDonasi()) {
            itemDonasi.setIdDonasi(donasi);
            itemDonasi.setKuantitas(-1);
        }
        donasiService.updateDonasi(donasi);
        redirectAttributes.addFlashAttribute("success", "Detail donasi berhasil diubah");
        return "redirect:view/" + donasi.getIdDonasi();
    }

    @PostMapping(value="/update", params={"addRowUpdate"})
    private String addRowListItemDonasiUpdate(
            @ModelAttribute DonasiModel donasi,
            Model model) {
        donasi.getListItemDonasi().add(new ItemDonasiModel());
        List<WarehouseModel> listItemWarehouse = warehouseService.getListItemWarehouse();

        System.out.println(listItemWarehouse.get(0).getIdItem());

        model.addAttribute("donasi", donasi);
        model.addAttribute("listItemWarehouse", listItemWarehouse);

        return "donasi/update-donasi";
    }

    @PostMapping(value="/update", params={"deleteRowUpdate"})
    private String deleteRowListItemDonasiUpdate(
            @ModelAttribute DonasiModel donasi,
            @RequestParam("deleteRowUpdate") Integer row,
            Model model) {
        final Integer rowId = Integer.valueOf(row);
        donasi.getListItemDonasi().remove(rowId.intValue());

        List<WarehouseModel> listItemWarehouse = warehouseService.getListItemWarehouse();

        model.addAttribute("donasi",donasi);
        model.addAttribute("listItemWarehouse", listItemWarehouse);

        return "donasi/update-donasi";
    }
}
