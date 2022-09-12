package ir.payeshgaran.project1.controller;

import ir.payeshgaran.project1.dto.TransactionDTO;
import ir.payeshgaran.project1.mapper.TransactionMapper;
import ir.payeshgaran.project1.model.User;
import ir.payeshgaran.project1.model.Transaction;
import ir.payeshgaran.project1.service.implementation.AccountServiceImplementation;
import ir.payeshgaran.project1.service.implementation.TransactionServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionServiceImplementation transactionService;
    @Autowired
    private AccountServiceImplementation accountService;



    @GetMapping("/")
    public String showForm(Model model) {
        TransactionDTO transaction = new TransactionDTO();
        model.addAttribute("transaction", transaction);
        return "form";
    }



    @PostMapping("/")
    public String transaction(@ModelAttribute("transaction") @Valid TransactionDTO transactionDTO, BindingResult result, RedirectAttributes redirAttrs) {

        if (result.hasErrors())
            return "form";

        double amount = transactionDTO.getAmount();
        User depositor = accountService.findUserByAccountNumber(loggedInAccountDetails());
        if (depositor == null) {
            redirAttrs.addFlashAttribute("error1", "Log in again.");
            return "redirect:/login";
        }
        User receiver = accountService.findUserByAccountNumber(transactionDTO.getReceiver());
        if (receiver == null) {
            redirAttrs.addFlashAttribute("error1", "Please fill in the blanks and try again.");
            return "redirect:/transaction/";
        }


        if (receiver.getAccountNumber() == loggedInAccountDetails()) {
            redirAttrs.addFlashAttribute("error1", "You can not transfer money to your account.");
            return "redirect:/transaction/";
        }


        if (depositor.getAccountBalance() < amount) {

            redirAttrs.addFlashAttribute("error1", "You don't have enough money!!");
            return "redirect:/transaction/";
        }
        TransactionMapper transactionMapper = new TransactionMapper(new Transaction(), new TransactionDTO(), accountService);

        transactionDTO.setDepositor(depositor.getAccountNumber());
        transactionService.save(transactionMapper.toEntity(transactionDTO), transactionDTO.getAmount());

        redirAttrs.addFlashAttribute("success", "Done!");
        return "redirect:/dashboard/";


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

