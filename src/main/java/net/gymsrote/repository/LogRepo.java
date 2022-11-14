package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gymsrote.entity.Log;
import net.gymsrote.service.NeedImpl.LogRepoCustom;

public interface LogRepo extends JpaRepository<Log, Long>, LogRepoCustom {
	
}
