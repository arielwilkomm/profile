package br.com.profile.user.exceptions;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserException() {
    }

    public UserException(String msg) {
	    super(msg);
    }
	
}
