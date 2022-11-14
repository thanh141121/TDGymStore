package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gymsrote.entity.CloudResource;

public interface CloudResourceRepo extends JpaRepository<CloudResource, Long> {}
