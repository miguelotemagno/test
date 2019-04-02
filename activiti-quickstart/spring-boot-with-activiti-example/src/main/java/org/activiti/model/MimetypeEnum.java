package org.activiti.model;

public enum MimetypeEnum {

    MIMETYPE_PDF		("001", ".pdf","application/pdf"),
    MIMETYPE_TXT		("002", ".txt","text/plain;"),
    MIMETYPE_DOCX		("003", ".docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document;"),

    ;

    private String code;
    private String mimytype;
    private String extension;

    private MimetypeEnum(String code, String extension, String mimytype) {
        this.code = code;
        this.extension = extension;
        this.mimytype = mimytype;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMimytype() {
        return mimytype;
    }
    public void setMimytype(String mimytype) {
        this.mimytype = mimytype;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}