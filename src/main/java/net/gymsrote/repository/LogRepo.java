package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.gymsrote.entity.Log;

@Repository
public interface LogRepo extends JpaRepository<Log, Long>{//, LogRepoCustom {
	
}
