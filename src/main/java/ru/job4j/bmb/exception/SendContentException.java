package ru.job4j.bmb.exception;

public class SendContentException extends RuntimeException {
    public SendContentException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
