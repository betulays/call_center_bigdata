package com.calleva.mapper;

import java.io.IOException;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

import com.calleva.similarity.cossim;

public class acilisMapper extends TableMapper<Text, DoubleWritable> {
	
	Text word = new Text();
	public void map(ImmutableBytesWritable rowKey, Result columns, Context context)
            throws IOException, InterruptedException {
		
		String inKey = new String(rowKey.get());
        String storeKey = inKey.split("#")[0];
        byte[] gorusme = columns.getValue(Bytes.toBytes("gorusmeler"), Bytes.toBytes("metin"));

        String cagri = new String(gorusme);
      
        
        String[] words;
        words= cagri.toString().split("\\n");
       
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
        	context.write(new Text(storeKey), new DoubleWritable(sonuc));
        }
        
        else{
        	context.write(new Text(storeKey), new DoubleWritable(sonuc2));
        }
		
	}
	
}
