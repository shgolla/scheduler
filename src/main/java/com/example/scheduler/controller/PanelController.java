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

	@GetMapping("/panel/{id}")
	private Panel getPanelById(@Valid @PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		return panelRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find Panel with Id "+id));
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
	private Boolean deletePanel(@Valid @PathVariable(value= "id") Long panelId) throws ResourceNotFoundException{
		Panel panel = panelRepo.findById(panelId)
				.orElseThrow(() -> new ResourceNotFoundException("Panel Not Found with Id " + panelId));
		panelRepo.delete(panel);
		return true;
	}

}
