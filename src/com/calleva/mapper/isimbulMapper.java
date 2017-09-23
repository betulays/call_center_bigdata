package com.calleva.mapper;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;





import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;


public class isimbulMapper extends TableMapper<Text, IntWritable>{
	  
  private static final Pattern UNDESIRABLES = Pattern.compile("[(){},.;!+\"?<>%]");

  private final static IntWritable one = new IntWritable(1);
  private Text word = new Text();
  private Text word1 = new Text();

  
@SuppressWarnings({ "resource", "deprecation", "unused" })
public void map(ImmutableBytesWritable rowKey, Result columns, Context context
  ) throws IOException, InterruptedException {

	
	String inKey = new String(rowKey.get());
    String storeKey = inKey.split("#")[0];
    byte[] gorusme = columns.getValue(Bytes.toBytes("gorusmeler"), Bytes.toBytes("metin"));

	  String metin = new String(gorusme);
	  String[] words;
      words= metin.toString().split("\\n");
      
      for(int i=1;i<words.length;i++){
    	
    	  word1.set(words[i]);
    	  
    	  
      
	  StringTokenizer itr = new StringTokenizer(word1.toString().toLowerCase());
	  Configuration conf=HBaseConfiguration.create();
	  HTable table=new HTable(conf,"KisiAdlari");
	  
      while (itr.hasMoreTokens()) {
    	  
          word.set(itr.nextToken());
          String cleanWord = UNDESIRABLES.matcher(word.toString()).replaceAll("");
          
          SingleColumnValueFilter filter=new SingleColumnValueFilter(Bytes.toBytes("KisiAdi"),
          		Bytes.toBytes("isim"),CompareOp.EQUAL,Bytes.toBytes(cleanWord));
          Scan scan=new Scan();
          scan.setFilter(filter);
          ResultScanner scanner=table.getScanner(scan);
       
          for (  Result result : scanner) {
            for (    KeyValue kv : result.raw()) {
           
           //   String keyy=storeKey + "|" + Bytes.toString(kv.getValue());
              context.write(new Text(storeKey), one);
            }
          }
          filter.setFilterIfMissing(true);
          context.write(new Text(storeKey),new IntWritable(0));
      }
      }
  }
}
