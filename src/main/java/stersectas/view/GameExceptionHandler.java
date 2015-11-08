package stersectas.view;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import stersectas.application.game.GameNotFoundException;

@ControllerAdvice
public class GameExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameExceptionHandler.class);

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(GameNotFoundException.class)
	public void handleGameNotFound(HttpServletRequest request) {
		LOGGER.info("Game not found.");
	}

}