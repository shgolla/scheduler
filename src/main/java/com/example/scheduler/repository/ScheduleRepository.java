/**
 * 
 */
package com.example.scheduler.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.scheduler.entity.Schedule;

/**
 * Repository resource bean that exposes CRUD operations
 * @author sgolla
 *
 */
@RepositoryRestResource(collectionResourceRel = "schedules", path = "schedules")
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long>{
	
}
