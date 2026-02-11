package com.greenportal.monitoting.repository;

import com.greenportal.monitoting.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByName(String name);
}
