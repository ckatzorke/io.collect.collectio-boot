package io.collect.games.rest;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@ControllerAdvice(basePackages = { "io.collect.games.rest" })
public class GamesControllerAdvice {

	public static final String CONTEXT_HTTP_STATUS = "HTTP_STATUS";

	@ExceptionHandler(ContextedRuntimeException.class)
	public ResponseEntity<ErrorResponseEntity> notFound(ContextedRuntimeException e) {
		ErrorResponseEntity error = new ErrorResponseEntity(e.getMessage());
		return new ResponseEntity<ErrorResponseEntity>(error, (HttpStatus) e.getFirstContextValue(CONTEXT_HTTP_STATUS));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponseEntity> lastLine(Exception e) {
		ErrorResponseEntity error = new ErrorResponseEntity(e.getMessage());
		return new ResponseEntity<ErrorResponseEntity>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
