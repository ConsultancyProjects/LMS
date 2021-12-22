package com.lms.tutor.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2662838815649189811L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}

