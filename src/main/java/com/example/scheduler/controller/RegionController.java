/**
 * 
 */
package com.example.scheduler.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.scheduler.entity.Panel;
import com.example.scheduler.entity.Region;
import com.example.scheduler.exception.ResourceNotFoundException;
import com.example.scheduler.service.RegionService;
import com.sun.istack.NotNull;

/**
 * @author sgolla
 *
 */
@RestController
@RequestMapping(produces = {"application/json", "application/vnd.example.scheduler.v1+json"})
public class RegionController {
	
	@Autowired
	RegionService regionService;
	
	/**
	 * Creates Region
	 * @param region
	 * @return Region
	 */
	@PostMapping("/regions")
	public Region createRegion(@Valid @RequestBody Region region) {
		return regionService.createRegion(region);
	}
	
	/**
	 * Get all Regions
	 * @return List<Region>
	 */
	@GetMapping("/regions")
	public List<Region> getAllRegions() {
		return regionService.getAllRegions();
	}
	
	/**
	 * Get all panels by a region id
	 * @param regionId
	 * @return set of Panels
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/regions/{id}/panels")
	public Set<Panel> getPanelsByRegion(@NotNull @PathVariable(value = "id") Long regionId) throws ResourceNotFoundException {
		return regionService.getPanelsByRegion(regionId);
		
	}
	
	/**
	 * Read Panel from a region
	 * @param regionId
	 * @return Panel
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/regions/{regionId}/panels/{panelId}")
	public Panel getPanelFromRegion(@NotNull @PathVariable(value = "regionId") Long regionId, 
			@NotNull @PathVariable(value = "panelId") Long panelId) throws ResourceNotFoundException {
		
		return regionService.getPanelFromRegion(regionId, panelId);
		
	}
	
	
	/**
	 * Delete Panel from a region
	 * @param regionId
	 * @throws ResourceNotFoundException
	 */
	@DeleteMapping("/regions/{regionId}/panels/{panelId}")
	public void deletePanelFromRegion(@NotNull @PathVariable(value = "regionId") Long regionId, 
			@NotNull @PathVariable(value = "panelId") Long panelId) throws ResourceNotFoundException {
		
		regionService.deletePanelFromRegion(regionId, panelId);
		
	}
	
	/**
	 * Create panel in a region id
	 * @param regionId
	 * @return set of Panels
	 * @throws ResourceNotFoundException
	 */
	@PostMapping("/regions/{id}/panels")
	public Region createPanelInARegion(@NotNull @PathVariable(value = "id") Long regionId, @Valid @RequestBody Panel panel) throws ResourceNotFoundException {
		
		return regionService.createPanelInARegion(regionId, panel);
		
	}
	
	

}
