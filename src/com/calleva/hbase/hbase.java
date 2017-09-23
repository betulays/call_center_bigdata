package com.calleva.hbase;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class hbase {
	
	public void baglanti(){
		try{
		
		@SuppressWarnings("deprecation")
		HBaseConfiguration hc = new HBaseConfiguration(new Configuration());
		@SuppressWarnings("deprecation")
		HTableDescriptor ht = new HTableDescriptor("YasakliKelimeler");
		ht.addFamily( new HColumnDescriptor("YasakliKelime"));
		System.out.println("connecting");
		@SuppressWarnings({ "resource", "deprecation" })
		HBaseAdmin hba = new HBaseAdmin(hc);
		System.out.println("Creating Table");
		hba.createTable(ht);
		System.out.println("Done......");
		
		}
		catch(Exception e){
		System.out.println(e);
		}
		
	}
	
	@SuppressWarnings({ "deprecation", "resource" })
	public void veriekle1() throws IOException{
		
		HBaseConfiguration hc = new HBaseConfiguration(new Configuration());
		HTable table = new HTable(hc, "ArgoKelimeler");
		
		String[] dizi=new String[20000];
    	FileReader read = new FileReader("/home/betul/argokelimeler.txt");
  	    BufferedReader reader=new BufferedReader(read); 
    	String satir=new String(); 
    	
    	int i=0;
    	
    	while((satir=reader.readLine())!=null){ 
    	dizi[i]=satir; 
    	i++;
    	}
    	
    	for(int a=0;a<=dizi.length;a++){
    		
    		Put p = new Put(Bytes.toBytes("row" + a));
    		p.add(Bytes.toBytes("ArgoKelime"), Bytes.toBytes("akelime"),Bytes.toBytes(dizi[a]));
    		table.put(p);
    	}
		
	}
	
@SuppressWarnings({ "deprecation", "resource" })
public void veriekle2() throws IOException{
		
		HBaseConfiguration hc = new HBaseConfiguration(new Configuration());
		HTable table = new HTable(hc, "KisiAdlari");
		
		String[] dizi=new String[20000];
    	FileReader read = new FileReader("/home/betul/kisi-adlari.txt");
  	    BufferedReader reader=new BufferedReader(read); 
    	String satir=new String(); 
    	
    	int i=0;
    	
    	while((satir=reader.readLine())!=null){ 
    	dizi[i]=satir; 
    	i++;
    	}
    	
    	for(int a=0;a<=dizi.length;a++){
    		
    		Put p = new Put(Bytes.toBytes("row" + a));
    		p.add(Bytes.toBytes("KisiAdi"), Bytes.toBytes("isim"),Bytes.toBytes(dizi[a]));
    		table.put(p);
    	}
		
	}

@SuppressWarnings({ "deprecation", "resource" })
public void veriekle3() throws IOException{
	
	HBaseConfiguration hc = new HBaseConfiguration(new Configuration());
	HTable table = new HTable(hc, "YasakliKelimeler");
	
	String[] dizi=new String[20000];
	FileReader read = new FileReader("/home/betul/yasaklikelimeler.txt");
	    BufferedReader reader=new BufferedReader(read); 
	String satir=new String(); 
	
	int i=0;
	
	while((satir=reader.readLine())!=null){ 
	dizi[i]=satir; 
	i++;
	}
	
	for(int a=0;a<=dizi.length;a++){
		
		Put p = new Put(Bytes.toBytes("row" + a));
		p.add(Bytes.toBytes("YasakliKelime"), Bytes.toBytes("ykelime"),Bytes.toBytes(dizi[a]));
		table.put(p);
	}
	
}
	
		@SuppressWarnings("unused")
		public static void main(String[] args) throws IOException
		{
		hbase vt = new hbase();
		//vt.baglanti();
		//vt.veriekle1();
		//vt.veriekle2();
		//vt.veriekle3();
		
		}
}
