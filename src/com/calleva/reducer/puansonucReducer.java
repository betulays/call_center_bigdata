package com.calleva.reducer;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;


public class puansonucReducer extends TableReducer<Text, DoubleWritable, ImmutableBytesWritable> {



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
	            insHBase.add(Bytes.toBytes("analiz"), Bytes.toBytes("puansonuc"), Bytes.toBytes(String.valueOf(sum)));
	            context.write(null, insHBase);
	            	        
	    }

	}

