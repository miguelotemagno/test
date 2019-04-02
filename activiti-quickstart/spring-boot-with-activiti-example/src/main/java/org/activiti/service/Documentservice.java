package org.activiti.service;

import org.activiti.DAO.DocumentDao;
import org.activiti.DAO.FolderDao;
import org.activiti.model.CMISModel;
import org.activiti.model.ErrorResponse;
import org.activiti.model.RegisterDocumentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("documentService")
public class Documentservice {

    @Autowired(required=true)
    private AlfrescoProperties alfresco;

    @Autowired(required=true)
    private DocumentDao documentDao;

    @Autowired(required=true)
    private FolderDao folderDao;


    public RegisterDocumentResponse registerDocument(CMISModel model, MultipartFile file) {

        Session session = documentDao.getSession();
        Folder rootFolder = session.getRootFolder();

        RegisterDocumentResponse response = new RegisterDocumentResponse();
        ErrorResponse error = new ErrorResponse();

        Folder folderAspect = folderDao.getFolder(session, rootFolder, alfresco.getAlfDefaultFolder());

        try {
            Document doc = documentDao.registerDocument(session, model, folderAspect, convert(file));

            response.setDocumentId(doc.getId());
            error.setCodigo("000000");
            response.setError(error);
        } catch (Exception e) {
            e.printStackTrace();
            response.setDocumentId("0");
            error.setCodigo("000001");
            error.setMensaje(e.getMessage());
            response.setError(error);
        }
        return response;
    }

    public void registerDocument(CMISModel model, File file, String folder) {

    }

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


    public CMISModel getDocument(String objId) {

        CMISModel model = new CMISModel();

        Session session = documentDao.getSession();
        Folder rootFolder = session.getRootFolder();
        Folder folderAspect = folderDao.getFolder(session, rootFolder, alfresco.getAlfDefaultFolder());

        try {
            Document document = documentDao.getDocument(session, objId, folderAspect);

            model.setFilename(document.getName());
            model.setDescription(document.getDescription());
            model.setUrlDownload(document.getContentUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    public void deleteDocument(String objId) {

        Session session = documentDao.getSession();
        Folder rootFolder = session.getRootFolder();
        Folder folderAspect = folderDao.getFolder(session, rootFolder, alfresco.getAlfDefaultFolder());

        try {
            documentDao.deleteDocument(session, objId, folderAspect);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}