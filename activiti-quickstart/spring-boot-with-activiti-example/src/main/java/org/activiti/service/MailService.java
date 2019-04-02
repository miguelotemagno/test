package org.activiti.service;

import org.activiti.dto.Mail;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.*;
import java.util.*;

@Configuration
//@PropertySource("file:alfresco-global.properties")

@Component
public class MailService {
    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.port}")
    private String mailPort;

    @Value("${mail.username}")
    private String mailUsername;

    @Value("${mail.password}")
    private String mailPassword;

    private static final Logger logger = LogManager.getLogger(MailService.class);
    Folder inbox;

    public List<Mail> fetch() {
        logger.info("Read email list...");
        List<Mail> mails = new ArrayList<Mail>();
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3.host", mailHost);
        properties.put("mail.pop3.port", mailPort);
        properties.put("mail.pop3.starttls.enable", "true");

        try {
            Session emailSession = Session.getDefaultInstance(properties);
            logger.info("Opening session: "+properties.toString());
            Store store = emailSession.getStore("pop3s");

            logger.info("connecting with: "+mailUsername);
            store.connect(mailHost, mailUsername, mailPassword);

            // create the folder object and open it
            logger.info("opening inbox...");
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            logger.info("search unread mails...");
            Message messages[] = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.CONTENT_INFO);

            logger.info("fetch messages...");
            inbox.fetch(messages, fp);

            for (Message msg : messages) {
                String subject = msg.getSubject();

                if(subject.matches(".*[Cc]otizacion.*")) {
                    Mail mail = new Mail();
                    mail.setFrom(msg.getFrom()[0].toString());
                    mail.setSubject(subject);
                    Object o = msg.getContent();
                    String str = (o instanceof String) ?(String) o :writePart(msg);
                    Message[] msgs = new Message[1];
                    msgs[0] = msg;
                    Map attachedFiles = getListFiles(msgs);
                    mail.setBody(msg.getContentType()+"\n"+str+"\nAttached files:\n"+attachedFiles.keySet().toString());

                    mails.add(mail);
                }
            }

            logger.info("OK");
        } catch (Exception e) {
            logger.error("ERROR: "+e.toString());
        }

        return mails;
    }

    public String writePart(Part p) throws Exception {
        StringBuilder out = new StringBuilder();
        if (p instanceof Message)
            //Call methos writeEnvelope
            out.append(writeEnvelope((Message) p));

        out.append("\n----------------------------");
        out.append("\nCONTENT-TYPE: " + p.getContentType());

        //check if the content is plain text
        if (p.isMimeType("text/plain")) {
           out.append((String) p.getContent());
        }
        //check if the content has attachment
        else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++)
                writePart(mp.getBodyPart(i));
        }
        //check if the content is a nested message
        else if (p.isMimeType("message/rfc822")) {
            writePart((Part) p.getContent());
        }
        //check if the content is an inline image
        else if (p.isMimeType("image/jpeg")) {
            int i;
            byte[] bArray = null;
            out.append("\n--------> image/jpeg");
            Object o = p.getContent();

            InputStream x = (InputStream) o;
            // Construct the required byte array
            out.append("\nx.length = " + x.available());
//            while ((i = (int) ((InputStream) x).available()) > 0) {
//                int result = (int) (((InputStream) x).read(bArray));
//                if (result == -1)
//                    i = 0;
//                bArray = new byte[x.available()];
//
//                break;
//            }
//            FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
//            f2.write(bArray);
        }
        else if (p.getContentType().contains("image/")) {
            out.append("content type" + p.getContentType());
            File f = new File("image" + new Date().getTime() + ".jpg");
            DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
            com.sun.mail.util.BASE64DecoderStream test = (com.sun.mail.util.BASE64DecoderStream) p.getContent();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = test.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }
        else {
            Object o = p.getContent();
            if (o instanceof String) {
                out.append((String) o);
            }
            else if (o instanceof InputStream) {
                InputStream is = (InputStream) o;
                is = (InputStream) o;
                int c;
                while ((c = is.read()) != -1)
                    out.append(c);
            }
            else {
                out.append(o.toString());
            }
        }

        return out.toString()+"\n";
    }

    public String writeEnvelope(Message m) throws Exception {
        String out = new String();
        Address[] a;

        // FROM
        if ((a = m.getFrom()) != null) {
            for (int j = 0; j < a.length; j++)
                out += ("\nFROM: " + a[j].toString());
        }

        // TO
        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
            for (int j = 0; j < a.length; j++)
                out += ("\nTO: " + a[j].toString());
        }

        // SUBJECT
        if (m.getSubject() != null)
            out += ("\nSUBJECT: " + m.getSubject());

        return out+"\n";
    }

    Map<String,File> getListFiles(Message[] temp) throws IOException, MessagingException {
        Map<String,File> attachments = new HashMap<String, File>();
        for (Message message : temp) {
            Multipart multipart = (Multipart) message.getContent();

            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if(!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) &&
                        StringUtils.isBlank(bodyPart.getFileName())) {
                    continue; // dealing with attachments only
                }
                InputStream is = bodyPart.getInputStream();
                String fileName = bodyPart.getFileName();
                File f = new File("/tmp/" + fileName);
                FileOutputStream fos = new FileOutputStream(f);
                byte[] buf = new byte[4096];
                int bytesRead;
                while((bytesRead = is.read(buf))!=-1) {
                    fos.write(buf, 0, bytesRead);
                }
                fos.close();
                attachments.put(fileName, f);
            }
        }

        return attachments;
    }
}
