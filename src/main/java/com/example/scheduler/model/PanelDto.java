/**
 * 
 */
package com.example.scheduler.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Model to represent Panel
 * @author sgolla
 *
 */
public class PanelDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6895235830601130677L;

	private long id;
	
	private String name;
	
	private Set<RestrictionDto> restrictions = new HashSet<>() ;
	
	private RegionDto region;
	
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;

    private String updatedBy;


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
	 * @return the restrictions
	 */
	public Set<RestrictionDto> getRestrictions() {
		return restrictions;
	}

	/**
	 * @param restrictions the restrictions to set
	 */
	public void setRestrictions(Set<RestrictionDto> restrictions) {
		this.restrictions = restrictions;
	}

	@Override
	public String toString() {
		return "Panel [id=" + id + ", name=" + name + "]";
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the region
	 */
	public RegionDto getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(RegionDto region) {
		if (region == null || (this.region != null && this.region.equals(region))) {
			return;
		}
		RegionDto oldRegion = this.region;
		if(oldRegion != null) {
			oldRegion.removePanel(this);
		}
		this.region = region;
		region.addPanel(this);
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
		PanelDto other = (PanelDto) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	

}
