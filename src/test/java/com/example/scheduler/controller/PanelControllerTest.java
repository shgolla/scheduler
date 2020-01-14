/**
 * 
 */
package com.example.scheduler.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.scheduler.entity.Country;
import com.example.scheduler.entity.Panel;
import com.example.scheduler.entity.Region;
import com.example.scheduler.entity.Restriction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author sgolla
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PanelControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	private String getRootUrl() {
		return "http://localhost:" + port;
	}
	
	/**
	 * Test to get and verify that Panels API is returning existing Panels
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void shouldReturnAllPanels_WhenRequestedForGetPanelsAPI() throws JsonMappingException, JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Accept", "application/vnd.example.scheduler.v1+json");
		HttpEntity<String> httpEntity = new HttpEntity(null, httpHeaders);
		ResponseEntity<String>  response = restTemplate.exchange(getRootUrl() + "/panels", HttpMethod.GET, httpEntity, String.class);
		assertNotNull(response.getBody());
		assertTrue(response.getStatusCode() == HttpStatus.OK);
	}
	
	/**
	 * Test that Panels API errors out when requested for non existing or unsupported API version
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Test
	public void shouldErrorOut_WhenRequestedForGetPanelsAPIV2() throws JsonMappingException, JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Accept", "application/vnd.example.scheduler.v2+json");
		HttpEntity<String> httpEntity = new HttpEntity(null, httpHeaders);
		ResponseEntity<String>  response = restTemplate.exchange(getRootUrl() + "/panels", HttpMethod.GET, httpEntity, String.class);
		assertNotNull(response.getBody());
		assertTrue(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Test to create and verify a Panel object is created properly with its relations
	 * like Region, Country, Restrictions
	 */
	@Test
	public void shouldCreatePanel_WhenNewPanelIsPosted() {
		
		Panel panel = getMockPanelObject();
		
		ResponseEntity<Panel> res = restTemplate.postForEntity(getRootUrl()+"/panels", panel, Panel.class);
		Panel resultPanel = res.getBody();
		
		//Ensure all objects are created by testing Id is not Null
		assertNotNull(resultPanel);
		assertNotNull(resultPanel.getId());
		resultPanel.getRestrictions().forEach( restr ->
					assertNotNull(restr.getId()));
		assertNotNull(resultPanel.getRegion().getId());
		
	}
	
	/**
	 * Test to add a restriction to a panel when a panel id is given
	 */
	@Test
	public void shouldAddRestriction_WhenAPanelIdIsGiven() {
		
		Panel panel = getMockPanelObject();
		
		ResponseEntity<Panel> res = restTemplate.postForEntity(getRootUrl()+"/panels", panel, Panel.class);
		Panel resultPanel = res.getBody();
		
		//Ensure all objects are created by testing Id is not Null
		assertNotNull(resultPanel);
		assertNotNull(resultPanel.getId());
		Long panelId = resultPanel.getId();
		int restrictionSizeBeforeUpdate = resultPanel.getRestrictions().size();
		
		//Add restriction to a Panel
		ResponseEntity<Panel> response = restTemplate.postForEntity(getRootUrl()+"/panels/"+panelId+"/restrictions",
				getMockRestriction("UnHealthyAd"), Panel.class);
		
		Panel updatedPanel = response.getBody();
		//Check Panel id returned is same
		assertNotNull(updatedPanel);
		assertNotNull(updatedPanel.getId());
		assertTrue(resultPanel.getId() == updatedPanel.getId());
		
		//check new restriction is added to the panel
		int restrictionSizeAfterUpdate = updatedPanel.getRestrictions().size();
		assertTrue(restrictionSizeAfterUpdate == restrictionSizeBeforeUpdate + 1);
		//Check all restrictions have primary key created
		updatedPanel.getRestrictions().forEach(restr -> assertNotNull(restr.getId()));
		
		
	}
	

	/**
	 * Returns mock Panel object
	 * @return
	 */
	private Panel getMockPanelObject() {
		//Create Panel
		Panel panel = new Panel();
		panel.setName("Panel1");
		panel.setCreatedBy("admin");
		panel.setUpdatedBy("admin");
		
		Restriction restriction = getMockRestriction("Healthy Ad");
		Set<Restriction> restrictions = new HashSet<>();
		restrictions.add(restriction);
		
		//Set Restrictions
		panel.setRestrictions(restrictions);
		
		Region region = new Region();
		region.setName("south");
		
		//Set Country
		Country country = new Country();
		country.setName("UK");
		region.setCountry(country);
		
		//Set Region
		panel.setRegion(region);
		return panel;
	}

	/**
	 * @return
	 */
	private Restriction getMockRestriction(String type) {
		//Create Restriction
		Restriction restriction = new Restriction();
		restriction.setType(type);
		restriction.setCreatedBy("admin");
		restriction.setUpdatedBy("admin");
		return restriction;
	}
	
	

}
