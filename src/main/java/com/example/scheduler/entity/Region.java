/**
 * 
 */
package com.example.scheduler.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity mapping a location table that represents a combination of Region and country
 * @author sgolla
 *
 */
@Entity
@Table(name="Region")
public class Region implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7889274307325563135L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@NotBlank(message = "Region name cannot be empty")
	@Column(name = "name")
	private String name;
	
	@NotNull(message = "Country cannot be null")
	@ManyToOne( cascade= CascadeType.ALL)
	@JoinColumn(name = "country_id")
	private Country country;
	
	@JsonIgnore
	@OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
	private Set<Panel> panels = new HashSet<>();

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
	public Country getCountry() {
		return country;
	}

	/**
	 * Maintains Relationship consistency
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		
		//this means country is already set from other side of bidirection
		if(country == null || (this.country != null && this.country.equals(country))) {
			return;
		}
		Country oldCountry = this.country;
		this.country = country;
		if (oldCountry != null) {
			oldCountry.removeRegion(this);
		}
		country.addRegion(this);
	}

	/**
	 * @return the panels
	 */
	public Set<Panel> getPanels() {
		return panels;
	}

	/**
	 * Maintains Relationship consistency
	 * @param panels the panels to set
	 */
	public void addPanel(Panel panel) {
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
	public void removePanel(Panel panel) {
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
		Region other = (Region) obj;
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
