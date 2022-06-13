package com.diploma.service;

import com.diploma.domain.Record;
import com.diploma.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Record}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final Logger log = LoggerFactory.getLogger(RecordService.class);
    private final RecordRepository recordRepository;

    /**
     * Save a record.
     *
     * @param record the entity to save.
     * @return the persisted entity.
     */
    public Record create(final Record record) {
        log.debug("Request to save Record : {}", record);
        return recordRepository.save(record);
    }

    public boolean existsById(final Long id) {
        return recordRepository.existsById(id);
    }

    /**
     * Update a record.
     *
     * @param record the entity to save.
     * @return the persisted entity.
     */
    public Record update(final Record record) {
        Record updated = Record.builder()
                .id(record.getId())
                .heartRate(record.getHeartRate())
                .sugarLevel(record.getSugarLevel())
                .dateCreated(record.getDateCreated())
                .dateUpdated(record.getDateUpdated())
                .build();
        log.debug("Request to save Record : {}", updated);
        return recordRepository.save(updated);
    }

    /**
     * Get all the records.
     *
     * @param searchParameters the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Record> getAllRecords(SearchParameters searchParameters) {
        log.debug("Request to get all Records");
        Pageable pageable = PageRequest.of(searchParameters.getOffset(), searchParameters.getLimit());
        return recordRepository.findAll(pageable);
    }

    /**
     * Get one record by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Record> getById(Long id) {
        log.debug("Request to get Record : {}", id);
        return recordRepository.findById(id);
    }

    /**
     * Delete the record by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Record : {}", id);
        recordRepository.deleteById(id);
    }
}
