/**
 * 
 */
package com.example.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scheduler.entity.Panel;

/**
 * @author sgolla
 *
 */
@Repository
public interface PanelRepository extends JpaRepository<Panel, Long>{
	
}
