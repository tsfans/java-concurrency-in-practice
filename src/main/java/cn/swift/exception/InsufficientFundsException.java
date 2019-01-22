package cn.swift.exception;

public class InsufficientFundsException extends Exception {

    private static final long serialVersionUID = 8866437210181078854L;

    public InsufficientFundsException(String message) {
        super(message);
    }
}
