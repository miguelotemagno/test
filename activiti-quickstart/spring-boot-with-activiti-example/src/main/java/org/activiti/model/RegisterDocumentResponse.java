package org.activiti.model;

import java.io.Serializable;

public class RegisterDocumentResponse implements Serializable{

    private static final long serialVersionUID = 3327906169098008306L;

    private String documentId;

    private ErrorResponse error;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }



}
