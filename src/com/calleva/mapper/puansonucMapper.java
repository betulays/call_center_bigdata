package com.calleva.mapper;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;


public class puansonucMapper extends TableMapper<Text, DoubleWritable> {
	
	Text word = new Text();
	public void map(ImmutableBytesWritable rowKey, Result columns, Context context)
            throws IOException, InterruptedException {
		
		String inKey = new String(rowKey.get());
        String storeKey = inKey.split("#")[0];
        byte[] acilis = columns.getValue(Bytes.toBytes("analiz"), Bytes.toBytes("acilispuan"));
        byte[] argo = columns.getValue(Bytes.toBytes("analiz"), Bytes.toBytes("argopuan"));
        byte[] cumle = columns.getValue(Bytes.toBytes("analiz"), Bytes.toBytes("cumlepuan"));
        byte[] isim = columns.getValue(Bytes.toBytes("analiz"), Bytes.toBytes("isimpuan"));
        byte[] kapanis = columns.getValue(Bytes.toBytes("analiz"), Bytes.toBytes("kapanispuan"));
        byte[] uyari = columns.getValue(Bytes.toBytes("analiz"), Bytes.toBytes("uyaripuan"));
        byte[] yardim = columns.getValue(Bytes.toBytes("analiz"), Bytes.toBytes("yardimpuan"));
        byte[] yasakli = columns.getValue(Bytes.toBytes("analiz"), Bytes.toBytes("yasaklipuan"));

        String acilisp = new String(acilis);
        String argop = new String(argo);
        String cumlep = new String(cumle);
        String isimp = new String(isim);
        String kapanisp = new String(kapanis);
        String uyarip = new String(uyari);
        String yardimp = new String(yardim);
        String yasaklip = new String(yasakli);
        
        double puansonuc= (Double.parseDouble(acilisp)+Double.parseDouble(argop)+Double.parseDouble(cumlep)
        		+Double.parseDouble(isimp)+Double.parseDouble(kapanisp)+Double.parseDouble(uyarip)+
        		Double.parseDouble(yardimp)+Double.parseDouble(yasaklip))/8;
              
        context.write(new Text(storeKey), new DoubleWritable(puansonuc));

	}
	
}

