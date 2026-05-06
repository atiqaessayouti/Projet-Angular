package org.sdi.ebankinng.repositores;

import org.sdi.ebankinng.entites.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}