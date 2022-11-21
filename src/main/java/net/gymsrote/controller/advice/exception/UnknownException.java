package net.gymsrote.controller.advice.exception;

public class UnknownException extends RuntimeException {
	private static final long serialVersionUID = 3093643941124782543L;

	public UnknownException(String message) {
		super(message);
	}
}
