package io.collect.games.rest;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

/**
 * Simple error reponse, to be serialized by json
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class ErrorResponseEntity extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = -3284409804970516777L;

	private final String error;

	public ErrorResponseEntity(String error) {
		this.error = error;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}
}
