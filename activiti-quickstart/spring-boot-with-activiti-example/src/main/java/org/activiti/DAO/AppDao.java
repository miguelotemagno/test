package org.activiti.DAO;

import java.util.HashMap;
import java.util.Map;

import org.activiti.service.AlfrescoProperties;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.springframework.beans.factory.annotation.Autowired;

public class AppDao {

    @Autowired(required=true)
    private AlfrescoProperties alfresco;

    public Session getSession() {

        SessionFactory factory = SessionFactoryImpl.newInstance();
        Map<String, String> parameters = new HashMap<String, String>();

        // user credentials
        parameters.put(SessionParameter.USER, alfresco.getAlfUser());
        parameters.put(SessionParameter.PASSWORD, alfresco.getAlfPassword());

        String urlAtomPub = alfresco.getAlfLevelHttp().concat("://").concat(alfresco.getAlfHost())
                .concat(":").concat(alfresco.getAlfPort()).concat(alfresco.getAlfAtompubUrl());

        // connection settings
        parameters.put(SessionParameter.ATOMPUB_URL, urlAtomPub);
        parameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

        Session session = factory.getRepositories(parameters).get(0).createSession();
        return session;
    }

}