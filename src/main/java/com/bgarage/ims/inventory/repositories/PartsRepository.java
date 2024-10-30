package com.bgarage.ims.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bgarage.ims.inventory.entity.Parts;

public interface PartsRepository extends JpaRepository<Parts, Long> {

}
