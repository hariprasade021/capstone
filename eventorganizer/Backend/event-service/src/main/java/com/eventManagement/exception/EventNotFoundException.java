package com.eventManagement.exception;

public class EventNotFoundException extends RuntimeException
{
    public EventNotFoundException(String message) {
        super(message);
    }
}
