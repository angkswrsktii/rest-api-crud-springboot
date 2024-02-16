package com.example.restapi.adapter.persistence.repository;

import com.example.restapi.adapter.persistence.entity.Kendaraan;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface KendaraanRepository extends JpaRepository<Kendaraan, UUID> {
    @Query(value = "SELECT * FROM kendaraan WHERE kendaraan_id = ?1", nativeQuery = true)
    List<Kendaraan> findByKendaraanId(String kendaraanId);

    @Query(value = "SELECT * FROM kendaraan", nativeQuery = true)
    List<Kendaraan> findAllKendaraan();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM kendaraan WHERE kendaraan_id = ?1", nativeQuery = true)
    void deleteByKendaraanId(String kendaraanId);
}
