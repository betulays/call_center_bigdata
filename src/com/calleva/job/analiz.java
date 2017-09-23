package com.calleva.job;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import com.calleva.mapper.acilisMapper;
import com.calleva.mapper.argoMapper;
import com.calleva.mapper.cagridurumMapper;
import com.calleva.mapper.cagritipMapper;
import com.calleva.mapper.cumletekrarMapper;
import com.calleva.mapper.isimbulMapper;
import com.calleva.mapper.kapanisMapper;
import com.calleva.mapper.puansonucMapper;
import com.calleva.mapper.uyariMapper;
import com.calleva.mapper.yardimMapper;
import com.calleva.mapper.yasakliMapper;
import com.calleva.reducer.acilisReducer;
import com.calleva.reducer.argoReducer;
import com.calleva.reducer.cagridurumReducer;
import com.calleva.reducer.cagritipReducer;
import com.calleva.reducer.cumletekrarReducer;
import com.calleva.reducer.isimbulReducer;
import com.calleva.reducer.kapanisReducer;
import com.calleva.reducer.puansonucReducer;
import com.calleva.reducer.uyariReducer;
import com.calleva.reducer.yardimReducer;
import com.calleva.reducer.yasaklikelimeReducer;
import com.calleva.xmlyaz.xmltojson;
import com.calleva.xmlyaz.xmlyaz;

public class analiz {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, ParserConfigurationException, TransformerException {
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(analiz.class);
		job.setJobName("Acilis");
		Scan scan = new Scan();
		scan.addFamily(Bytes.toBytes("gorusmeler"));

        TableMapReduceUtil.initTableMapperJob(
                "proje", 
                scan, 
                acilisMapper.class,
                Text.class,
                DoubleWritable.class, 
                job);
        
        TableMapReduceUtil.initTableReducerJob(
                "proje", 
                acilisReducer.class, 
                job);
		
		job.waitForCompletion(true);
		
		Configuration conf1 = new Configuration();
		Job job1 = Job.getInstance(conf1);
		job1.setJarByClass(analiz.class);
		job1.setJobName("Kapanis");
		Scan scan1 = new Scan();
		scan1.addFamily(Bytes.toBytes("gorusmeler"));
		
        TableMapReduceUtil.initTableMapperJob(
                "proje",
                scan1, 
                kapanisMapper.class, 
                Text.class, 
                DoubleWritable.class, 
                job1);
        
        TableMapReduceUtil.initTableReducerJob(
                "proje", 
                kapanisReducer.class, 
                job1);
		
		job1.waitForCompletion(true);
		
		Configuration conf2 = new Configuration();
		Job job2 = Job.getInstance(conf2);
		job2.setJarByClass(analiz.class);
		job2.setJobName("Çağrı Tip");
		Scan scan2 = new Scan();
		scan2.addFamily(Bytes.toBytes("gorusmeler"));
        TableMapReduceUtil.initTableMapperJob(
                "proje",
                scan2, 
                cagritipMapper.class, 
                Text.class, 
                Text.class, 
                job2);
        
        TableMapReduceUtil.initTableReducerJob(
                "proje", 
                cagritipReducer.class, 
                job2);
		
		job2.waitForCompletion(true);
		
		Configuration conf3 = new Configuration();
		Job job3 = Job.getInstance(conf3);
		job3.setJarByClass(analiz.class);
		job3.setJobName("Çağrı Durum");
		Scan scan3 = new Scan();
		scan3.addFamily(Bytes.toBytes("gorusmeler"));
        TableMapReduceUtil.initTableMapperJob(
                "proje",
                scan3, 
                cagridurumMapper.class, 
                Text.class, 
                Text.class, 
                job3);
        
        TableMapReduceUtil.initTableReducerJob(
                "proje", 
                cagridurumReducer.class, 
                job3);
		
		job3.waitForCompletion(true);
		
		Configuration conf4 = new Configuration();
		Job job4 = Job.getInstance(conf4);
		job4.setJarByClass(analiz.class);
		job4.setJobName("Uyari");
		Scan scan4 = new Scan();
		scan4.addFamily(Bytes.toBytes("gorusmeler"));
        TableMapReduceUtil.initTableMapperJob(
                "proje",
                scan4, 
                uyariMapper.class, 
                Text.class, 
                DoubleWritable.class, 
                job4);
        
        TableMapReduceUtil.initTableReducerJob(
                "proje", 
                uyariReducer.class, 
                job4);
		
		job4.waitForCompletion(true);
		
		Configuration conf5 = new Configuration();
		Job job5 = Job.getInstance(conf5);
		job5.setJarByClass(analiz.class);
		job5.setJobName("Yardım");
		Scan scan5 = new Scan();
		scan5.addFamily(Bytes.toBytes("gorusmeler"));
        TableMapReduceUtil.initTableMapperJob(
                "proje",
                scan5, 
                yardimMapper.class, 
                Text.class, 
                DoubleWritable.class, 
                job5);
        
        TableMapReduceUtil.initTableReducerJob(
                "proje", 
                yardimReducer.class, 
                job5);
		
		job5.waitForCompletion(true);
		
		Configuration conf6 = new Configuration();
		Job job6 = Job.getInstance(conf6);
		job6.setJarByClass(analiz.class);
		job6.setJobName("Argo");
		Scan scan6 = new Scan();
		scan6.addFamily(Bytes.toBytes("gorusmeler"));
        TableMapReduceUtil.initTableMapperJob(
                "proje",
                scan6, 
                argoMapper.class, 
                Text.class, 
                IntWritable.class, 
                job6);
        
        TableMapReduceUtil.initTableReducerJob(
                "proje", 
                argoReducer.class, 
                job6);
		
		job6.waitForCompletion(true);
		
		Configuration conf7 = new Configuration();
		Job job7 = Job.getInstance(conf7);
		job7.setJarByClass(analiz.class);
		job7.setJobName("isim");
		Scan scan7 = new Scan();
		scan7.addFamily(Bytes.toBytes("gorusmeler"));
        TableMapReduceUtil.initTableMapperJob(
                "proje",
                scan7, 
                isimbulMapper.class, 
                Text.class, 
                IntWritable.class, 
                job7);
        
        TableMapReduceUtil.initTableReducerJob(
                "proje", 
                isimbulReducer.class, 
                job7);
		
		job7.waitForCompletion(true);
		
		Configuration conf8 = new Configuration();
		Job job8 = Job.getInstance(conf8);
		job8.setJarByClass(analiz.class);
		job8.setJobName("yasakli");
		Scan scan8 = new Scan();
		scan8.addFamily(Bytes.toBytes("gorusmeler"));
        TableMapReduceUtil.initTableMapperJob(
                "proje",
                scan8, 
                yasakliMapper.class, 
                Text.class, 
                IntWritable.class, 
                job8);
        
        TableMapReduceUtil.initTableReducerJob(
                "proje", 
                yasaklikelimeReducer.class, 
                job8);
		
		job8.waitForCompletion(true);
		
		Configuration conf9 = new Configuration();
		Job job9 = Job.getInstance(conf9);
		job9.setJarByClass(analiz.class);
		job9.setJobName("cümle");
		Scan scan9 = new Scan();
		scan9.addFamily(Bytes.toBytes("gorusmeler"));
        TableMapReduceUtil.initTableMapperJob(
                "proje",
                scan9, 
                cumletekrarMapper.class, 
                Text.class, 
                IntWritable.class, 
                job9);
        
        TableMapReduceUtil.initTableReducerJob(
                "proje", 
                cumletekrarReducer.class, 
                job9);
		
		job9.waitForCompletion(true);
		
		Configuration conf10 = new Configuration();
		Job job10 = Job.getInstance(conf10);
		job10.setJarByClass(analiz.class);
		job10.setJobName("puansonuc");
		Scan scan10 = new Scan();
		scan10.addFamily(Bytes.toBytes("analiz"));
        TableMapReduceUtil.initTableMapperJob(
                "proje",
                scan10, 
                puansonucMapper.class, 
                Text.class, 
                DoubleWritable.class, 
                job10);
        
        TableMapReduceUtil.initTableReducerJob(
                "proje", 
                puansonucReducer.class, 
                job10);
		
		job10.waitForCompletion(true);

		
		xmlyaz x = new xmlyaz();
		x.xml();
		xmltojson cevir=new xmltojson();
		cevir.xmlcevir();
		
		System.exit(job10.waitForCompletion(true) ? 0 : 1);
		
	}
	}

