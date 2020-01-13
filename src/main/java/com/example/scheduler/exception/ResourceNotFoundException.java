/**
 * 
 */
package com.example.scheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author sgolla
 *
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4628093275291638992L;

	public ResourceNotFoundException (String msg) {
		super(msg);
	}

}
