package propensi.project.water.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.project.water.model.Transaksi.TransaksiModel;
import propensi.project.water.service.TransaksiService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/transaksi")
public class TransaksiController {

    @Autowired
    private TransaksiService transaksiService;

    @GetMapping(value="/viewall/{jenis}")
    private String viewAllTransaksi(Model model,
                                    @PathVariable(name="jenis") String jenis) {

//        List<TransaksiModel> listTransaksi;
//        if (jenis.equals("")){
//            listTransaksi = transaksiService.retrieveAllTransaksi();
//        } else if(jenis.equals("pendapatan")){
//            listTransaksi = transaksiService.retrieveAllTransaksi("pendapatan");
//        } else {
//            listTransaksi = transaksiService.retrieveAllTransaksi("pengeluaran");
//        }
//
//        model.addAttribute("listTransaksi", listTransaksi);
        model.addAttribute("jenis", jenis);
        return "laporan-transaksi/viewall-transaksi";
    }

    @GetMapping(value="/view")
    private String viewTransaksi(Model model) {
//        List<TransaksiModel> listTransaksi = transaksiService.getAllTransaksi();
//        model.addAttribute("listTransaksi", listTransaksi);
        return "laporan-transaksi/view-transaksi";
    }

}
