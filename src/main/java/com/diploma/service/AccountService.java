package com.diploma.service;

import com.diploma.domain.Account;
import com.diploma.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Account}.
 */
@Service
@Transactional
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Save a user.
     *
     * @param account the entity to save.
     * @return the persisted entity.
     */
    public Account save(Account account) {
        log.debug("Request to save User : {}", account);
        return accountRepository.save(account);
    }

    /**
     * Update a user.
     *
     * @param account the entity to save.
     * @return the persisted entity.
     */
    public Account update(Account account) {
        Account updated = Account.builder()
                .id(account.getId())
                .name(account.getName())
                .surname(account.getSurname())
                .email(account.getEmail())
                .birthDate(account.getBirthDate())
                .gender(account.getGender())
                .weight(account.getWeight())
                .build();
        log.debug("Request to save User : {}", account);
        return accountRepository.save(account);
    }

    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Account> findAll(Pageable pageable) {
        log.debug("Request to get all Users");
        return accountRepository.findAll(pageable);
    }

    /**
     * Get one user by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Account> findOne(Long id) {
        log.debug("Request to get User : {}", id);
        return accountRepository.findById(id);
    }

    /**
     * Delete the user by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);
        accountRepository.deleteById(id);
    }
}
