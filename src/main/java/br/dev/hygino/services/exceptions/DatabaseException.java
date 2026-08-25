package br.dev.hygino.services.exceptions;

public final class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
