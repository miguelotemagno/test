package org.activiti.model;

import java.io.Serializable;

public class ErrorResponse implements Serializable{

    private static final long serialVersionUID = -6228818668683761637L;

    private String codigo;

    private String mensaje;

    public String getCodigo() {
        return codigo;
    }

    public ErrorResponse() {
        super();
    }

    public ErrorResponse(String codigo, String mensaje) {
        super();
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Error [codigo=" + codigo + ", mensaje=" + mensaje + "]";
    }

}
