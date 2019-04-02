package AppCargaMasiva;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.csvreader.CsvReader;


public class LeerTxtAspectos 
{
    public ArrayList leerArchivo(String ubicacionArchivoIndex, char delimitador)
    {
        FileReader freader = null;
        CsvReader csvReader = null;
        ArrayList fileAddress = new ArrayList();
        ArrayList allFileAddresses = new ArrayList();
        String valor = null;
        String _headers[] = null;
        try
        {
            File file = new File(ubicacionArchivoIndex);
            freader = new FileReader(file);
            csvReader = new CsvReader(freader, delimitador);
            if(csvReader.readHeaders())
            {
                _headers = csvReader.getHeaders();
                System.out.println("Iniciando carga de Archivos a Alfresco");
            }
  
            	
            
            for(; csvReader.readRecord(); allFileAddresses.add(fileAddress.clone()))
            {
                fileAddress.clear();
                valor =  new String(csvReader.get(0).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(1).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(2).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(3).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(4).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(5).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(6).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(7).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(8).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(9).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(10).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(11).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(12).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(13).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(14).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
                valor =  new String(csvReader.get(15).getBytes("ISO-8859-1"),"utf-8");
                fileAddress.add(valor);
            }
            csvReader.close();
            freader.close();
            file.delete();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return allFileAddresses;
    }


}
