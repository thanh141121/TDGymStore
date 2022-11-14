package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gymsrote.entity.MediaResource;

public interface CloudResourceRepo extends JpaRepository<MediaResource, Long> {}
