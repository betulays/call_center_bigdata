package com.calleva.hbase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class hbasevt {
	private static Configuration conf;
	HTable table;

	@SuppressWarnings("deprecation")
	hbasevt(String tableName, String[] colFams) throws IOException {
		conf = HBaseConfiguration.create();
		table = new HTable(conf, tableName);
	}

	@SuppressWarnings("deprecation")
	public void addAColumnEntry(String tableName, String row, String colFamilyName, String colName, byte[] data) throws IOException {

		byte[] rowKey = Bytes.toBytes("gid"+row);
		Put putdata = new Put(rowKey);
		putdata.add(Bytes.toBytes(colFamilyName), Bytes.toBytes(colName), data);
		table.put(putdata);

	}

	public static void main(String args[]) throws IOException {
		String tableName = "proje";
		String[] colFamilyNames = { "gorusmeler" };
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		String date = sdf.format(new Date()); 
		//System.out.println(date);
		
		hbasevt test = new hbasevt(tableName, colFamilyNames);

		List<File> listOfFiles = test.getAllFiles("/home/betul/workspace/proje/input");
		int i=1;
		for (File eachFile : listOfFiles) {
			if(i<=eachFile.length())
			//test.addAColumnEntry(tableName, String.valueOf(i), colFamilyNames[0], "gid", Bytes.toBytes(String.valueOf(i)));
			test.addAColumnEntry(tableName, String.valueOf(i), colFamilyNames[0], "mtid", Bytes.toBytes(String.valueOf(i)));
			test.addAColumnEntry(tableName, String.valueOf(i), colFamilyNames[0], "uzunluk", Bytes.toBytes(String.valueOf(i)));
			test.addAColumnEntry(tableName, String.valueOf(i), colFamilyNames[0], "zaman", Bytes.toBytes(String.valueOf(i)));
			test.addAColumnEntry(tableName, String.valueOf(i), colFamilyNames[0], "tarih", Bytes.toBytes(String.valueOf(date)));
			test.addAColumnEntry(tableName, String.valueOf(i), colFamilyNames[0], "metin", FileUtils.readFileToByteArray(eachFile));
			i++;
		}
	}

	public List<File> getAllFiles(String path) {

		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<File> returnList = new ArrayList<File>();
		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				returnList.add(listOfFiles[i]);
			}
		}

		return returnList;
	}

}