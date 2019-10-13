package ru.domclick.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.domclick.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountRestController {
    private AccountService accountService;

    @Autowired
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/subtract")
    public ResponseEntity<?> writeOffMoneyFromAccount(@RequestParam Long accountId,
                                                      @RequestParam Double amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount can't be less than zero!");
        }
        return new ResponseEntity<>(accountService.writeOffMoneyFromAccount(accountId, amount), HttpStatus.OK);
    }

    @PostMapping(value = "/deposit")
    public ResponseEntity<?> depositMoneyToAccount(@RequestParam Long accountId,
                                                   @RequestParam Double amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount can't be less than zero!");
        }
        return new ResponseEntity<>(accountService.depositMoneyToAccount(accountId, amount), HttpStatus.OK);
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<?> transferMoneyBetweenAccounts(@RequestParam Long writeOffAccountId,
                                                          @RequestParam Long depositAccountId,
                                                          @RequestParam Double amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount can't be less than zero!");
        }
        return new ResponseEntity<>(
                accountService.transferMoneyBetweenAccounts(writeOffAccountId, depositAccountId, amount), HttpStatus.OK);
    }
}
