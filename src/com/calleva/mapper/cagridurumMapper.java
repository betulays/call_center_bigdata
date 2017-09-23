package com.calleva.mapper;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;

import org.apache.hadoop.io.Text;


import com.calleva.similarity.cossim;

public class cagridurumMapper extends TableMapper<Text, Text> {

	Text word=new Text();
	String kapanis;
	public void map(ImmutableBytesWritable rowKey, Result columns, Context context)
            throws IOException, InterruptedException {
		
		String inKey = new String(rowKey.get());
        String storeKey = inKey.split("#")[0];
        byte[] gorusme = columns.getValue(Bytes.toBytes("gorusmeler"), Bytes.toBytes("metin"));

        String metin = new String(gorusme);
        String[] words;
        words= metin.toString().split("\\n");

        int sonindex = words.length-1;
        word.set(words[sonindex]);
        kapanis=word.toString();      
        
        
        String s1="ben teşekkür ederim iyi günler dilerim";
        String s2="görüşmeyi sonlandırabilirsiniz";
        cossim cs = new cossim();
        double benzerlik1 = cs.Cosine_Similarity_Score(s1, kapanis);
        double sonuc1=benzerlik1*100;
        double benzerlik2 = cs.Cosine_Similarity_Score(s2, kapanis);
        double sonuc2=benzerlik2*100;
        
        if(sonuc1>sonuc2){
      
        	context.write(new Text(storeKey), new Text(Bytes.toBytes("Görüşme Başarılı")));
       
        }
        else{
        	
        	context.write(new Text(storeKey), new Text(Bytes.toBytes("Görüşme Başarısız.Tekrar Aranmalı")));
        }
        
        
		
	}


}
