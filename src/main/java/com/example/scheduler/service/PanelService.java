/**
 * 
 */
package com.example.scheduler.service;

import java.util.List;

import com.example.scheduler.entity.Panel;
import com.example.scheduler.entity.Restriction;
import com.example.scheduler.exception.ResourceNotFoundException;

/**
 * Service Interface to operate on Panels
 * @author sgolla
 *
 */
public interface PanelService {
	
	/**
	 * Find Panel By Id
	 * @param id
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Panel findPanelById(Long id) throws ResourceNotFoundException;
	
	/**
	 * Get all Panels
	 * @return List<Panel>
	 */
	public List<Panel> getAllPanels();
	
	/**
	 * Save Panel
	 * @param panel
	 * @return
	 */
	public Panel savePanel(Panel panel);

	/**
	 * Delete Panel
	 * @param panelId
	 * @return boolean
	 * @throws ResourceNotFoundException
	 */
	public Boolean deletePanel(Long panelId) throws ResourceNotFoundException;
	
	/**
	 * Read Restriction from Panel
	 * @param panelId
	 * @param restrictionId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Restriction getRestrictionFromPanel(Long panelId, Long restrictionId) throws ResourceNotFoundException;
	
	/**
	 * Add Restriction to Panel.
	 * Ignores Restriction if already exists in Panel
	 * @param panelId
	 * @param Restriction
	 * @return Restriction
	 * @throws ResourceNotFoundException
	 */
	public Panel addRestrictionToPanel(Long panelId, Restriction restriction) throws ResourceNotFoundException;
	
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
	public Panel modifyRestrictionInAPanel(Long panelId,
			Long restrictionId,
			Restriction inputRestriction) throws ResourceNotFoundException;
	
	/**
	 * Delete Restriction from a Panel.
	 * If the restriction is associated to other Panels, they will be impacted as well.
	 * 
	 * @param panelId
	 * @param restrictionId
	 * @return Restriction
	 * @throws ResourceNotFoundException
	 */
	public void deleteRestrictionFromAPanel(Long panelId, Long restrictionId) throws ResourceNotFoundException;

}
