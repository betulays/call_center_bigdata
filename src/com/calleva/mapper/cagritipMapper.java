package com.calleva.mapper;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;

import org.apache.hadoop.io.Text;


import com.calleva.similarity.cossim;

public class cagritipMapper extends TableMapper<Text, Text> {
	 Text word = new Text();
		public void map(ImmutableBytesWritable rowKey, Result columns, Context context)
	            throws IOException, InterruptedException {
			
			String inKey = new String(rowKey.get());
	        String storeKey = inKey.split("#")[0];
	        byte[] gorusme = columns.getValue(Bytes.toBytes("gorusmeler"), Bytes.toBytes("metin"));

	        String metin = new String(gorusme);
	        String[] words;
	        words= metin.toString().split("\\n");
	        word.set(words[0]);
	        String acilis=word.toString();
	       
	        String s1="iyi günler ben ahmet size nasıl yardımcı olabilirim";
	        String s2 ="iyi günler ben arıyorum ismim ahmet";
	        cossim cs = new cossim();
	       
	        double benzerlik1 = cs.Cosine_Similarity_Score(s1, acilis);
	        double sonuc=benzerlik1*100;
	        
	        double benzerlik2 = cs.Cosine_Similarity_Score(s2, acilis);
	        double sonuc2=benzerlik2*100;
	     
	        if(sonuc>sonuc2){
	        	context.write(new Text(storeKey), new Text(Bytes.toBytes("Gelen Çağrı")));
	        }
	        
	        else{
	        	context.write(new Text(storeKey), new Text(Bytes.toBytes("Giden Çağrı")));
	        }
			
		}

}
