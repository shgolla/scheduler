/**
 * 
 */
package com.example.scheduler.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scheduler.entity.Panel;
import com.example.scheduler.entity.Restriction;
import com.example.scheduler.exception.ResourceNotFoundException;
import com.example.scheduler.repository.PanelRepository;

/**
 * Panel service Implementation with methods to do CRUD operations on Panel and Restrictions
 * @author sgolla
 *
 */
@Service
public class PanelServiceImpl  implements PanelService{

	@Autowired
	PanelRepository panelRepo;
	
	/**
	 * Find Panel by Id
	 * @param id
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Panel findPanelById(Long id) throws ResourceNotFoundException {
		return panelRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find Panel with Id " + id));
	}

	/**
	 * Get all Panels
	 */
	@Override
	public List<Panel> getAllPanels() {
		return panelRepo.findAll();
	}

	/**
	 * Save Panel
	 * @param panel obj
	 */
	@Override
	public Panel savePanel(Panel panel) {
		return panelRepo.save(panel);
	}
	
	/**
	 * Delete Panel
	 * @param panelId
	 * @return Boolean
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Boolean deletePanel(Long panelId) throws ResourceNotFoundException {
		Panel panel = findPanelById(panelId);
		panelRepo.delete(panel);
		return true;
	}
	
	/**
	 * Read Restriction from Panel
	 * @param panelId
	 * @param restrictionId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Restriction getRestrictionFromPanel(Long panelId, Long restrictionId) throws ResourceNotFoundException{
		Panel panel = findPanelById(panelId);
		return findRestrictionById(restrictionId, panel);
	}
	
	/**
	 * Add Restriction to Panel.
	 * Ignores Restriction if already exists in Panel
	 * @param panelId
	 * @param Restriction
	 * @return Restriction
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Panel addRestrictionToPanel(Long panelId, Restriction restriction) throws ResourceNotFoundException{
		Panel panel = findPanelById(panelId);
		Set<Restriction> restrictions = panel.getRestrictions();
		if (restrictions.stream().filter(rest -> rest.getType().equalsIgnoreCase(restriction.getType())).count() == 0) {
			restrictions.add(restriction);
		}
		return panelRepo.save(panel);
	}
	
	/**
	 * Update Restriction in a Panel.
	 * If the restriction is associated to other Panels, they will be impacted as well.
	 * To add new Restriction
	 * @param panelId
	 * @param restrictionId
	 * @param Restriction
	 * @return Restriction
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Panel modifyRestrictionInAPanel(Long panelId,
			Long restrictionId,
			Restriction inputRestriction) throws ResourceNotFoundException{
		
		Panel panel = findPanelById(panelId);
		Restriction foundRestriction = findRestrictionById(restrictionId, panel);
		foundRestriction.setType(inputRestriction.getType());

		return panelRepo.save(panel);
	}
	
	/**
	 * Delete Restriction from a Panel.
	 * If the restriction is associated to other Panels, they will be impacted as well.
	 * 
	 * @param panelId
	 * @param restrictionId
	 * @return Restriction
	 * @throws ResourceNotFoundException
	 */
	public void deleteRestrictionFromAPanel(Long panelId, Long restrictionId) throws ResourceNotFoundException{
		
		Panel panel = findPanelById(panelId);
		Restriction foundRestriction = findRestrictionById(restrictionId, panel);
		panel.getRestrictions().remove(foundRestriction);
		panelRepo.save(panel);

	}

	/**
	 * Find Restriction By Id
	 * @param restrictionId
	 * @param panel
	 * @return Restriction
	 * @throws ResourceNotFoundException
	 */
	public Restriction findRestrictionById(Long restrictionId, Panel panel) throws ResourceNotFoundException {
		Restriction foundRestriction = panel.getRestrictions().stream()
		.filter(restriction -> restriction.getId() == restrictionId)
		.findFirst()
		.orElseThrow(() -> new ResourceNotFoundException("Restriction not found with Id "+restrictionId));
		return foundRestriction;
	}

}
