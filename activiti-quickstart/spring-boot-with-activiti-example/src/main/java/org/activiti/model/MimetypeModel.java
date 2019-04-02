package org.activiti.model;

public class MimetypeModel {

    private String code;

    private String extension;

    private String mimetype;

    public MimetypeModel(String code, String extension, String mimetype) {
        super();
        this.code = code;
        this.extension = extension;
        this.mimetype = mimetype;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

}