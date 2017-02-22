package io.collect.games.resourcesimport;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class ImporterException extends RuntimeException {

	private static final long serialVersionUID = -4095359329921471996L;

	/**
	 * @param msg
	 * @param e
	 */
	public ImporterException(String msg, Throwable t) {
		super(msg, t);
	}

}
