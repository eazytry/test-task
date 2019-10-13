package ru.domclick.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.domclick.model.Account;

import javax.persistence.EntityManager;

@Repository
public interface AccountDao extends CrudRepository<Account, Long> {
}
