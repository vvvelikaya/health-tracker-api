package com.diploma.web.rest;

import com.diploma.domain.Account;
import com.diploma.service.AccountService;
import com.diploma.service.SearchParameters;
import com.diploma.service.dto.SearchParametersDTO;
import com.diploma.web.rest.errors.ProcessException;
import com.diploma.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link Account}.
 */
@RestController
@RequestMapping(value = "/api/accounts", consumes = "application/json", produces = "application/json")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);
    private static final String ENTITY_NAME = "account";
    private final AccountService accountService;

    public AccountResource(final AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * {@code POST  /} : Create a new user.
     *
     * @param account the user to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDTO, or with status {@code 400 (Bad Request)} if the user has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping
    public ResponseEntity<Account> createUser(@Valid @RequestBody Account account) throws URISyntaxException {
        log.debug("REST request to save User : {}", account);
        if (account.getId() != null) {
            throw new ProcessException("A new user cannot already have an ID", HttpStatus.BAD_REQUEST);
        }
        Account result = accountService.save(account);
        return ResponseEntity
            .created(new URI("/api/users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /:id} : Updates an existing user.
     *
     * @param id the id of the userDTO to save.
     * @param account the userDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDTO,
     * or with status {@code 400 (Bad Request)} if the userDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Account account ) {
        log.debug("REST request to update User : {}, {}", id, account);
        if (account.getId() == null) {
            throw new ProcessException("Null id for " + ENTITY_NAME, HttpStatus.BAD_REQUEST);
        }
        if (!Objects.equals(id, account.getId())) {
            throw new ProcessException("Invalid id for" + ENTITY_NAME, HttpStatus.BAD_REQUEST);
        }
        if (!accountService.existsById(id)) {
            throw new ProcessException("Entity not found" + ENTITY_NAME, HttpStatus.BAD_REQUEST);
        }
        Account result = accountService.update(account);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, account.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /} : get all the users.
     *
     * @param searchParametersDTO the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
     */
    @GetMapping
    public ResponseEntity<Page<Account>> getAllAccounts(@RequestBody SearchParametersDTO searchParametersDTO) {
        log.debug("REST request to get Accounts");
        SearchParameters searchParameters = searchParametersDTO.convertSearchParamsRequestToSearchParams();
        Page<Account> page = accountService.findAll(searchParameters);
        return ResponseEntity.ok(page);
    }

    /**
     * {@code GET  /users/:id} : get the "id" user.
     *
     * @param id the id of the userDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        Optional<Account> user = accountService.findOne(id);
        return user.map(item -> ResponseEntity.ok().body(item)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /users/:id} : delete the "id" user.
     *
     * @param id the id of the userDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        log.debug("REST request to delete User : {}", id);
        accountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .build();
    }
}
