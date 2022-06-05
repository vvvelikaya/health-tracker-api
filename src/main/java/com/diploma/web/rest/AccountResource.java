package com.diploma.web.rest;

import com.diploma.domain.Account;
import com.diploma.service.AccountService;
import com.diploma.web.rest.errors.ProcessException;
import com.diploma.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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
     * {@code POST  /users} : Create a new user.
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
     * {@code PUT  /users/:id} : Updates an existing user.
     *
     * @param id the id of the userDTO to save.
     * @param userDTO the userDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDTO,
     * or with status {@code 400 (Bad Request)} if the userDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(
//        @PathVariable(value = "id", required = false) final Long id,
//        @Valid @RequestBody UserDTO userDTO
//    ) throws URISyntaxException {
//        log.debug("REST request to update User : {}, {}", id, userDTO);
//        if (userDTO.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        if (!Objects.equals(id, userDTO.getId())) {
//            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
//        }
//
//        if (!userRepository.existsById(id)) {
//            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
//        }
//
//        UserDTO result = userService.update(userDTO);
//        return ResponseEntity
//            .ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDTO.getId().toString()))
//            .body(result);
//    }

    /**
     * {@code PATCH  /users/:id} : Partial updates given fields of an existing user, field will ignore if it is null
     *
     * @param id the id of the userDTO to save.
     * @param userDTO the userDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDTO,
     * or with status {@code 400 (Bad Request)} if the userDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
//    @PatchMapping(value = "/users/{id}", consumes = { "application/json", "application/merge-patch+json" })
//    public ResponseEntity<User> partialUpdateUser(
//        @PathVariable(value = "id", required = false) final Long id,
//        @NotNull @RequestBody UserDTO userDTO
//    ) throws URISyntaxException {
//        log.debug("REST request to partial update User partially : {}, {}", id, userDTO);
//        if (userDTO.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        if (!Objects.equals(id, userDTO.getId())) {
//            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
//        }
//
//        if (!userRepository.existsById(id)) {
//            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
//        }
//
//        Optional<UserDTO> result = userService.partialUpdate(userDTO);
//
//        return ResponseUtil.wrapOrNotFound(
//            result,
//            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDTO.getId().toString())
//        );
//    }

    /**
     * {@code GET  /users} : get all the users.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
     */
//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getAllUsers(
//        UserCriteria criteria,
//        @org.springdoc.api.annotations.ParameterObject Pageable pageable
//    ) {
//        log.debug("REST request to get Users by criteria: {}", criteria);
//        Page<UserDTO> page = userQueryService.findByCriteria(criteria, pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }

    /**
     * {@code GET  /users/count} : count all the users.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
//    @GetMapping("/count")
//    public ResponseEntity<Long> countUsers(UserCriteria criteria) {
//        log.debug("REST request to count Users by criteria: {}", criteria);
//        return ResponseEntity.ok().body(userQueryService.countByCriteria(criteria));
//    }

    /**
     * {@code GET  /users/:id} : get the "id" user.
     *
     * @param id the id of the userDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getUser(@PathVariable Long id) {
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
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User : {}", id);
        accountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .build();
    }
}
