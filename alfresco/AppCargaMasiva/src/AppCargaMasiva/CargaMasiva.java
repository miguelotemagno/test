package AppCargaMasiva;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;


public class CargaMasiva {
	 public static void main(String[] parametro) throws IOException 
	 {
		 
		
		
		
		 // Declaración de objetos
		 Properties properties = new Properties();
		 CargaMasiva cargaMasiva = new CargaMasiva();
		 LeerTxtAspectos leerTxtArchivo = new LeerTxtAspectos();
		 
		 		 
		 // asignar variable properties
		 
		 cargaMasiva.setProperties(properties);
		 
		
		 
		 
		 
		 // se recorre el archivo de indexes y se toman los datos
		 
		 ArrayList allFileAddresses = new ArrayList();
		 allFileAddresses =  cargaMasiva.TomarDatosIndex(properties, leerTxtArchivo);
		 /*for(int i = 0; i < allFileAddresses.size(); i++)
	        {
			 	System.out.println(allFileAddresses.get(i));
	        }*/
		
		 // se recupera el ticket
		 
		 String ticket = cargaMasiva.getTicket(properties);
		 
		 cargaMasiva.cargaDocumentosAlfresco(ticket, allFileAddresses, properties);
		 

         System.out.println("Carga Realizada exitosamente");
		
		 
	  }
	 
	 public void setProperties(Properties properties) throws IOException
	 {
		 FileInputStream fileProp = null;
		 try 
		 {
			fileProp = new FileInputStream(System.getProperty("user.dir")+"//src//archivo//properties//archivo.properties");
		 } 
		 catch (Exception e) 
		 {
			 e.printStackTrace();
			 System.out.println("El archivo no existe, por favor revise si existe el acrhvivo");
		 }
		 
		 properties.load(fileProp);
	 }
	 
	 public ArrayList TomarDatosIndex(Properties properties, LeerTxtAspectos leerTxtArchivo) 
	 {
		 ArrayList allFileAddresses = new ArrayList();
		 String ubicacionArchivoIndex = (String) properties.get("ubicacionArchivoIndex");
		 char delimitador =  ((String) properties.get("delimitador")).charAt(0);
		 allFileAddresses =  leerTxtArchivo.leerArchivo(ubicacionArchivoIndex, delimitador);
		 return allFileAddresses;
	}
	 
	 public String getTicket(Properties properties)
    {
		StringBuilder url = new StringBuilder().append("http://").append(properties.getProperty("ipAlfresco")).append(":").append(properties.getProperty("puertoAlfresco")).append("/alfresco/service/api/login?u=").append(properties.getProperty("userAlfresco")).append("&pw=").append(properties.getProperty("passAlfresco"));
		GetMethod var1 = new GetMethod(url.toString());
	    HttpClient client = new HttpClient();
        String ticket;
        try
        {
            client.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
            client.executeMethod(var1);
            ticket = var1.getResponseBodyAsString();
            ticket = getStringContent(ticket);
        }
        catch(Exception ex)
        {
            ticket = "";
            System.out.println((new StringBuilder()).append("No se pudo obtener el Ticket. Error: ").append(ex).toString());
        }
        return ticket;
		 
    }
	
	 private String getStringContent(String ticket)
    {
        String ticket1[] = ticket.split("<");
        String ticket2[] = ticket1[2].split(">");
        ticket = ticket2[1];
        return ticket;
    }
	
	private String cargaDocumentosAlfresco(String ticket, ArrayList allFileAddresses, Properties properties) throws HttpException, IOException
	{
		String ticketCarga = ticket ;
		HttpClient client;
	    PostMethod filePost;
	    File doc = null;
	    ArrayList ArrayPartes= new ArrayList();
	    Reportes rep = new Reportes();
	    int status = 0;
	    GenerarIndexRechazados generarIndex = new GenerarIndexRechazados();
	   
	    StringBuilder url = new StringBuilder().append("http://").append(properties.getProperty("ipAlfresco")).append(":").append(properties.getProperty("puertoAlfresco")).append("/alfresco/s/loadnyp").append("?alf_ticket=").append(ticket);
	    
	    
		ArrayList fileContent = new ArrayList();
		
        client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
        filePost = null;
        File folder = new File(properties.getProperty("carpRechazo")+this.getFechaActual());
     	folder.mkdirs();
        
        for(int i = 0; i < allFileAddresses.size(); i++)
        {
            filePost = new PostMethod(url.toString());
            filePost.getParams().setBooleanParameter("http.protocol.expect-continue", true);
            fileContent = (ArrayList)allFileAddresses.get(i);
            
            String rutaArchivo =properties.getProperty("ubicacionArchivos");
            int SO = System.getProperty("os.name").toUpperCase().indexOf("WINDOWS");
            if(SO == 0)
            	doc = new File((new StringBuilder()).append(rutaArchivo).append(fileContent.get(0)).toString());
            else
            	doc = new File((new StringBuilder()).append(rutaArchivo).append(fileContent.get(0)).toString());
            
            String file = (String)fileContent.get(0);
            String gnID = (String)fileContent.get(1);
            String gnNAME = (String)fileContent.get(2);
            String gnCREATIONDATE = (String)fileContent.get(3);
            String gnTITULO = (String)fileContent.get(4);
            String gnDATE = (String)fileContent.get(5);
            String gnDOC_ORIG = (String)fileContent.get(6);
            String gnCADUCITYDATE = (String)fileContent.get(7);
            String gnVALIDITYDATE = (String)fileContent.get(8);
            String dFecPubliC = (String)fileContent.get(9);
            String cCodPublic = (String)fileContent.get(10);
            String nPublicId = (String)fileContent.get(11);
            String nVersDoc = (String)fileContent.get(12);
            String nAnalistaId = (String)fileContent.get(13);
            String nNumCircular = (String)fileContent.get(14);

            if(doc.exists())
            {
	            Part parts[] = {
	                new StringPart("contenttype", properties.getProperty("contenttype")), 
	                new StringPart("author",acentos(properties.getProperty("author"))), 
	                new StringPart("filename", acentos(file)), 
	                new StringPart("mimetype", properties.getProperty("mimetype")), 
	                new FilePart("file", rutaArchivo+file, doc), 
	                
	                new StringPart("file", acentos(file.toString())),
	                new StringPart("GN_ID", acentos(gnID.toString())),
	                new StringPart("GN_NAME", acentos(gnNAME.toString())),
	                new StringPart("GN_CREATIONDATE", acentos(gnCREATIONDATE.toString())),
//	                new StringPart("contentStreamMimeType", properties.getProperty("mimetype")),
	                new StringPart("GN_TITULO", acentos(gnTITULO.toString())),
	                new StringPart("GN_DATE", acentos(gnDATE.toString())),
	                new StringPart("GN_DOC_ORIG", acentos(gnDOC_ORIG.toString())),
	                new StringPart("GN_CADUCITYDATE", acentos(gnCADUCITYDATE.toString())),
	                new StringPart("GN_VALIDITYDATE", acentos(gnVALIDITYDATE.toString())),
	                new StringPart("DFecPubliC", acentos(dFecPubliC.toString())),
	                new StringPart("CCodPublic", acentos(cCodPublic.toString())),
	                new StringPart("nPublicId", acentos(nPublicId.toString())),
	                new StringPart("nVersDoc", acentos(nVersDoc.toString())),
	                new StringPart("nAnalistaId", acentos(nAnalistaId.toString())),
	                new StringPart("nNumCircular", acentos(nNumCircular.toString()))

	            };
	            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
	            status = client.executeMethod(filePost);
            
            }
            else
            {
            	status = 1;
            }
            if(status == 1)
            {
            	fileContent.add(0, status);
            	fileContent.add(15, "Error : La ubicaci�n del archivo descripto en el index no existe");
            	//generarIndex.generarIndexRechazados(fileContent,folder, properties); 
            }
            
            if(status == 401)
            {
            	fileContent.add(0, status);
            	fileContent.add(15, "Error ; Problemas con el contenido del documento");
            	File fdest = new File(folder.getAbsolutePath()+"\\"+file);
            	doc.renameTo(fdest);
            	//generarIndex.generarIndexRechazados(fileContent,folder, properties); 
            }
            if(status == 500)
            {
            	fileContent.add(0, status);
             	fileContent.add(15, "Error : El documento ya se encuentra cargado en esa ubicacion o tiene problemas con el contenido");
             	File fdest = new File(folder.getAbsolutePath()+"\\"+file);           	
             	doc.renameTo(fdest);
             	//generarIndex.generarIndexRechazados(fileContent,folder, properties); 
            }
            if(status == 200)
            {
            	fileContent.add(0, status);
            	fileContent.add(15, " Exito: El documento fue cargado exitosamente");
            	doc.delete();
            }
            if(status == 300)
            {
            	fileContent.add(0, status);
            	fileContent.add(15, " Error : Problemas con la metadata del index");
            	File fdest = new File(folder.getAbsolutePath()+"\\"+file);
            	doc.renameTo(fdest);
            	//generarIndex.generarIndexRechazados(fileContent,folder, properties); 
            }
            
            if(status == 444)
            {
            	fileContent.add(0, status);
            	fileContent.add(15, " Error : El path es incorrecto");
            	File fdest = new File(folder.getAbsolutePath()+"\\"+file);
            	doc.renameTo(fdest);
            	//generarIndex.generarIndexRechazados(fileContent,folder, properties); 
            }
            
            if(status == 404)
            {
            	fileContent.add(0, status);
            	fileContent.add(15, " Error : La url no fue encontrada");
            	System.out.println("Error : La url no fue encontrada");
            	File fdest = new File(folder.getAbsolutePath()+"\\"+file);
            	doc.renameTo(fdest);
            }
            
            ArrayPartes.add(fileContent.clone()); 
          
        }
    
        generarIndex.generarIndexRechazados(ArrayPartes,folder, properties);
        fileContent.clear();
        rep.generarReportes(properties, ArrayPartes);
		 return "hola";
	}
	public String getFechaActual() 
	{
	    Date ahora = new Date();
	    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy-HHmm");
	    return formateador.format(ahora);
	}
    public String acentos (String dato) throws UnsupportedEncodingException
    {
    	
		ArrayList<String> acentos = new ArrayList<String>();
		acentos.add("�");
		acentos.add("�");
		acentos.add("�");
		acentos.add("�");
		acentos.add("�");
		acentos.add("�");
		acentos.add("�");
		acentos.add("�");
		acentos.add("�");
		acentos.add("�");
	    acentos.add("�");
	    acentos.add("�");
	    //System.out.println(dato);
		for (Object a: acentos)
		{
			 
			if(dato.indexOf(a.toString()) != -1){
				dato = dato.replace(a.toString(), "acent" + cambiarLetra(a.toString()));
			}
		}
		//System.out.println(dato);
		return dato;
    }

	public String cambiarLetra(String a)
	{
		if(a.toString().indexOf("�") != -1)
			return "a";
		if(a.toString().indexOf("�") != -1)
			return "e";
		if(a.toString().indexOf("�") != -1)
			return "i";
		if(a.toString().indexOf("�") != -1)
			return "o";
		if(a.toString().indexOf("�") != -1)
			return "u";
		if(a.toString().indexOf("�") != -1)
			return "A";
		if(a.toString().indexOf("�") != -1)
			return "E";
		if(a.toString().indexOf("�") != -1)
			return "I";
		if(a.toString().indexOf("�") != -1)
			return "O";
		if(a.toString().indexOf("�") != -1)
			return "U";
	            if(a.toString().indexOf("�") != -1)
			return "N";
		if(a.toString().indexOf("�") != -1)
			return "n";
		return null;
	}
}
