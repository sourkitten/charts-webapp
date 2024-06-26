package org.tardis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tardis.data.Country;

import java.util.List;

@Repository
public interface CountryDAO extends JpaRepository<Country, Long> {
    List<Country> findAll();
}
