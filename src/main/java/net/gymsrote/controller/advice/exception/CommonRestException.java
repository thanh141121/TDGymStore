package net.gymsrote.controller.advice.exception;

public class CommonRestException extends RuntimeException {

	private static final long serialVersionUID = -4977677411506771568L;

	public CommonRestException(String message) {
		super(message);
	}

}