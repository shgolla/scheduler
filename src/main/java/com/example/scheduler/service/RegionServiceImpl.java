/**
 * 
 */
package com.example.scheduler.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scheduler.entity.Country;
import com.example.scheduler.entity.Panel;
import com.example.scheduler.entity.Region;
import com.example.scheduler.exception.ResourceNotFoundException;
import com.example.scheduler.repository.CountryRepository;
import com.example.scheduler.repository.PanelRepository;
import com.example.scheduler.repository.RegionRepository;

/**
 * Service implementation to provide CRUD operations on Region
 * @author sgolla
 *
 */
@Service
public class RegionServiceImpl implements RegionService{
	
	@Autowired
	private RegionRepository regionRepo;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private PanelRepository panelRepo;
	
	/**
	 * Creates Region
	 * @param region
	 * @return Region
	 */
	@Override
	public Region createRegion(Region region) {
		Country countryFromDB = null;
		if (region.getCountry().getId() != 0) {
			countryFromDB = countryRepo.getOne(region.getCountry().getId());
		}
		if (countryFromDB != null) {
			region.setCountry(countryFromDB);
		}
		return regionRepo.save(region);
	}
	
	/**
	 * Get all Regions
	 * @return List<Region>
	 */
	@Override
	public List<Region> getAllRegions() {
		return regionRepo.findAll();
	}
	
	/**
	 * Get all panels by a region id
	 * @param regionId
	 * @return set of Panels
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Set<Panel> getPanelsByRegion(Long regionId) throws ResourceNotFoundException {
		
		return regionRepo.findById(regionId)
				.orElseThrow(() -> new ResourceNotFoundException("Region not found by Id "+regionId)).getPanels();
		
	}
	
	/**
	 * Read Panel from a region
	 * @param regionId
	 * @return Panel
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Panel getPanelFromRegion(Long regionId, Long panelId) throws ResourceNotFoundException {
		return findPanelByRegion(regionId, panelId);
	}

	/**
	 * Find Panel by given panel id and region id
	 * @param regionId
	 * @param panelId
	 * @return Panel
	 * @throws ResourceNotFoundException
	 */
	private Panel findPanelByRegion(Long regionId, Long panelId) throws ResourceNotFoundException {
		Region foundRegion = findRegionById(regionId);
		return foundRegion.getPanels().stream().filter(panel -> panel.getId() == panelId).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Panel Id is not found "+panelId));
	}
	
	/**
	 * Delete Panel from a region
	 * @param regionId
	 * @throws ResourceNotFoundException
	 */
	@Override
	public void deletePanelFromRegion(Long regionId, Long panelId) throws ResourceNotFoundException {
		
		Panel foundPanel = findPanelByRegion(regionId, panelId);
		panelRepo.delete(foundPanel);
	}
	
	/**
	 * Create panel in a region id
	 * @param regionId
	 * @param panel
	 * @return set of Panels
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Region createPanelInARegion(Long regionId, Panel panel) throws ResourceNotFoundException {
		Region foundRegion = findRegionById(regionId);
		foundRegion.addPanel(panel);
		return regionRepo.save(foundRegion);
		
	}

	/**
	 * Find region by Id
	 * @param regionId
	 * @return Region
	 * @throws ResourceNotFoundException
	 */
	private Region findRegionById(Long regionId) throws ResourceNotFoundException {
		Region foundRegion =  regionRepo.findById(regionId)
				.orElseThrow(() -> new ResourceNotFoundException("Region not found by Id "+regionId));
		return foundRegion;
	}

}
