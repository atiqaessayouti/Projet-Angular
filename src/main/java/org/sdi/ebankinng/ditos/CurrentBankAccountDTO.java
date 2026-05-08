package org.sdi.ebankinng.ditos;

import java.util.Date;
import lombok.Data;
import org.sdi.ebankinng.enums.AccountStatus;

@Data
public class CurrentBankAccountDTO extends BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;
}
