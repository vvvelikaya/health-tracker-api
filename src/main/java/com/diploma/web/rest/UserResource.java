package com.diploma.web.rest;

import com.diploma.domain.User;
import com.diploma.service.UserService;
import com.diploma.service.SearchParameters;
import com.diploma.service.dto.SearchParametersDTO;
import com.diploma.web.rest.errors.ProcessException;
import com.diploma.web.rest.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
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
 * REST controller for managing {@link User}.
 */
@RestController
@RequestMapping(value = "/api/users", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);
    private static final String ENTITY_NAME = "account";
    private final UserService userService;

    /**
     * {@code POST  /} : Create a new user.
     *
     * @param user the user to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDTO, or with status {@code 400 (Bad Request)} if the user has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {
        log.debug("REST request to save User : {}", user);
        if (user.getId() != null) {
            throw new ProcessException("A new user cannot already have an ID", HttpStatus.BAD_REQUEST);
        }
        User result = userService.create(user);
        return ResponseEntity
            .created(new URI("/api/users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /:id} : Updates an existing user.
     *
     * @param id the id of the userDTO to save.
     * @param user the userDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDTO,
     * or with status {@code 400 (Bad Request)} if the userDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody User user) {
        log.debug("REST request to update User : {}, {}", id, user);
        if (user.getId() == null) {
            throw new ProcessException("Null id for " + ENTITY_NAME, HttpStatus.BAD_REQUEST);
        }
        if (!Objects.equals(id, user.getId())) {
            throw new ProcessException("Invalid id for" + ENTITY_NAME, HttpStatus.BAD_REQUEST);
        }
        if (!userService.existsById(id)) {
            throw new ProcessException("Entity not found" + ENTITY_NAME, HttpStatus.BAD_REQUEST);
        }
        User result = userService.update(user);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, user.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /} : get all the users.
     *
     * @param searchParametersDTO the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
     */
    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(@RequestBody SearchParametersDTO searchParametersDTO) {
        log.debug("REST request to get Accounts");
        SearchParameters searchParameters = searchParametersDTO.convertSearchParamsRequestToSearchParams();
        Page<User> page = userService.findAll(searchParameters);
        return ResponseEntity.ok(page);
    }

    /**
     * {@code GET  /:id} : get the "id" user.
     *
     * @param id the id of the userDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        Optional<User> user = userService.findById(id);
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
        userService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .build();
    }
}
