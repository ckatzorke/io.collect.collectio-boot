package io.collect.rest.games;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;

/**
 * The generic json result
 *
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class Result extends ResourceSupport implements Serializable {
	private static final long serialVersionUID = 2569196901438096359L;

	public final Map<String, Object> result = new LinkedHashMap<String, Object>();


	public void addResultObject(String key, Object object) {
		result.put(key, object);
	}
}
