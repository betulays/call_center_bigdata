package com.calleva.mapper;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;


import com.calleva.similarity.cossim;

public class yardimMapper extends TableMapper<Text, DoubleWritable> {
	 Text word = new Text();
	 String acilis;
		public void map(ImmutableBytesWritable rowKey, Result columns, Context context)
	            throws IOException, InterruptedException {
			
			String inKey = new String(rowKey.get());
	        String storeKey = inKey.split("#")[0];
	        byte[] gorusme = columns.getValue(Bytes.toBytes("gorusmeler"), Bytes.toBytes("metin"));

	        String metin = new String(gorusme);
	        
	        String[] words;
	        words= metin.toString().split("\\n");
	       
	        for(int i=0;i<words.length;i++){
	        
	        	word.set(words[i]);
	        	acilis=word.toString();
	        	String s1="yardım edebileceğim başka bir konu var mı";
	   	     
		        cossim cs = new cossim();
		        double benzerlik1 = cs.Cosine_Similarity_Score(s1, acilis);
		        double sonuc=benzerlik1*100;
		       
		        if(sonuc>50){
		        
		
		        	context.write(new Text(storeKey), new DoubleWritable(sonuc));
		        }
		        
		        if(sonuc<50){
		        	context.write(new Text(storeKey), new DoubleWritable(0.0));
		        }
	        }
	        
	        
	        
	   
			
		}
		
	}


