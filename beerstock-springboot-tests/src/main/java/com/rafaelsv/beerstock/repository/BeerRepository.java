package com.rafaelsv.beerstock.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rafaelsv.beerstock.model.Beer;

public interface BeerRepository extends JpaRepository<Beer, Long> {
    Optional<Beer> findByNameIgnoreCase(String name);
}
