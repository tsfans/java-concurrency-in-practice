package cn.swift.exception;

public class DataLoadException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    
    public DataLoadException(String message) {
	super(message);
    }

}
