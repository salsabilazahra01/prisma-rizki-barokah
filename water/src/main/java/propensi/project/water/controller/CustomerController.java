package propensi.project.water.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import propensi.project.water.model.User.CustomerModel;
import propensi.project.water.model.User.Role;
import propensi.project.water.model.User.UserModel;
import propensi.project.water.service.UserService;
import propensi.project.water.service.CustomerService;

@Slf4j
@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Qualifier("customerServiceImpl")
    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/add")
    public String addCustomerForm(Model model) {
        CustomerModel customer = new CustomerModel();
        model.addAttribute("customer", customer);

        return "customer/form-add-customer";
    }

    @PostMapping(value = "/add")
    public String addCustomerSubmit(@ModelAttribute CustomerModel customer, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String password = customer.getPassword();
        String passwordConfirmer = request.getParameter("passwordConfirmer");

        for (UserModel user : userService.getListUser()) {
            if (user.getUsername().equals(customer.getUsername())) {
                return "customer/failed-add-customer";
            }
        }

        if ((!userService.verifyPassword(password)) || (!userService.matchPassword(password, passwordConfirmer))) {
            return "customer/failed-add-customer";
        }

        customer.setRole(Role.CUSTOMER);
        customerService.addCustomer(customer);
        model.addAttribute("customer", customer);

        return "customer/success-add-customer";
    }

    @GetMapping("/viewall")
    public String viewAllCustomer(Model model) {
        model.addAttribute("listCustomer", customerService.getListCustomer());

        return "customer/viewall-customer";
    }
}
