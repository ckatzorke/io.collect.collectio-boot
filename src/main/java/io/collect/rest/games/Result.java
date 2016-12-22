package io.collect.rest.games;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

/**
 * The generic json result
 *
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class Result<T> extends ResourceSupport implements Serializable {
	private static final long serialVersionUID = 2569196901438096359L;

	public final T result;

	public Result(T result) {
		this.result = result;
	}

}
