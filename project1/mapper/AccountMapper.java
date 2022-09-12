package ir.payeshgaran.project1.mapper;


import ir.payeshgaran.project1.dto.AccountDTO;
import ir.payeshgaran.project1.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class AccountMapper {


    private User user;
    private AccountDTO accountDTO;


    /*public User toEntity(AccountDTO accountDTO) {
        User user = new User();
        user.setAccountNumber(accountDTO.getAccountNumber());
        user.setAccountBalance(accountDTO.getBalance());
        user.setPassword(accountDTO.getPassword());
        return user;
    }*/

    public AccountDTO toDTO(User user) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(user.getAccountNumber());
        accountDTO.setBalance(user.getAccountBalance());
        accountDTO.setPassword(user.getPassword());
        return accountDTO;
    }

/*    public List<User> toEntities(List<AccountDTO> accountDTOS) {
        List<User> users = new ArrayList<>();
        for (AccountDTO dto : accountDTOS) {
            User user = new User();
            user.setAccountNumber(dto.getAccountNumber());
            user.setPassword(dto.getPassword());
            user.setAccountBalance(dto.getBalance());
            users.add(user);

        }
        return users;
    }*/

    public List<AccountDTO> toDTOS(List<User> users) {
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for (User user : users) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setAccountNumber(user.getAccountNumber());
            accountDTO.setPassword(user.getPassword());
            accountDTO.setBalance(user.getAccountBalance());
            accountDTOList.add(accountDTO);

        }
        return accountDTOList;
    }

}
