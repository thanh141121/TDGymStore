package net.gymsrote.controller.advice.exception;

public class LoginException extends RuntimeException{
	private static final long serialVersionUID = 7625182990309036949L;

	public LoginException(String message) {
		super(message);
	}
}
