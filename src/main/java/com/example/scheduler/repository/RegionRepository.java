/**
 * 
 */
package com.example.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scheduler.entity.Region;

/**
 * @author sgolla
 *
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

}
