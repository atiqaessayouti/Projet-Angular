package org.sdi.ebankinng.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("CUR")
public class CurrentAccount extends BankAccount{

    private double overDraft;

}

