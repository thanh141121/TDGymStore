package net.gymsrote.controller.advice.exception;

@SuppressWarnings("serial")
public class DataConflictException extends RuntimeException {
	public DataConflictException(String message) {
		super(message);
	}
}
