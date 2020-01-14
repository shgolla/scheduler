/**
 * 
 */
package com.example.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scheduler.entity.Country;

/**
 * @author sgolla
 *
 */
public interface CountryRepository extends JpaRepository<Country, Long>{
	


}
