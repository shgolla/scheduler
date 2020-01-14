/**
 * 
 */
package com.example.scheduler.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.scheduler.entity.Advertisement;

/**
 * Repository resource bean that exposes CRUD operations
 * @author sgolla
 *
 */
@RepositoryRestResource(collectionResourceRel = "advertisements", path = "advertisements")
public interface AdvertisementRepository extends PagingAndSortingRepository<Advertisement, Long>{
	
	    List<Advertisement> findByName(@Param("name") String name);
	    

}
