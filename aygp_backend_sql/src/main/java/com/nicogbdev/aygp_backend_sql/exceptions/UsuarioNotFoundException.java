package com.nicogbdev.aygp_backend_sql.exceptions;

public class UsuarioNotFoundException extends Exception{
    public UsuarioNotFoundException() {
    }

    public UsuarioNotFoundException(String message) {
        super(message);
    }
}
