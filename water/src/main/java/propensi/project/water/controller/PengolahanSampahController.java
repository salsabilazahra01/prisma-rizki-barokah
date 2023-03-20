package propensi.project.water.controller;

import lombok.extern.slf4j.Slf4j;
import propensi.project.water.model.Warehouse.BatchModel;
import propensi.project.water.model.Warehouse.JenisItemModel;
import propensi.project.water.model.Warehouse.WarehouseModel;
import propensi.project.water.service.BatchService;
import propensi.project.water.service.JenisItemService;
import propensi.project.water.service.WarehouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pengolahan-sampah")
public class PengolahanSampahController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private BatchService batchService;

    @GetMapping()
    public String showIndexPage(Model model) {
        // get list of warehouses

        List<WarehouseModel> warehouses = new ArrayList<WarehouseModel>();
        warehouseService.getListJenisItem().forEach( e -> {
            warehouses.addAll(e.getListNamaItem());
        });
        model.addAttribute("warehouses", warehouses);
        // get list of batches
        List<BatchModel> batches = batchService.getAll();
        model.addAttribute("batches", batches);
        return "laporan-sampah/index";
    }


    @GetMapping("/{batchModel}")
    public String showBatchDetail(@PathVariable("batchModel") BatchModel batchModel, Model model) {
        // Add BatchModel to model for use in Thymeleaf template
        model.addAttribute("batch", batchModel);

        return "laporan-sampah/detail";
    }

    @PostMapping("/{batchModel}")
    public String appendBatchDetail(@PathVariable("batchModel") BatchModel batchModel, Model model,
    RedirectAttributes redirectAttributes) {
        batchModel.setStatus(batchModel.getStatus()+1);
        batchService.update(batchModel);
        redirectAttributes.addFlashAttribute("message", "Batch detail updated successfully.");
        return "redirect:/pengolahan-sampah/" + batchModel.getIdBatch();
    }

    @PostMapping("/save")
    public String saveBatch(@RequestParam("warehouseId") WarehouseModel warehouse,
                            @RequestParam("kuantitasBahanBaku") Integer kuantitasBahanBaku,
                            RedirectAttributes redirectAttributes) {
        try {
            BatchModel batchModel = new BatchModel();
            batchModel.setWarehouse(warehouse);
            batchModel.setKuantitasBahanBaku(kuantitasBahanBaku);
            batchModel.setStatus(1);
            batchModel.setTanggalDibuat(LocalDateTime.now());
            batchModel.setKuantitasHasil(0);
            // save batch
            batchService.add(batchModel);
            redirectAttributes.addFlashAttribute("successMessage", "Batch berhasil dibuat");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Terjadi kesalahan saat membuat batch");
        }
        return "redirect:/pengolahan-sampah";
    }
}
