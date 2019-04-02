package org.activiti.model;

public class CMISModel {

    private String filename;

    private String codeMimytype;

    private String urlDownload;

    private String description;


    public CMISModel(String filename, String codeMimytype, String description, String urlDownload) {
        super();
        this.filename = filename;
        this.codeMimytype = codeMimytype;
        this.urlDownload = urlDownload;
        this.description = description;
    }

    public CMISModel(String filename, String codeMimytype, String description) {
        super();
        this.filename = filename;
        this.codeMimytype = codeMimytype;
        this.description = description;
    }

    public CMISModel() {
        super();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCodeMimytype() {
        return codeMimytype;
    }

    public void setCodeMimytype(String codeMimytype) {
        this.codeMimytype = codeMimytype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlDownload() {
        return urlDownload;
    }

    public void setUrlDownload(String urlDownload) {
        this.urlDownload = urlDownload;
    }
}