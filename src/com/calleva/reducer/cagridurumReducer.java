package com.calleva.reducer;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;


public class cagridurumReducer extends TableReducer<Text, Text, ImmutableBytesWritable> {

	String durum="";
    @SuppressWarnings("deprecation")
	@Override
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
       
            
            for (Text val : values) {
                durum=val.toString();
            }

            Put insHBase = new Put(Bytes.toBytes(key.toString()));

            insHBase.add(Bytes.toBytes("analiz"), Bytes.toBytes("cagridurum"), Bytes.toBytes(durum));

            context.write(null, insHBase);

        
    }

}


