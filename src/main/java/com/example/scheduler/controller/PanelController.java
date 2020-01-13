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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.scheduler.entity.Panel;
import com.example.scheduler.entity.Restriction;
import com.example.scheduler.exception.ResourceNotFoundException;
import com.example.scheduler.repository.PanelRepository;

/**
 * @author sgolla
 *
 */
@RestController
public class PanelController {

	@Autowired
	PanelRepository panelRepo;

	@GetMapping("/panels/{id}")
	private Panel getPanelById(@Valid @PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		return panelRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find Panel with Id " + id));
	}

	@GetMapping("/panels")
	private List<Panel> getPanels() {
		return panelRepo.findAll();
	}

	@PostMapping("/panels")
	private Panel createPanel(@Valid @RequestBody Panel panel) {
		return panelRepo.save(panel);
	}

	@PutMapping("/panels/{id}")
	private Panel modifyPanel(@PathVariable(value = "id") Long panelId, @Valid @RequestBody Panel inputPanel)
			throws ResourceNotFoundException {

		Panel panel = panelRepo.findById(panelId)
				.orElseThrow(() -> new ResourceNotFoundException("Panel Not Found with Id " + panelId));

		panel.setName(inputPanel.getName());
		panel.setRestrictions(inputPanel.getRestrictions());
		return panelRepo.save(panel);
	}

	@DeleteMapping("/panels/{id}")
	private Boolean deletePanel(@Valid @PathVariable(value = "id") Long panelId) throws ResourceNotFoundException {
		Panel panel = panelRepo.findById(panelId)
				.orElseThrow(() -> new ResourceNotFoundException("Panel Not Found with Id " + panelId));
		panelRepo.delete(panel);
		return true;
	}

	/**
	 * Read Restriction from Panel
	 * @param id
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/panels/{panelId}/restrictions/{restrictionId}")
	private Restriction getRestrictionFromPanel(@Valid @PathVariable(value = "panelId") Long panelId, @Valid @PathVariable(value = "restrictionId") Long restrictionId) throws ResourceNotFoundException{
		return panelRepo.findById(panelId)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find Panel with Id "+panelId))
				.getRestrictions().stream()
				.filter(restriction -> restriction.getId() == restrictionId).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException(
						"Restriction Not found with Id "+restrictionId));
				
	}
	
	/**
	 * Add Restriction to Panel.
	 * Ignores Restriction if already exists in Panel
	 * @param panelId
	 * @param restrictionId
	 * @return Restriction
	 * @throws ResourceNotFoundException
	 */
	@PostMapping("/panels/{panelId}/restrictions")
	private Panel addRestrictionToPanel(@Valid @PathVariable(value = "panelId") Long panelId, @Valid @RequestBody Restriction restriction) throws ResourceNotFoundException{
		Panel panel = panelRepo.findById(panelId)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find Panel with Id "+panelId));
		 
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
	 * @return Restriction
	 * @throws ResourceNotFoundException
	 */
	@PutMapping("/panels/{panelId}/restrictions/{restrictionId}")
	private Panel modifyRestrictionInAPanel(@Valid @PathVariable(value = "panelId") Long panelId,
			@Valid @PathVariable(value="restrictionId") Long restrictionId,
			@Valid @RequestBody Restriction inputRestriction) throws ResourceNotFoundException{
		Panel panel = panelRepo.findById(panelId)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find Panel with Id "+panelId));
		
		Restriction foundRestriction = panel.getRestrictions().stream()
		.filter(restriction -> restriction.getId() == restrictionId)
		.findFirst()
		.orElseThrow(() -> new ResourceNotFoundException("Restriction not found with Id "+restrictionId));
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
	@DeleteMapping("/panels/{panelId}/restrictions/{restrictionId}")
	private void deleteRestrictionFromAPanel(@Valid @PathVariable(value = "panelId") Long panelId, 
			@Valid @PathVariable(value = "restrictionId") Long restrictionId) throws ResourceNotFoundException{
		Panel panel = panelRepo.findById(panelId)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find Panel with Id "+panelId));
		
		Restriction foundRestriction = panel.getRestrictions().stream()
		.filter(restriction -> restriction.getId() == restrictionId)
		.findFirst()
		.orElseThrow(() -> new ResourceNotFoundException("Restriction not found with Id "+restrictionId));
		
		panel.getRestrictions().remove(foundRestriction);
		
		panelRepo.save(panel);

	}
	
	
	

}
