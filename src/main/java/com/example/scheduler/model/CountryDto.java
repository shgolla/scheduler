/**
 * 
 */
package com.example.scheduler.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * model representing Country table
 * @author sgolla
 *
 */
public class CountryDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1506422445994535609L;

	private long id;
	
	private String name;
	private Set<RegionDto> regions = new HashSet<>();

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
	public Set<RegionDto> getRegions() {
		return regions;
	}

	/**
	 * Maintains Relationship consistency
	 * @param regions the regions to set
	 */
	public void addRegion(RegionDto region) {
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
	public void removeRegion(RegionDto region) {
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
		CountryDto other = (CountryDto) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	

}
