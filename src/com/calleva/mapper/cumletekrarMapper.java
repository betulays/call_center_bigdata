package com.calleva.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import java.util.regex.Pattern;


import org.apache.hadoop.hbase.client.Result;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;


import com.calleva.parser.Sentence;

public class cumletekrarMapper extends TableMapper<Text, IntWritable> {

//	private static final Pattern UNDESIRABLES = Pattern.compile("[(){},.;!+\"?<>%]");
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    
public void map(ImmutableBytesWritable rowKey, Result columns, Context context
    ) throws IOException, InterruptedException {

	String inKey = new String(rowKey.get());
    String storeKey = inKey.split("#")[0];
    byte[] gorusme = columns.getValue(Bytes.toBytes("gorusmeler"), Bytes.toBytes("metin"));

    String metin = new String(gorusme);
    
	List<String> nGrams = new ArrayList<String>();
	String lineParse = metin.toLowerCase()
			.replaceAll("[-+.^:,()?'!;\"]", "");
	Sentence lineSentence = new Sentence(lineParse);

	nGrams = lineSentence.wordNGrams(lineSentence, 5);

	for (String aNGram : nGrams) {
		word.set(aNGram);
		
		// String keyy=storeKey + "|" + word.toString();
		context.write(new Text(storeKey), one);
		
	}
        
    }
}
