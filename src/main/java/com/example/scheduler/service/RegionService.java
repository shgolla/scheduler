/**
 * 
 */
package com.example.scheduler.service;

import java.util.List;
import java.util.Set;

import com.example.scheduler.entity.Panel;
import com.example.scheduler.entity.Region;
import com.example.scheduler.exception.ResourceNotFoundException;

/**
 * Service to provide CRUD operations on Region
 * @author sgolla
 *
 */
public interface RegionService {
	
	/**
	 * Creates Region
	 * @param region
	 * @return Region
	 */
	public Region createRegion(Region region);
	
	/**
	 * Get all Regions
	 * @return List<Region>
	 */
	public List<Region> getAllRegions();
	
	/**
	 * Get all panels by a region id
	 * @param regionId
	 * @return set of Panels
	 * @throws ResourceNotFoundException
	 */
	public Set<Panel> getPanelsByRegion(Long regionId) throws ResourceNotFoundException;
	
	/**
	 * Read Panel from a region
	 * @param regionId
	 * @return Panel
	 * @throws ResourceNotFoundException
	 */
	public Panel getPanelFromRegion(Long regionId, Long panelId) throws ResourceNotFoundException;
	
	/**
	 * Delete Panel from a region
	 * @param regionId
	 * @throws ResourceNotFoundException
	 */
	public void deletePanelFromRegion(Long regionId, Long panelId) throws ResourceNotFoundException;
	
	/**
	 * Create panel in a region id
	 * @param regionId
	 * @param panel
	 * @return set of Panels
	 * @throws ResourceNotFoundException
	 */
	public Region createPanelInARegion(Long regionId, Panel panel) throws ResourceNotFoundException;
	

}
