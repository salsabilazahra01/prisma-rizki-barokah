package propensi.project.water.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.project.water.model.Transaksi.ProsesLainModel;
import propensi.project.water.model.Transaksi.TransaksiModel;
import propensi.project.water.service.TransaksiService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/transaksi")
public class TransaksiController {

    @Autowired
    private TransaksiService transaksiService;

    @GetMapping("/add")
    public String addTransaksiForm(Model model){
        ProsesLainModel transaksiManual = new ProsesLainModel();
        transaksiManual.setProses(2);
        model.addAttribute("transaksi", transaksiManual);
        return "laporan-transaksi/add-transaksi-form";
    }

    @PostMapping(value = "/add")
    public String addTransaksiSubmit(@ModelAttribute ProsesLainModel transaksiManual,
                                     RedirectAttributes redirectAttrs
    ){
        transaksiService.addTransaksiManual(transaksiManual);
        redirectAttrs.addFlashAttribute("success","Transaksi baru berhasil ditambahkan");
        return "redirect:/transaksi/viewall/semua";
    }

    @GetMapping(value="/viewall/{jenis}")
    private String viewAllTransaksi(Model model,
                                    @PathVariable(name="jenis") String jenis) {

        List<TransaksiModel> listTransaksi;
        if (jenis.equals("semua")){
            listTransaksi = transaksiService.retrieveAllTransaksi();
        } else if(jenis.equals("pendapatan")){
            listTransaksi = transaksiService.retrieveAllTransaksi(Boolean.FALSE);
        } else {
            listTransaksi = transaksiService.retrieveAllTransaksi(Boolean.TRUE);
        }

        int total = 0;
        for(TransaksiModel transaksi : listTransaksi){
            if(jenis.equals("semua")){
                if(transaksi.getJenisTransaksi()){
                    total -= transaksi.getNominal();
                } else {
                    total += transaksi.getNominal();
                }
            } else {
                total += transaksi.getNominal();
            }
        }

        model.addAttribute("listTransaksi", listTransaksi);
        model.addAttribute("jenis", jenis);
        model.addAttribute("total", total);
        return "laporan-transaksi/viewall-transaksi";
    }

    @GetMapping(value="/view/{idTransaksi}")
    private String viewTransaksi(Model model,
                                 @PathVariable String idTransaksi) {

        var transaksi = transaksiService.retrieveTransaksiById(idTransaksi);

        if(transaksi.getProses() == 0){
            transaksi = transaksiService.getTransaksiPenawaranSampah(idTransaksi);
        } else if (transaksi.getProses() == 1){
            transaksi = transaksiService.getTransaksiPenawaranOlahan(idTransaksi);
        } else {
            transaksi = transaksiService.getTransaksiLain(idTransaksi);
        }

        model.addAttribute("transaksi", transaksi);
        return "laporan-transaksi/view-transaksi";
    }

    @GetMapping(value = "/delete/{idTransaksi}")
    private String deleteTransaksiProsesLain(@PathVariable String idTransaksi,
                                             RedirectAttributes redirectAttrs){

        TransaksiModel transaksi = transaksiService.retrieveTransaksiById(idTransaksi);
        transaksiService.delete(transaksi);

        redirectAttrs.addFlashAttribute("success",
                String.format("Transaksi dengan ID %s berhasil dihapus", idTransaksi));

        return "redirect:/transaksi/viewall/semua";
    }

    @GetMapping("/update/{idTransaksi}")
    private String updateTransaksi(
            @PathVariable String idTransaksi,
            Model model
    ) {

        var transaksi = transaksiService.retrieveTransaksiById(idTransaksi);

        if(transaksi.getProses() == 0){
            transaksi = transaksiService.getTransaksiPenawaranSampah(idTransaksi);
        } else if (transaksi.getProses() == 1){
            transaksi = transaksiService.getTransaksiPenawaranOlahan(idTransaksi);
        } else {
            transaksi = transaksiService.getTransaksiLain(idTransaksi);
        }

        model.addAttribute("transaksi", transaksi);
        return "laporan-transaksi/update-transaksi";
    }

    @PostMapping("/update/sampah-olahan")
    private String updateTransaksiSubmitSampahOlahan(
            @ModelAttribute TransaksiModel transaksi,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        var updatedTransaksi = transaksiService.updateTransaksiSampahOlahan(transaksi);
        model.addAttribute("transaksi", updatedTransaksi);
        redirectAttributes.addFlashAttribute("success","Transaksi berhasil diubah");

        return "redirect:/transaksi/view/" + transaksi.getIdTransaksi();
    }

    @PostMapping("/update/proses-lain")
    private String updateTransaksiSubmitProsesLain(
            @ModelAttribute ProsesLainModel transaksi,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        var updatedTransaksi = transaksiService.updateTransaksiProsesLain(transaksi);

        model.addAttribute("transaksi", updatedTransaksi);
        redirectAttributes.addFlashAttribute("success",
                String.format("Transaksi dengan ID %s berhasil diubah", updatedTransaksi.getIdTransaksi()));

        return "redirect:/transaksi/view/" + transaksi.getIdTransaksi();
    }

}
