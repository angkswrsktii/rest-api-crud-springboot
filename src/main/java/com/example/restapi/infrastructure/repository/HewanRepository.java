package com.example.restapi.infrastructure.repository;

import com.example.restapi.infrastructure.entity.Hewan;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HewanRepository extends CrudRepository<Hewan, UUID> {
    @Query(value = "SELECT * FROM hewan WHERE hewan_id = ?1", nativeQuery = true)
    List<Hewan> findByHewanId(String hewanId);

    @Query(value = "SELECT * FROM hewan", nativeQuery = true)
    List<Hewan> findAllHewan();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM hewan WHERE hewan_id = ?1", nativeQuery = true)
    void deleteByHewanId(String hewanId);
}
