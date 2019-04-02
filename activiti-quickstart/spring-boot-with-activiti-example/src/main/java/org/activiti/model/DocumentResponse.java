package org.activiti.model;

import java.io.Serializable;

public class DocumentResponse implements Serializable{

    private static final long serialVersionUID = 6905242610708826571L;

    private CMISModel document;

    private ErrorResponse error;

    public CMISModel getDocument() {
        return document;
    }

    public void setDocument(CMISModel document) {
        this.document = document;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }



}
