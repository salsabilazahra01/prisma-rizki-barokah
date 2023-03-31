package propensi.project.water.controller;

import lombok.extern.slf4j.Slf4j;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import propensi.project.water.model.User.ManajerModel;
import propensi.project.water.model.User.UserModel;
import propensi.project.water.model.User.Role;
import propensi.project.water.setting.Settings;
import propensi.project.water.setting.xml.Attributes;
import propensi.project.water.setting.xml.ServiceResponse;
import propensi.project.water.service.ManajerService;
import propensi.project.water.service.UserService;

@Slf4j
@Controller
public class HomeController {
    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private ManajerService manajerService;

    @Autowired
    private UserService userService;

    private WebClient webClient = WebClient.builder().build();

    @RequestMapping("/")
    public String homeAfterLogin() {
        for (UserModel user : userService.getListUser()) {
            if (!user.getRole().equals(Role.MANAJER)) {
                ManajerModel manajer = new ManajerModel();
                manajer.setNama("Manajer Perusahaan");
                manajer.setRole(Role.MANAJER);
                manajer.setEmail("water@gmail.com");
                manajer.setHp("08123456789");
                manajer.setUsername("manajerwater");
                manajer.setPassword("Test1234!");

                userService.addUser(manajer);
            }
        }

        return "home";
    }

    @RequestMapping("/home")
    public String home() {
        for (UserModel user : userService.getListUser()) {
            if (!user.getRole().equals(Role.MANAJER)) {
                ManajerModel manajer = new ManajerModel();
                manajer.setNama("Manajer Perusahaan");
                manajer.setRole(Role.MANAJER);
                manajer.setEmail("water@gmail.com");
                manajer.setHp("08123456789");
                manajer.setUsername("manajerwater");
                manajer.setPassword("Test1234!");

                userService.addUser(manajer);
            }
        }

        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        for (UserModel user : userService.getListUser()) {
            if (!user.getRole().equals(Role.MANAJER)) {
                ManajerModel manajer = new ManajerModel();
                manajer.setNama("Manajer Perusahaan");
                manajer.setRole(Role.MANAJER);
                manajer.setEmail("water@gmail.com");
                manajer.setHp("08123456789");
                manajer.setUsername("manajerwater");
                manajer.setPassword("Test1234!");

                userService.addUser(manajer);
            }
        }

        return "login";
    }

//    @GetMapping("/validate-ticket")
//    public ModelAndView adminLogin(@RequestParam(value = "ticket", required = false) String ticket, HttpServletRequest request) {
//        ServiceResponse serviceResponse = this.webClient.get().uri(
//                String.format(Settings.SERVER_VALIDATE_TICKET, ticket, Settings.CLIENT_LOGIN)
//        ).retrieve().bodyToMono(ServiceResponse.class).block();
//
//        Attributes attributes = serviceResponse.getAuthenticationSuccess().getAttributes();
//        String username = serviceResponse.getAuthenticationSuccess().getUser();
//
//        UserModel user = userService.getUserByUsername(username);
//
//        if (user == null) {
//            user = new UserModel();
//            user.setEmailHp(username + "@ui.ac.id");
//            user.setNama(attributes.getNama());
//            user.setPassword("propensi123");
//            user.setUsername(username);
//            user.setRole(Role.ADMIN);
//            userService.addUser(user);
//        }
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(username, "propensi123");
//
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        securityContext.setAuthentication(authentication);
//
//        HttpSession httpSession = request.getSession(true);
//        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
//
//        return new ModelAndView("redirect:/home");
//    }
//
//    @GetMapping(value = "/login-admin")
//    public ModelAndView loginAdmin() {
//        return new ModelAndView("redirect:" + Settings.SERVER_LOGIN + Settings.CLIENT_LOGIN);
//    }
//
//    @GetMapping(value = "/logout-admin")
//    public ModelAndView logoutSSO(Principal principal) {
//        UserModel user = userService.getUserByUsername(principal.getName());
//
//        return new ModelAndView("redirect:" + Settings.SERVER_LOGOUT + Settings.CLIENT_LOGOUT);
//    }

    @GetMapping(value = "/logout")
    public ModelAndView logout(Principal principal) {
        UserModel user = userService.getUserByUsername(principal.getName());

        return new ModelAndView("redirect:" + Settings.SERVER_LOGOUT + Settings.CLIENT_LOGOUT);
    }

    @GetMapping(value = "/register")
    public String registrasiUser() {
        return  "manajemen-user/registrasi-user";
    }

    @RequestMapping("/user/viewall")
    private String manajemenUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        UserModel userModel = userService.getUserByUsername(username);

        if (userModel.getRole().equals(Role.ADMIN)) {
            return "manajemen-user/menu";
        }

        return "/home";
    }
}
