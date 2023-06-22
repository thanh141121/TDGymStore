package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.gymsrote.entity.Config;

@Repository
public interface ConfigRepo extends JpaRepository<Config, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Config c SET c.isSelected = false")
    Integer unSelected();

    @Modifying
    @Transactional
    @Query("UPDATE Config c SET c.isSelected = true WHERE c.id = ?1")
    Integer selected(Long id);
}
