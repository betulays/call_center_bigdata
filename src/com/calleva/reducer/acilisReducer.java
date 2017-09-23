package com.calleva.reducer;
import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;


public class acilisReducer extends TableReducer<Text, DoubleWritable, ImmutableBytesWritable> {

   double point=0.0;

    @SuppressWarnings("deprecation")
	@Override
    public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
            throws IOException, InterruptedException {
       

            double sum = 0.0;
           
            for (DoubleWritable sales : values) {
                Double intSales = new Double(sales.toString());
                sum += intSales;
            }

            Put insHBase = new Put(Bytes.toBytes(key.toString()));

            insHBase.add(Bytes.toBytes("analiz"), Bytes.toBytes("acilissonuc"), Bytes.toBytes(String.valueOf(sum)));

            context.write(null, insHBase);
            
            if(sum<=50){
            	point=0;
            }
            if(sum>50 || sum<=75){
            	
            	point=50;
            }
            
            if(sum>75){
            	
            	point=100;
            }
            
            Put insHBase1 = new Put(Bytes.toBytes(key.toString()));

            insHBase1.add(Bytes.toBytes("analiz"), Bytes.toBytes("acilispuan"), Bytes.toBytes(String.valueOf(point)));

            context.write(null, insHBase1);

        
    }

}
