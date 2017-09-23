package com.calleva.mapper;


import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.erisim.Zemberek;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;



import java.util.regex.Pattern;
import java.io.IOException;
import java.lang.InterruptedException; 
import java.util.*;

    public class argoMapper extends TableMapper<Text, IntWritable>{
    	  
    	private static final Pattern UNDESIRABLES = Pattern.compile("[(){},.;!+\"?<>%]");
        
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        
    @SuppressWarnings({ "deprecation", "unused" })
	public void map(ImmutableBytesWritable rowKey, Result columns, Context context
        ) throws IOException, InterruptedException {

    	Zemberek z= new Zemberek(new TurkiyeTurkcesi());
    	String inKey = new String(rowKey.get());
        String storeKey = inKey.split("#")[0];
        byte[] gorusme = columns.getValue(Bytes.toBytes("gorusmeler"), Bytes.toBytes("metin"));

        String metin = new String(gorusme);
        StringTokenizer itr = new StringTokenizer(metin.toLowerCase());
      
        Configuration conf=HBaseConfiguration.create();
        @SuppressWarnings("resource")
		HTable table=new HTable(conf,"ArgoKelimeler");
           
        while (itr.hasMoreTokens()) {
                word.set(itr.nextToken());
                String cleanWord = UNDESIRABLES.matcher(word.toString()).replaceAll("");
                if(z.kelimeDenetle(cleanWord)){
                	
                	cleanWord=z.kelimeCozumle(cleanWord)[0].kok().icerik();
                }
                         
                SingleColumnValueFilter filter=new SingleColumnValueFilter(Bytes.toBytes("ArgoKelime"),
                		Bytes.toBytes("akelime"),CompareOp.EQUAL,Bytes.toBytes(cleanWord));
                
                Scan scan=new Scan();
                scan.setFilter(filter);
                ResultScanner scanner=table.getScanner(scan);
                
                
                for (  Result result : scanner) {
                  for (    KeyValue kv : result.raw()) {
 
                    context.write(new Text(storeKey), one);
                    
                  }
            }
                filter.setFilterIfMissing(true);
                context.write(new Text(storeKey),new IntWritable(0));
        }
    }
    }