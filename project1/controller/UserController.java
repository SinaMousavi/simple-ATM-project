package ir.payeshgaran.project1.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.payeshgaran.project1.dto.AccountDTO;
import ir.payeshgaran.project1.dto.TransactionDTO;
import ir.payeshgaran.project1.mapper.AccountMapper;
import ir.payeshgaran.project1.mapper.TransactionMapper;
import ir.payeshgaran.project1.model.User;
import ir.payeshgaran.project1.model.Transaction;
import ir.payeshgaran.project1.service.implementation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AccountServiceImplementation accountService;

    @Autowired
    private TransactionServiceImplementation transactionService;


    @GetMapping("/accountBalance")
    public String getAccountBalance(Model model) {
        AccountMapper accountMapper = new AccountMapper(new User(), new AccountDTO());
        User user = accountService.findUserByAccountNumber(loggedInAccountDetails());
        double accountBalance = accountMapper.toDTO(user).getBalance();
        model.addAttribute("accountBalance", accountBalance);
        return "accountBalance";
    }

//    @GetMapping("/token/refresh")
//    public void refreshToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (request.getServletPath().equals("/login") || request.getServletPath().equals("/token/refresh")) {
//            filterChain.doFilter(request, response);
//        } else {
//            String authorizationHeader = request.getHeader(AUTHORIZATION);
//            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                try {
//                    String refresh_token = authorizationHeader.substring("Bearer ".length());
//                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//                    JWTVerifier verifier = JWT.require(algorithm).build();
//                    DecodedJWT decodedJWT = verifier.verify(refresh_token);
//                    String username = decodedJWT.getSubject();
//                    User user = accountService.findUserByAccountNumber(username);
//                    String access_token = JWT.create()
//                            .withSubject(user.getAccountNumber())
//                            .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
//                            .withIssuer(request.getRequestURL().toString())
//                            .withClaim("accountBalance", user.getAccountBalance())
//                            .sign(algorithm);
//                    Map<String, String> tokens = new HashMap<>();
//                    tokens.put("access_token", access_token);
//                    tokens.put("refresh_token", refresh_token);
//                    response.setContentType(APPLICATION_JSON_VALUE);
//                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//                } catch (Exception e) {
//                    response.setHeader("error", e.getMessage());
//                    response.setStatus(FORBIDDEN.value());
//                    Map<String, String> error = new HashMap<>();
//                    error.put("Error Message", e.getMessage());
//                    response.setContentType(APPLICATION_JSON_VALUE);
//                    new ObjectMapper().writeValue(response.getOutputStream(), error);
//                }
//            } else {
//                throw new RuntimeException("token is missing");
//            }
//        }
//    }

    @GetMapping("/transaction")
    public String myTransactions(Model model) {
        Long userId = accountService.findUserByAccountNumber(loggedInAccountDetails()).getId();

        TransactionMapper transactionMapper = new TransactionMapper(new Transaction(), new TransactionDTO(), accountService);
        List<TransactionDTO> transactionDTOS = transactionMapper.toDTOS(transactionService.getUserTransactions(userId));
        model.addAttribute("transactions", transactionDTOS);
        model.addAttribute("accountBalance", accountService.findUserByAccountNumber(loggedInAccountDetails()).getAccountBalance());

        return "transactions";
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
