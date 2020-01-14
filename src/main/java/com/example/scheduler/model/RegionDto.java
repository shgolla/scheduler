/**
 * 
 */
package com.example.scheduler.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Model mapping a Region table that represents a combination of Region and country
 * @author sgolla
 *
 */
public class RegionDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7889274307325563135L;
	
	private long id;
	
	private String name;
	
	private CountryDto country;
	private Set<PanelDto> panels = new HashSet<>();

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the country
	 */
	public CountryDto getCountry() {
		return country;
	}

	/**
	 * Maintains Relationship consistency
	 * @param country the country to set
	 */
	public void setCountry(CountryDto country) {
		
		//this means country is already set from other side of bidirection
		if(country == null || (this.country != null && this.country.equals(country))) {
			return;
		}
		CountryDto oldCountry = this.country;
		this.country = country;
		if (oldCountry != null) {
			oldCountry.removeRegion(this);
		}
		country.addRegion(this);
	}

	/**
	 * @return the panels
	 */
	public Set<PanelDto> getPanels() {
		return panels;
	}

	/**
	 * Maintains Relationship consistency
	 * @param panels the panels to set
	 */
	public void addPanel(PanelDto panel) {
		if(panels.contains(panel)) {
			return;
		}
		panels.add(panel);
		panel.setRegion(this);
	}
	
	/**
	 * Remove Panel from a region
	 * @param panel
	 */
	public void removePanel(PanelDto panel) {
		if (!panels.contains(panel)) {
			return;
		}
		panels.remove(panel);
		panel.setRegion(null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegionDto other = (RegionDto) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	

}
