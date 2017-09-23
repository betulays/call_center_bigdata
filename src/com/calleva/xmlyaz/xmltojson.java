package com.calleva.xmlyaz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.json.XML;

public class xmltojson {

	
	@SuppressWarnings("resource")
	public void xmlcevir() throws IOException {

		 try
         {

             File file = new File ("/home/betul/workspace/proje/sonuc.xml");
             InputStream inputStream = new FileInputStream(file);
             Reader reader = new InputStreamReader(inputStream,"UTF-8");
             StringBuilder builder =  new StringBuilder();
             int ptr = 0;
             while ((ptr = reader.read()) != -1 )
             {
                 builder.append((char) ptr);
             }

             String xml  = builder.toString();
             org.json.JSONObject jsonObj = XML.toJSONObject(xml); 
             System.out.println(jsonObj);
             
             FileWriter file1 = new FileWriter("/home/betul/workspace/proje/sonuc.json");
     		 file1.write(jsonObj.toString());
     		 file1.flush();
     		 file1.close();

         }
         catch(Exception e)
         {
             e.printStackTrace();
         }

		     }
	
	public static void main(String [] args){
		
		
		xmltojson x = new xmltojson();
		try {
			x.xmlcevir();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
