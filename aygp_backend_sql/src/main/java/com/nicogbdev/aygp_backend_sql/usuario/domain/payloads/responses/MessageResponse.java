package com.nicogbdev.aygp_backend_sql.usuario.domain.payloads.responses;

public class MessageResponse extends UsuarioResponse{
    // Propiedades.
    private String message;

    // Constructores.


    public MessageResponse() {
    }

    public MessageResponse(String message) {
        this.message = message;
    }


    // Getters y setters.

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
