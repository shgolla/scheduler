/**
 * 
 */
package com.example.scheduler.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity representing Country table
 * @author sgolla
 *
 */
@Entity
@Table(name="Country")
public class Country implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1506422445994535609L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@NotBlank(message = "Country name cannot be empty")
	@Column(name="name")
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "country")
	private Set<Region> regions = new HashSet<>();

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
	 * @return the regions
	 */
	public Set<Region> getRegions() {
		return regions;
	}

	/**
	 * Maintains Relationship consistency
	 * @param regions the regions to set
	 */
	public void addRegion(Region region) {
		if(regions.contains(region)) {
			return;
		}
		regions.add(region);
		region.setCountry(this);
	}
	
	/**
	 * Maintains Relationship consistency
	 * @param region
	 */
	public void removeRegion(Region region) {
		//Prevent endless loop
		if (!regions.contains(region)) {
			return;
		}
		regions.remove(region);
		region.setCountry(null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Country other = (Country) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	

}
