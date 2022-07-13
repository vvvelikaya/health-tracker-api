package com.diploma.service;

import com.diploma.domain.User;
import com.diploma.repository.UserRepository;
import com.diploma.service.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link User}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Save a user.
     *
     * @param user the entity to save.
     * @return the persisted entity.
     */
    public User create(final User user) {
        log.debug("Request to save User : {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean existsById(final Long id) {
        return userRepository.existsById(id);
    }

    /**
     * Update a user.
     *
     * @param user the entity to save.
     * @return the persisted entity.
     */
    public User update(final User user) {
        User updated = User.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .weight(user.getWeight())
                .build();
        log.debug("Request to save User : {}", updated);
        return userRepository.save(updated);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.getUserByEmail(principal.toString());
    }

    /**
     * Get all the users.
     *
     * @param searchParameters the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<User> getAllUsers(SearchParameters searchParameters) {
        log.debug("Request to get all Users");
        UserSpecification spec = new UserSpecification(searchParameters);
        Pageable pageable = PageRequest.of(searchParameters.getOffset(), searchParameters.getLimit());
        return userRepository.findAll(spec, pageable);
    }

    /**
     * Get one user by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<User> getById(Long id) {
        log.debug("Request to get User : {}", id);
        return userRepository.findById(id);
    }

    /**
     * Delete the user by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);
        userRepository.deleteById(id);
    }
}
