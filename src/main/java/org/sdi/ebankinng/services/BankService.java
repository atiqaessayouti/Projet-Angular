package org.sdi.ebankinng.services;

import jakarta.transaction.Transactional;
import org.sdi.ebankinng.entites.BankAccount;
import org.sdi.ebankinng.entites.CurrentAccount;
import org.sdi.ebankinng.entites.SavingAccount;
import org.sdi.ebankinng.repositores.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class BankService {

    @Autowired
    private BankAccountRepository bankAccountRepositorty;

    public void consulter() {

        Optional<BankAccount> optionalBankAccount =
                bankAccountRepositorty.findById("03ff74d7-ee32-41b3-8de5-0bf84cc84fe7");

        if (optionalBankAccount.isPresent()) {

            BankAccount bankAccount = optionalBankAccount.get();

            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getCustomer().getName());

            if (bankAccount instanceof CurrentAccount) {
                System.out.println("OverDraft => " + ((CurrentAccount) bankAccount).getOverDraft());

            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Rate => " + ((SavingAccount) bankAccount).getInterestRate());
            }

            bankAccount.getAccountOperations().forEach(op -> {
                System.out.println("============");
                System.out.println(op.getType());
                System.out.println(op.getOperationDate());
                System.out.println(op.getAmount());
            });
        }
    }
}