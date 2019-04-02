package org.activiti.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("alfresco")
@PropertySource("classpath:alfresco.properties")
public class AlfrescoProperties {

    @Value("${alf.host}")
    private String alfHost;

    @Value("${alf.port}")
    private String alfPort;

    @Value("${alf.level.http.secure}")
    private String alfLevelHttp;

    @Value("${alf.atompub.url}")
    private String alfAtompubUrl;

    @Value("${alf.default.folder}")
    private String alfDefaultFolder;

    @Value("${alf.charset}")
    private String alfCharset;

    @Value("${alf.full.charset}")
    private String alfFullCharset;

    @Value("${alf.cmis.document}")
    private String alfCmisDocument;

    @Value("${alf.cmis.folder}")
    private String alfCmisFolder;

    @Value("${alf.user}")
    private String alfUser;

    @Value("${alf.password}")
    private String alfPassword;

    public String getAlfHost() {
        return alfHost;
    }

    public void setAlfHost(String alfHost) {
        this.alfHost = alfHost;
    }

    public String getAlfPort() {
        return alfPort;
    }

    public void setAlfPort(String alfPort) {
        this.alfPort = alfPort;
    }

    public String getAlfLevelHttp() {
        return alfLevelHttp;
    }

    public void setAlfLevelHttp(String alfLevelHttp) {
        this.alfLevelHttp = alfLevelHttp;
    }

    public String getAlfAtompubUrl() {
        return alfAtompubUrl;
    }

    public void setAlfAtompubUrl(String alfAtompubUrl) {
        this.alfAtompubUrl = alfAtompubUrl;
    }

    public String getAlfDefaultFolder() {
        return alfDefaultFolder;
    }

    public void setAlfDefaultFolder(String alfDefaultFolder) {
        this.alfDefaultFolder = alfDefaultFolder;
    }

    public String getAlfCharset() {
        return alfCharset;
    }

    public void setAlfCharset(String alfCharset) {
        this.alfCharset = alfCharset;
    }

    public String getAlfFullCharset() {
        return alfFullCharset;
    }

    public void setAlfFullCharset(String alfFullCharset) {
        this.alfFullCharset = alfFullCharset;
    }

    public String getAlfCmisDocument() {
        return alfCmisDocument;
    }

    public void setAlfCmisDocument(String alfCmisDocument) {
        this.alfCmisDocument = alfCmisDocument;
    }

    public String getAlfCmisFolder() {
        return alfCmisFolder;
    }

    public void setAlfCmisFolder(String alfCmisFolder) {
        this.alfCmisFolder = alfCmisFolder;
    }

    public String getAlfUser() {
        return alfUser;
    }

    public void setAlfUser(String alfUser) {
        this.alfUser = alfUser;
    }

    public String getAlfPassword() {
        return alfPassword;
    }

    public void setAlfPassword(String alfPassword) {
        this.alfPassword = alfPassword;
    }

}