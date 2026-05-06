package org.sdi.ebankinng;

import org.sdi.ebankinng.entites.*;
import org.sdi.ebankinng.enums.OperationType;
import org.sdi.ebankinng.repositores.AccountOperationRepository;
import org.sdi.ebankinng.repositores.BankAccountRepository;
import org.sdi.ebankinng.repositores.CustomerRepository;
import org.sdi.ebankinng.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankinngApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankinngApplication.class, args);
    }

    // ✅ FIXED CommandLineRunner for service
    @Bean
    CommandLineRunner commandLineRunner(BankService bankService) {
        return args -> {
            bankService.consulter();
        };
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {

        return args -> {

            // =======================
            // CREATE CUSTOMERS
            // =======================
            Stream.of("Hassan", "Yassine", "Aicha").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });

            // =======================
            // CREATE ACCOUNTS
            // =======================
            customerRepository.findAll().forEach(cust -> {

                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random() * 90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(90000);

                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random() * 90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5);

                bankAccountRepository.save(savingAccount);
            });

            // =======================
            // CREATE OPERATIONS
            // =======================
            bankAccountRepository.findAll().forEach(acc -> {

                for (int i = 0; i < 5; i++) {

                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 1000);

                    accountOperation.setType(
                            Math.random() > 0.5
                                    ? OperationType.CREDIT
                                    : OperationType.DEBIT
                    );

                    accountOperation.setBankAccount(acc);

                    accountOperationRepository.save(accountOperation);
                }

                System.out.println("\n====================");
                System.out.println("ACCOUNT DETAILS");
                System.out.println("====================");

                System.out.println("ID => " + acc.getId());
                System.out.println("Balance => " + acc.getBalance());
                System.out.println("Status => " + acc.getStatus());
                System.out.println("Created At => " + acc.getCreatedAt());
                System.out.println("Customer => " + acc.getCustomer().getName());

                if (acc instanceof CurrentAccount current) {
                    System.out.println("OverDraft => " + current.getOverDraft());

                } else if (acc instanceof SavingAccount saving) {
                    System.out.println("Rate => " + saving.getInterestRate());
                }

                acc.getAccountOperations().forEach(op -> {
                    System.out.println("------------");
                    System.out.println("Type => " + op.getType());
                    System.out.println("Date => " + op.getOperationDate());
                    System.out.println("Amount => " + op.getAmount());
                });
            });
        };
    }
}