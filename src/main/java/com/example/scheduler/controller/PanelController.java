/**
 * 
 */
package com.example.scheduler.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.scheduler.entity.Panel;
import com.example.scheduler.entity.Restriction;
import com.example.scheduler.exception.ResourceNotFoundException;
import com.example.scheduler.service.PanelService;

/**
 * Pnael Controller class to serve Panel resource
 * @author sgolla
 *
 */
@RestController
@RequestMapping(produces = {"application/json", "application/vnd.example.scheduler.v1+json"})
public class PanelController {

	/** Panel service */
	@Autowired
	PanelService panelService;

	/**
	 * Get Panel by Id
	 * @param id
	 * @return Panel
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/panels/{id}")
	private Panel getPanelById(@NotNull @PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		return panelService.findPanelById(id);
	}

	/**
	 * Get all Panels
	 * @return List<Panel>
	 */
	@GetMapping("/panels")
	private List<Panel> getPanels() {
		return panelService.getAllPanels();
	}

	/**
	 * Create new Panel
	 * @param panel
	 * @return Panel
	 */
	@PostMapping("/panels")
	private Panel createPanel(@Valid @RequestBody Panel panel) {
		return panelService.savePanel(panel);
	}
	
	/**
	 * Delete Panel by Id
	 * @param panelId
	 * @return Boolean
	 * @throws ResourceNotFoundException
	 */
	@DeleteMapping("/panels/{id}")
	private Boolean deletePanel(@NotNull @PathVariable(value = "id") Long panelId) throws ResourceNotFoundException {
		return panelService.deletePanel(panelId);
	}

	/**
	 * Update given Panel
	 * @param panelId
	 * @param inputPanel
	 * @return Panel
	 * @throws ResourceNotFoundException
	 */
	@PutMapping("/panels/{id}")
	private Panel modifyPanel(@NotNull @PathVariable(value = "id") Long panelId, @Valid @RequestBody Panel inputPanel)
			throws ResourceNotFoundException {

		Panel panel = panelService.findPanelById(panelId);
		panel.setName(inputPanel.getName());
		panel.setRestrictions(inputPanel.getRestrictions());
		return panelService.savePanel(panel);
	}

	

	/**
	 * Read Restriction from Panel
	 * @param panelId
	 * @param restrictionId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/panels/{panelId}/restrictions/{restrictionId}")
	private Restriction getRestrictionFromPanel(@NotNull @PathVariable(value = "panelId") Long panelId, @NotNull @PathVariable(value = "restrictionId") Long restrictionId) throws ResourceNotFoundException{
		return panelService.getRestrictionFromPanel(panelId, restrictionId);
				
	}
	
	/**
	 * Add Restriction to Panel.
	 * Ignores Restriction if already exists in Panel
	 * @param panelId
	 * @param Restriction
	 * @return Restriction
	 * @throws ResourceNotFoundException
	 */
	@PostMapping("/panels/{panelId}/restrictions")
	private Panel addRestrictionToPanel(@NotNull @PathVariable(value = "panelId") Long panelId, @Valid @RequestBody Restriction restriction) throws ResourceNotFoundException{
		return panelService.addRestrictionToPanel(panelId, restriction);
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
	@PutMapping("/panels/{panelId}/restrictions/{restrictionId}")
	private Panel modifyRestrictionInAPanel(@NotNull @PathVariable(value = "panelId") Long panelId,
			@NotNull @PathVariable(value="restrictionId") Long restrictionId,
			@Valid @RequestBody Restriction inputRestriction) throws ResourceNotFoundException{

		return panelService.modifyRestrictionInAPanel(panelId, restrictionId, inputRestriction);
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
	@DeleteMapping("/panels/{panelId}/restrictions/{restrictionId}")
	private void deleteRestrictionFromAPanel(@NotNull @PathVariable(value = "panelId") Long panelId, 
			@NotNull @PathVariable(value = "restrictionId") Long restrictionId) throws ResourceNotFoundException{
		panelService.deleteRestrictionFromAPanel(panelId, restrictionId);

	}
	
	
	

}
