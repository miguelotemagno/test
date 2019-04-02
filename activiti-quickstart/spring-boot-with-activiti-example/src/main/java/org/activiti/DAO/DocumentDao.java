package org.activiti.DAO;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.model.CMISModel;
import org.activiti.model.MimetypeEnum;
import org.activiti.model.MimetypeModel;
import org.activiti.service.AlfrescoProperties;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("documentDao")
public class DocumentDao extends AppDao{

    private final String UTF8 = "charset=UTF-8";

    @Autowired(required=true)
    private AlfrescoProperties alfresco;

    public Document registerDocument(Session session, CMISModel model, Folder folder, File file) {

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(PropertyIds.NAME, model.getFilename());
        properties.put(PropertyIds.DESCRIPTION, model.getDescription());
        properties.put(PropertyIds.OBJECT_TYPE_ID, alfresco.getAlfCmisDocument());
        Document doc = null;

        try {
            byte[] contentBytes = new byte[(int) file.length()];
            ByteArrayInputStream stream = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
            ContentStream contentStream = session.getObjectFactory().createContentStream(model.getFilename(),
                    contentBytes.length, this.getMimytype(model.getCodeMimytype()), stream);

            doc = folder.createDocument(properties, contentStream, VersioningState.MAJOR);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

    public void deleteDocument(Session session, String objId, Folder folder) {
        try {
            CmisObject object = session.getObject(objId);
            Document delDoc = (Document) object;
            delDoc.delete(true);
        } catch (CmisObjectNotFoundException e) {
            System.err.println("Document is not found: " + objId);
        }
    }

    public Document getDocument(Session session, String objId, Folder folder) {

        Document document = null;
        try {
            CmisObject object = session.getObject(objId);
            document = (Document) object;
        } catch (CmisObjectNotFoundException e) {
            System.err.println("Document is not found: " + objId);
        }

        return document;
    }

    public List<CmisObject> getDocumentsByFolder(){

        return null;
    }

    public List<MimetypeModel> getAllMimetypes(){

        return null;
    }


    private String getMimytype(String code) {
        for (MimetypeEnum cods : MimetypeEnum.values()) {
            if(cods.getCode().equals(code)) {
                return cods.getMimytype();
            }
        }
        return "";
    }

}