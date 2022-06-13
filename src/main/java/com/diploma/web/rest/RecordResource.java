package com.diploma.web.rest;

import com.diploma.domain.Record;
import com.diploma.service.RecordService;
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
 * REST controller for managing {@link Record}.
 */
@RestController
@RequestMapping(value = "/api/records")
@RequiredArgsConstructor
public class RecordResource {
    private final Logger log = LoggerFactory.getLogger(RecordResource.class);
    private static final String ENTITY_NAME = "record";
    private final RecordService recordService;

    /**
     * {@code POST  /} : Create a new record.
     *
     * @param record the record to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultDTO, or with status {@code 400 (Bad Request)} if the record has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping
    public ResponseEntity<Record> createUser(@Valid @RequestBody Record record) throws URISyntaxException {
        log.debug("REST request to save Record : {}", record);
        if (record.getId() != null) {
            throw new ProcessException("A new record cannot already have an ID", HttpStatus.BAD_REQUEST);
        }
        Record result = recordService.create(record);
        return ResponseEntity
                .created(new URI("/api/records/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /:id} : Updates an existing record.
     *
     * @param id the id of the resultDTO to save.
     * @param record the recordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recordDTO,
     * or with status {@code 400 (Bad Request)} if the recordDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recordDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Record> updateUser(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Record record) {
        log.debug("REST request to update Record : {}, {}", id, record);
        if (record.getId() == null) {
            throw new ProcessException("Null id for " + ENTITY_NAME, HttpStatus.BAD_REQUEST);
        }
        if (!Objects.equals(id, record.getId())) {
            throw new ProcessException("Invalid id for" + ENTITY_NAME, HttpStatus.BAD_REQUEST);
        }
        if (!recordService.existsById(id)) {
            throw new ProcessException("Entity not found" + ENTITY_NAME, HttpStatus.BAD_REQUEST);
        }
        Record result = recordService.update(record);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, record.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /} : get all the records.
     *
     * @param searchParametersDTO the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of records in body.
     */
    @GetMapping
    public ResponseEntity<Page<Record>> getAllRecords(@RequestBody SearchParametersDTO searchParametersDTO) {
        log.debug("REST request to get Records");
        SearchParameters searchParameters = searchParametersDTO.convertSearchParamsRequestToSearchParams();
        Page<Record> page = recordService.getAllRecords(searchParameters);
        return ResponseEntity.ok(page);
    }

    /**
     * {@code GET  /:id} : get the "id" record.
     *
     * @param id the id of the recordDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recordDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Record> getRecord(@PathVariable Long id) {
        log.debug("REST request to get Record : {}", id);
        Optional<Record> record = recordService.getById(id);
        return record.map(item -> ResponseEntity.ok().body(item)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /:id} : delete the "id" record.
     *
     * @param id the id of the recordDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        log.debug("REST request to delete Record : {}", id);
        recordService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }
}
