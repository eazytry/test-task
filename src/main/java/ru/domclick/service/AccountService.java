package ru.domclick.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.domclick.dao.AccountDao;
import ru.domclick.exception.AccountNotExistException;
import ru.domclick.exception.NotEnoughMoneyException;
import ru.domclick.model.Account;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class AccountService {
    private AccountDao accountDao;

    @Autowired
    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account writeOffMoneyFromAccount(Long accountId, Double amount) {
        Account account = accountDao.findById(accountId).orElseThrow(
                ()->new AccountNotExistException(String.format("Account with id = '%s' is not exists!", accountId)));
        BigDecimal subtraction = account.getBalance().
                subtract(BigDecimal.valueOf(amount)).
                setScale(2, RoundingMode.HALF_UP);
        if(subtraction.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new NotEnoughMoneyException(
                    String.format("Account balance with id = '%s' is less than amount '%s'", accountId, amount));
        }
        account.setBalance(subtraction);
        return accountDao.save(account);
    }

    public Account depositMoneyToAccount(Long accountId, Double amount) {
        Account account = accountDao.findById(accountId).
                orElseThrow(()->new AccountNotExistException(String.format("Account with id = '%s' is not exists!", accountId)));
        BigDecimal convertedAmount = new BigDecimal(amount).
                add(account.getBalance()).
                setScale(2, RoundingMode.HALF_UP);
        account.setBalance(convertedAmount);
        return accountDao.save(account);
    }

    public Account transferMoneyBetweenAccounts(Long writeOffAccountId, Long depositAccountId, Double amount) {
        Account account = writeOffMoneyFromAccount(writeOffAccountId, amount);
        depositMoneyToAccount(depositAccountId, amount);
        return account;
    }
}
