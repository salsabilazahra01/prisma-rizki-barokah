package propensi.project.water.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.project.water.model.Warehouse.JenisItemModel;
import propensi.project.water.model.Warehouse.WarehouseModel;
import propensi.project.water.service.WarehouseService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/warehouse/laporan")
    public String viewAllItem(Model model) {
        List<JenisItemModel> listJenisItem = warehouseService.getListJenisItem();
        model.addAttribute("listJenisItem", listJenisItem);
        //Integer totalKuantitasSampah = 0;
        //model.addAttribute("totalKuantitasSampah", totalKuantitasSampah);
        return "/warehouse/laporanWarehouse";
    }

    @PostMapping("/warehouse/update")
    public String updateItem(@ModelAttribute WarehouseModel item, Model model, RedirectAttributes redirectAttrs){
        warehouseService.updateItem(item);
        String namaItem = item.getNamaItem();
        redirectAttrs.addFlashAttribute("successUpdate",
                String.format("Item %s sudah berhasil diperbarui", namaItem ));
        return "redirect:/warehouse/laporan";
    }

    @GetMapping("/warehouse/addNew")
    public String addItemForm(Model model, RedirectAttributes redirectAttrs){
        WarehouseModel itembaru = new WarehouseModel();
        model.addAttribute("itembaru", itembaru);
        return "redirect:/warehouse/laporan";
    }

    @PostMapping("/warehouse/add")
    public String addItem(@ModelAttribute WarehouseModel item, Model model, RedirectAttributes redirectAttrs){
        warehouseService.addItem(item);
        String namaItem = item.getNamaItem();
        redirectAttrs.addFlashAttribute("successAdd",
                String.format("Item %s sudah berhasil dibuat", namaItem ));
        return "redirect:/warehouse/laporan";
    }

    @PostMapping("/warehouse/delete")
    public String deleteItem(@ModelAttribute WarehouseModel item, Model model, RedirectAttributes redirectAttrs){
        String namaItem = item.getNamaItem();
        if(item.getKuantitasSampah().equals(0) && item.getKuantitasOlahan().equals(0)){
            warehouseService.deleteItem(item);
            redirectAttrs.addFlashAttribute("successDelete",
                    String.format("Item %s sudah berhasil dihapus dari database", namaItem ));
        }else{
            redirectAttrs.addFlashAttribute("failedDelete",
                    String.format("Item %s tidak dapat dihapus dari database. Kuantitas sampah dan hasil olahan item harus 0 sebelum dihapus.", namaItem ));
        }
        return "redirect:/warehouse/laporan";
    }


}