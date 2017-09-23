package com.calleva.xmlyaz;


import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;


public class xmlyaz {
	
	@SuppressWarnings({ "deprecation", "resource" })
	public void xml() throws IOException, ParserConfigurationException, TransformerException {
		
		 Configuration config = HBaseConfiguration.create();
		 HTable table = new HTable(config, "proje");
		 Scan scan = new Scan();
		 scan.addColumn(Bytes.toBytes("gorusmeler"), Bytes.toBytes("mtid"));
		 scan.addColumn(Bytes.toBytes("gorusmeler"), Bytes.toBytes("tarih"));
		 
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("acilissonuc"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("acilispuan"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("argosonuc"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("argopuan"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("cagridurum"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("cagritip"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("cumletekrari"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("cumlepuan"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("isimsonuc"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("isimpuan"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("kapanissonuc"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("kapanispuan"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("uyarisonuc"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("uyaripuan"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("yardimsonuc"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("yardimpuan"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("yasaklikelime"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("yasaklipuan"));
		 scan.addColumn(Bytes.toBytes("analiz"), Bytes.toBytes("puansonuc"));
		 
		 ResultScanner scanner = table.getScanner(scan);
		 
		 
		  DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	       
	   
	      Document doc = docBuilder.newDocument();
	      Element rootElement = doc.createElement("sonuc");
	      doc.appendChild(rootElement);
	      
	      for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
	    	  
	    	  Element staff = doc.createElement("gorusme");
    	      rootElement.appendChild(staff);
    		  Attr attr = doc.createAttribute("gid");  
			  attr.setValue(Bytes.toString(rr.getRow()));
    		  staff.setAttributeNode(attr);
    		  
	    	  for (KeyValue kv : rr.raw()) {


	    	      Element firstname = doc.createElement(Bytes.toString(kv.getQualifier()));
	    	      firstname.appendChild(doc.createTextNode(Bytes.toString(kv.getValue())));
	    	      staff.appendChild(firstname);

	    	      TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    	      Transformer transformer = transformerFactory.newTransformer();
	    	      DOMSource source = new DOMSource(doc);
	    	      StreamResult result = new StreamResult(new File("sonuc.xml"));  
	    	      transformer.transform(source, result);
	    	      
	    	     }
	    	    
	      }
	      scanner.close();
		 
	}
	
	public static void main(String [] args){
		
		
		try {
			xmlyaz x = new xmlyaz();
			x.xml();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
