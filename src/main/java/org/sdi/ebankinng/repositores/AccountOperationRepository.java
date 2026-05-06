package org.sdi.ebankinng.repositores;

import org.sdi.ebankinng.entites.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
