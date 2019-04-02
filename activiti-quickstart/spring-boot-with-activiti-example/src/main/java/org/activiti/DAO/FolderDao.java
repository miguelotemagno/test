package org.activiti.DAO;

import java.util.HashMap;
import java.util.Map;

import org.activiti.service.AlfrescoProperties;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("folderDao")
public class FolderDao extends AppDao {

    @Autowired(required=true)
    private AlfrescoProperties alfresco;

    public Folder registerFolder(Session session, Folder parent, String folderNew) {

        // prepare properties
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(PropertyIds.NAME, folderNew);
        properties.put(PropertyIds.OBJECT_TYPE_ID, alfresco.getAlfCmisFolder());

        Folder newFolder = parent.createFolder(properties);
        return newFolder;
    }

    public Folder getFolder(Session session, Folder parent, String f) {

        Folder folder = null;
        try {
            CmisObject object = session.getObjectByPath(parent.getPath().concat(f));
            folder = (Folder) object;

//			model.setFilename(document.getName());
//			model.setDescription(document.getDescription());
//			model.setUrlDownload(document.getContentUrl());

        } catch (CmisObjectNotFoundException e) {
            System.err.println("folder is not found: " + f);
        }

        return folder;
    }

    public void deleteFolder() {

    }

}