package br.com.profile.user.exceptions;

public class ExceptionResponse extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ExceptionResponse() {
    }

    public ExceptionResponse(String msg) {
	    super(msg);
    }
    
    public ExceptionResponse(String statusCode, String status, String msg) {
	    super(msg);
    }
	
}
