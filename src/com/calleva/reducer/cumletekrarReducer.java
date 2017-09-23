package com.calleva.reducer;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;


public class cumletekrarReducer extends TableReducer<Text, IntWritable, ImmutableBytesWritable> {
double point=0.0;
    @SuppressWarnings("deprecation")
	@Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
       
            int sum = 0;
         
          //  String inKey = new String(key.getBytes());
           // String store = inKey.split("\\|")[0];
           // String cumle = inKey.split("\\|")[1];

            for (IntWritable sales : values) {
                Integer intSales = new Integer(sales.toString());
                sum += intSales;
            }

           // if(sum>=3){
            
            Put insHBase = new Put(Bytes.toBytes(key.toString()));
            insHBase.add(Bytes.toBytes("analiz"), Bytes.toBytes("cumletekrari"), Bytes.toBytes(String.valueOf(sum)));
            context.write(null, insHBase);
            
         //   }
            
            if(sum<=1){
            	
            	point=100.0;
            	
            }
            
            if(sum>1 || sum <=3){
            	
            	point=50.0;
            	
            }
            
            if(sum>3){
            	
            	point=0.0;
            	
            }
            
            Put insHBase1 = new Put(Bytes.toBytes(key.toString()));
            insHBase1.add(Bytes.toBytes("analiz"), Bytes.toBytes("cumlepuan"), Bytes.toBytes(String.valueOf(point)));
            context.write(null, insHBase1);
            
    }

}
