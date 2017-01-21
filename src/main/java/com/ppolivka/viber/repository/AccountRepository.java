package com.ppolivka.viber.repository;

import com.ppolivka.viber.model.Account;
import org.springframework.data.repository.CrudRepository;


public interface AccountRepository extends CrudRepository<Account, Integer> {

    Account findByName(String name);

}
