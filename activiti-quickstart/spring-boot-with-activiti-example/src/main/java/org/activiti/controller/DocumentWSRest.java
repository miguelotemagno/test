package org.activiti.controller;

import org.activiti.model.CMISModel;
import org.activiti.model.RegisterDocumentResponse;
import org.activiti.service.Documentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DocumentWSRest {

    @Autowired(required=true)
    private Documentservice documentService;

    @RequestMapping(value="/document/upload",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RegisterDocumentResponse registerDocument(
            @RequestParam("file") MultipartFile uploadfile,
            @RequestParam("description") String description,
            @RequestParam("codeMT") String codeMT) {

        CMISModel model = new CMISModel(uploadfile.getOriginalFilename(), codeMT, description);
        RegisterDocumentResponse response = documentService.registerDocument(model, uploadfile);

        return response;

    }

    @RequestMapping(value="/document/get/{objId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CMISModel getDocument(@PathVariable String objId) {

        return documentService.getDocument(objId);

    }

    @RequestMapping(value="/document/delete/{objId}",
            method = RequestMethod.DELETE)
    public void deleteDocument(@PathVariable String objId) {

        documentService.deleteDocument(objId);

    }

}