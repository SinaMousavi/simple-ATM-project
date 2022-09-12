package ir.payeshgaran.project1.controller;

import ir.payeshgaran.project1.dto.AccountDTO;
import ir.payeshgaran.project1.mapper.AccountMapper;
import ir.payeshgaran.project1.model.User;
import ir.payeshgaran.project1.service.implementation.AccountServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class LoginController {

    @Autowired
    private AccountServiceImplementation accountService;


    @GetMapping("/")
    public String getAccounts(Model model) {
        AccountMapper accountMapper = new AccountMapper(new User(), new AccountDTO());
        List<AccountDTO> user = accountMapper.toDTOS(accountService.findAll());
        model.addAttribute("users", user);

        return "home";
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String username = loggedInAccountDetails();
        model.addAttribute("username", username);

        return "dashboard";
    }


    public String loggedInAccountDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
