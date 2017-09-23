package com.calleva.reducer;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;


public class cagritipReducer extends TableReducer<Text, Text, ImmutableBytesWritable> {

	String tip="";

    @SuppressWarnings("deprecation")
	@Override
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
       

            


           
            for (Text val : values) {
                tip =val.toString();
            }

            Put insHBase = new Put(Bytes.toBytes(key.toString()));

            insHBase.add(Bytes.toBytes("analiz"), Bytes.toBytes("cagritip"), Bytes.toBytes(tip));

            context.write(null, insHBase);

        
    }


}
