package ir.payeshgaran.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {

    private String depositor;

    @NotBlank(message = "Receiver can not be empty")
    @Size(min = 2,message = "Receiver must be at least 2 character")
    private String receiver;

    @Min(value = 20 , message = "Transaction amount must be at least 20")
    private double amount;
}
