package propensi.project.water.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.project.water.model.PenjualanHasilOlahan.ItemPenawaranOlahanModel;
import propensi.project.water.model.PenjualanHasilOlahan.PenawaranOlahanModel;
import propensi.project.water.model.Transaksi.ProsesPenawaranOlahanModel;
import propensi.project.water.model.User.CustomerModel;
import propensi.project.water.model.Warehouse.WarehouseModel;
import propensi.project.water.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/penawaran-hasil-olahan")
public class PenawaranHasilOlahanController {

    @Autowired
    private PenawaranOlahanService penawaranOlahanService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TransaksiService transaksiService;

    @GetMapping(value="/viewall/{status}")
    private String viewAllPenawaranOlahan(Model model,
                                          @PathVariable(name="status") String status,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "5") int size) {

        try{
            Integer statusInt = Integer.parseInt(status);
            Pageable paging = PageRequest.of(page - 1, size);

            Page<PenawaranOlahanModel> pagePenawaranOlahan =
                    penawaranOlahanService.retrievePage(paging, statusInt);

            List<PenawaranOlahanModel> listPageOlahan = pagePenawaranOlahan.getContent();

            Integer firstItem = (pagePenawaranOlahan.getNumber() + 1)*size-size+1;
            Integer lastItem = firstItem + listPageOlahan.size() - 1;

            model.addAttribute("currentPage", pagePenawaranOlahan.getNumber() + 1);
            model.addAttribute("firstItem", firstItem);
            model.addAttribute("lastItem", lastItem);
            model.addAttribute("totalItems", pagePenawaranOlahan.getTotalElements());
            model.addAttribute("totalPages", pagePenawaranOlahan.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("listPenawaranOlahan", listPageOlahan);
            model.addAttribute("status", statusInt);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "penawaran-olahan/viewall-penawaran-olahan";
    }

    @GetMapping(value="/view/{idPenawaranOlahan}")
    private String viewPenawaranOlahan(Model model,
                                       @PathVariable String idPenawaranOlahan) {

        PenawaranOlahanModel penawaranOlahan = penawaranOlahanService.getPenawaranOlahanById(idPenawaranOlahan);
        List<ItemPenawaranOlahanModel> listItem = penawaranOlahan.getListItemPenawaranOlahan();

        int totalBerat = 0;
        for(int i = 0; i < listItem.size(); i++){
            totalBerat += listItem.get(i).getKuantitas();
        }

        model.addAttribute("totalBerat", totalBerat);
        model.addAttribute("penawaranOlahan", penawaranOlahan);
        return "penawaran-olahan/view-penawaran-olahan";
    }

    @GetMapping(value="/add")
    private String addPenawaranOlahanForm(Model model,
                                          HttpServletRequest request) {

        //get customer if exists
        CustomerModel customer =  getCustomer(request);

        //set new penawaranOlahan
        PenawaranOlahanModel penawaranOlahan = new PenawaranOlahanModel();

        //create list of items that has > 0
        List<WarehouseModel> listItemEx = warehouseService.getListItem();
        listItemEx.removeIf(item -> (item.getKuantitasOlahan() == 0));

        //create new list item
        List<ItemPenawaranOlahanModel> listItemPenawaranOlahan = new ArrayList<>();
        penawaranOlahan.setListItemPenawaranOlahan(listItemPenawaranOlahan);
        penawaranOlahan.getListItemPenawaranOlahan().add(new ItemPenawaranOlahanModel());

        //input customer info to penawaranOlahan
        if(customer != null){
            penawaranOlahan = penawaranOlahanService.setCustomerInfo(customer, penawaranOlahan);
        }

        model.addAttribute("penawaranOlahan", penawaranOlahan);
        model.addAttribute("listItemEx", listItemEx);
        return "penawaran-olahan/add-penawaran-olahan";
    }

    @PostMapping(value="/add", params = {"save"})
    private String addPenawaranOlahanSubmit(@ModelAttribute PenawaranOlahanModel penawaranOlahan,
                                            Model model,
                                            RedirectAttributes redirectAttrs,
                                            HttpServletRequest request) {

        List<ItemPenawaranOlahanModel> listItem = penawaranOlahan.getListItemPenawaranOlahan();

        //get customer if exists
        CustomerModel customer =  getCustomer(request);

        //check validation
        if(kontakEmpty(penawaranOlahan)){
            redirectAttrs.addFlashAttribute("failed",
                    "E-mail atau nomor telepon harus diisi");
            return "redirect:/penawaran-hasil-olahan/add";
        }
        else if(isDuplicate(listItem)){
            redirectAttrs.addFlashAttribute("failed",
                    "Penawaran hasil olahan sampah tidak dapat dibuat karena terdapat duplikasi jenis item");
            return "redirect:/penawaran-hasil-olahan/add";
        }
        else if(itemValueInvalid(listItem)){
            redirectAttrs.addFlashAttribute("failed",
                    "Perubahan tidak dapat dibuat karena terdapat jenis yang tidak valid");
            return "redirect:/penawaran-hasil-olahan/add";
        }
        else if(quantityExceeded(listItem)){
            redirectAttrs.addFlashAttribute("failed",
                    "Penawaran hasil olahan sampah tidak dapat dibuat karena berat melebihi kuantitas yang tersedia");
            return "redirect:/penawaran-hasil-olahan/add";
        }

        penawaranOlahanService.add(penawaranOlahan, customer);

        //integrate if manual
        if(customer == null){
            integrateWarehouseTransaksi(penawaranOlahan, false);
        }

        redirectAttrs.addFlashAttribute("success",
            String.format("Penawaran hasil olahan sampah baru dengan id %s berhasil dibuat",
                    penawaranOlahan.getIdPenawaranOlahan()));

        model.addAttribute("penawaranOlahan", penawaranOlahan.getIdPenawaranOlahan());
        return "redirect:/penawaran-hasil-olahan/viewall/0";
    }

    @GetMapping(value="/update/{idPenawaranOlahan}")
    private String updatePenawaranOlahanForm(Model model,
                                             @PathVariable String idPenawaranOlahan) {

        PenawaranOlahanModel penawaranOlahan = penawaranOlahanService.getPenawaranOlahanById(idPenawaranOlahan);
        List<WarehouseModel> listItemEx = warehouseService.getListItem();

        model.addAttribute("penawaranOlahan", penawaranOlahan);
        model.addAttribute("listItemEx", listItemEx);
        return "penawaran-olahan/update-penawaran-olahan";
    }

    @PostMapping(value="/update", params = {"save"})
    private String updatePenawaranOlahanSubmit(@ModelAttribute PenawaranOlahanModel penawaranOlahan,
                                               Model model,
                                               RedirectAttributes redirectAttrs) {

        List<ItemPenawaranOlahanModel> listItem = penawaranOlahan.getListItemPenawaranOlahan();

        //check validation
        if(kontakEmpty(penawaranOlahan)){
            redirectAttrs.addFlashAttribute("failed",
                    "E-mail atau nomor telepon harus diisi");
            return "redirect:/penawaran-hasil-olahan/update/" + penawaranOlahan.getIdPenawaranOlahan();
        }
        else if(isDuplicate(listItem)){
            redirectAttrs.addFlashAttribute("failed",
                    "Perubahan tidak dapat dibuat karena terdapat duplikasi jenis item");
            return "redirect:/penawaran-hasil-olahan/update/" + penawaranOlahan.getIdPenawaranOlahan();
        }
        else if(itemValueInvalid(listItem)){
            redirectAttrs.addFlashAttribute("failed",
                    "Perubahan tidak dapat dibuat karena terdapat jenis yang tidak valid");
            return "redirect:/penawaran-hasil-olahan/update/" + penawaranOlahan.getIdPenawaranOlahan();
        }
        else if(quantityExceeded(listItem)){
            redirectAttrs.addFlashAttribute("failed",
                    "Perubahan tidak dapat dibuat karena berat melebihi kuantitas yang tersedia ");
            return "redirect:/penawaran-hasil-olahan/update/" + penawaranOlahan.getIdPenawaranOlahan();
        }

        //delete previous items in listItemPenawaranOlahan
        PenawaranOlahanModel penawaranOlahanEx = penawaranOlahanService.getPenawaranOlahanById(penawaranOlahan.getIdPenawaranOlahan());
        penawaranOlahanEx.getListItemPenawaranOlahan().removeAll(penawaranOlahanEx.getListItemPenawaranOlahan());
        penawaranOlahanService.deleteAllItem(penawaranOlahanEx);
        penawaranOlahanService.update(penawaranOlahan, false);

        redirectAttrs.addFlashAttribute("success",
                String.format("id %s berhasil diperbaharui", penawaranOlahan.getIdPenawaranOlahan()));

        model.addAttribute("totalBerat", getTotalBerat(listItem));
        model.addAttribute("penawaranOlahan", penawaranOlahan);
        return "penawaran-olahan/view-penawaran-olahan";
    }

    @PostMapping(value="/add", params = {"addRowItem"})
    private String addRowAddForm(@ModelAttribute PenawaranOlahanModel penawaranOlahan,
                                       Model model){

        //create list item with item quantity > 0
        List<WarehouseModel> listItemEx = warehouseService.getListItem();
        listItemEx.removeIf(item -> (item.getKuantitasOlahan() == 0));

        //get current list item
        List<ItemPenawaranOlahanModel> listItemPO = penawaranOlahan.getListItemPenawaranOlahan();

        //create new list
        if(listItemPO == null || listItemPO.size() == 0){
            penawaranOlahan.setListItemPenawaranOlahan(new ArrayList<>());
        }
        listItemPO.add(new ItemPenawaranOlahanModel());

        model.addAttribute("penawaranOlahan", penawaranOlahan);
        model.addAttribute("listItemEx", listItemEx);

        return "penawaran-olahan/add-penawaran-olahan";
    }

    @PostMapping(value = "/add", params = {"deleteRowItem"})
    private String deleteRowAddForm(@ModelAttribute PenawaranOlahanModel penawaranOlahan,
                                      @RequestParam("deleteRowItem") Integer row,
                                      Model model){
        final Integer rowId = Integer.valueOf(row);

        penawaranOlahan.getListItemPenawaranOlahan().remove(rowId.intValue());
        List<WarehouseModel> listItemEx = warehouseService.getListItem();

        model.addAttribute("penawaranOlahan", penawaranOlahan);
        model.addAttribute("listItemEx", listItemEx);

        return "penawaran-olahan/add-penawaran-olahan";
    }

    @PostMapping(value="/update", params = {"addRowItem"})
    private String addRowUpdateForm(@ModelAttribute PenawaranOlahanModel penawaranOlahan,
                                       Model model){

        //create list item with item quantity > 0
        List<WarehouseModel> listItemEx = warehouseService.getListItem();
        listItemEx.removeIf(item -> (item.getKuantitasOlahan() == 0));

        //get current list item
        List<ItemPenawaranOlahanModel> listItemPO = penawaranOlahan.getListItemPenawaranOlahan();

        //create new list
        if(listItemPO == null || listItemPO.size() == 0){
            penawaranOlahan.setListItemPenawaranOlahan(new ArrayList<>());
        }
        listItemPO.add(new ItemPenawaranOlahanModel());

        model.addAttribute("penawaranOlahan", penawaranOlahan);
        model.addAttribute("listItemEx", listItemEx);

        return "penawaran-olahan/update-penawaran-olahan";
    }

    @PostMapping(value = "/update", params = {"deleteRowItem"})
    private String deleteRowUpdateForm(@ModelAttribute PenawaranOlahanModel penawaranOlahan,
                                          @RequestParam("deleteRowItem") Integer row,
                                          Model model){
        final Integer rowId = Integer.valueOf(row);

        penawaranOlahan.getListItemPenawaranOlahan().remove(rowId.intValue());
        List<WarehouseModel> listItemEx = warehouseService.getListItem();

        model.addAttribute("penawaranOlahan", penawaranOlahan);
        model.addAttribute("listItemEx", listItemEx);

        return "penawaran-olahan/update-penawaran-olahan";
    }

    @GetMapping(value = "/delete/{idPenawaranOlahan}")
    private String deletePenawaranOlahan(@PathVariable String idPenawaranOlahan,
                                         RedirectAttributes redirectAttrs){

        PenawaranOlahanModel penawaranOlahan = penawaranOlahanService.getPenawaranOlahanById(idPenawaranOlahan);
        penawaranOlahanService.delete(penawaranOlahan);

        redirectAttrs.addFlashAttribute("success",
                String.format("Penawaran hasil olahan sampah dengan id %s berhasil dihapus",
                        penawaranOlahan.getIdPenawaranOlahan()));

        return "redirect:/penawaran-hasil-olahan/viewall/0";
    }

    @PostMapping(value = "/update-status")
    private String updateStatusPenawaranOlahan(@ModelAttribute PenawaranOlahanModel penawaranOlahan,
                                               RedirectAttributes redirectAttrs,
                                               Model model){


        PenawaranOlahanModel penawaranOlahanEx = penawaranOlahanService.getPenawaranOlahanById(penawaranOlahan.getIdPenawaranOlahan());
        List<ItemPenawaranOlahanModel> listItem = penawaranOlahanEx.getListItemPenawaranOlahan();

        //approval & status
        Boolean isApproved = penawaranOlahan.getKeteranganTolak() == null;
        Integer status = penawaranOlahanEx.getStatus();

        if(isApproved){
            if(status == 0){
                listItem = penawaranOlahan.getListItemPenawaranOlahan();
                int totalHarga = 0;
                for(ItemPenawaranOlahanModel item : listItem){
                    totalHarga += item.getHarga();
                }
                penawaranOlahanEx.setHarga(totalHarga);
                penawaranOlahanEx.setListItemPenawaranOlahan(listItem);
            }
            else if(status == 3){
                integrateWarehouseTransaksi(penawaranOlahanEx, true);
            }
            penawaranOlahanEx.setStatus(penawaranOlahanEx.getStatus()+1);
        } else {
            if(status == 0){
                penawaranOlahanEx.setKeteranganTolak(penawaranOlahan.getKeteranganTolak());
            } else if(status == 1){
                penawaranOlahanEx.setKeteranganTolak("Customer menolak penawaran harga");
            }
            penawaranOlahanEx.setStatus(5);
        }
        penawaranOlahanService.updateStatus(penawaranOlahanEx);

        redirectAttrs.addFlashAttribute("success",
                String.format("Status penawaran hasil olahan sampah dengan id %s berhasil diperbarui",
                        penawaranOlahan.getIdPenawaranOlahan()));

        model.addAttribute("listItem", listItem);
        model.addAttribute("penawaranOlahan", penawaranOlahanEx);
        return "redirect:/penawaran-hasil-olahan/view/" + penawaranOlahanEx.getIdPenawaranOlahan();
    }

    private Integer getTotalBerat(List<ItemPenawaranOlahanModel> listItem){
        int totalBerat = 0;
        for(int i = 0; i < listItem.size(); i++){
            totalBerat += listItem.get(i).getKuantitas();
        }
        return totalBerat;
    }

    private void integrateWarehouseTransaksi(PenawaranOlahanModel penawaranOlahan, Boolean isUpdate){

        List<ItemPenawaranOlahanModel> listItem = penawaranOlahan.getListItemPenawaranOlahan();

        //update warehouse
        int totalHarga = 0;
        for(ItemPenawaranOlahanModel item: listItem){
            totalHarga += item.getHarga();
            WarehouseModel itemWarehouse = warehouseService.getItemById(item.getIdItem().getIdItem());
            itemWarehouse.setKuantitasOlahan(itemWarehouse.getKuantitasOlahan()-item.getKuantitas());
            warehouseService.updateItem(itemWarehouse);
        }

        //update harga
        penawaranOlahan.setHarga(totalHarga);

        //update transaksi
        transaksiService.addTransaksiOlahan(penawaranOlahan, true);
        ProsesPenawaranOlahanModel transaksi = transaksiService.getTransaksiByPenawaranOlahan(penawaranOlahan);
        penawaranOlahan.setTransaksiOlahan(transaksi);

        if(isUpdate){
            penawaranOlahanService.updateStatus(penawaranOlahan);
        } else {
            penawaranOlahanService.add(penawaranOlahan, null);
        }
    }

    private boolean quantityExceeded(List<ItemPenawaranOlahanModel> listItem) {
        for(ItemPenawaranOlahanModel item : listItem){
            if(item.getKuantitas() > item.getIdItem().getKuantitasOlahan()){
                return true;
            }
        }
        return false;
    }

    private boolean isDuplicate(List<ItemPenawaranOlahanModel> listItem){
        Set<ItemPenawaranOlahanModel> duplicate = new HashSet<>();
        Set<ItemPenawaranOlahanModel> isDuplicate =  listItem.stream().filter(e -> !duplicate.add(e)).collect(Collectors.toSet());
        if (isDuplicate.size() > 0){
            return true;
        }
        return false;
    }

    private boolean itemValueInvalid(List<ItemPenawaranOlahanModel> listItem) {
        for(ItemPenawaranOlahanModel item:listItem){
            if(item.getIdItem() == null){
                return true;
            }
        }
        return false;
    }

    private boolean kontakEmpty(PenawaranOlahanModel penawaranOlahan) {
        if(penawaranOlahan.getEmail().isEmpty() && penawaranOlahan.getHp().isEmpty()){
            return true;
        }
        return false;
    }

    private CustomerModel getCustomer(HttpServletRequest request) {
        CustomerModel customer =  customerService.getCustomerByUsername(request.getRemoteUser()) == null ?
                null : customerService.getCustomerByUsername(request.getRemoteUser());
        return customer;
    }
}
