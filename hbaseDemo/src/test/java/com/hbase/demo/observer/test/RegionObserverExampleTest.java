package com.hbase.demo.observer.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import clb.hbase.jdbc.HBaseJavaAPI;

public class RegionObserverExampleTest {

	public static void main(String[] args) throws Exception{

		Configuration conf = HBaseConfiguration.create();
		  conf.set("hbase.zookeeper.quorum", "node4");
	        conf.set("hbase.zookeeper.property.clientPort", "2181");
		HConnection connection = HConnectionManager.createConnection(conf);
		HTableInterface table = connection.getTable("test");
		Get get = new Get(Bytes.toBytes("002"));
		Result result = table.get(get);
		    // 输出结果,raw方法返回所有keyvalue数组
		   for (KeyValue rowKV : result.raw()) {
		        System.out.print("行名:" + new String(rowKV.getRow()) + " ");
		        System.out.print("时间戳:" + rowKV.getTimestamp() + " ");
		        System.out.print("列族名:" + new String(rowKV.getFamily()) + " ");
		        System.out.print("列名:" + new String(rowKV.getQualifier()) + " ");
		        System.out.println("值:" + new String(rowKV.getValue()));
		    }
		
		Scan scan = new Scan();
		ResultScanner scanner = table.getScanner(scan);
		 for (Result result1 : scanner) {
	            for (KeyValue rowKV : result1.raw()) {
	                System.out.print("行名:" + new String(rowKV.getRow()) + " ");
	                System.out.print("时间戳:" + rowKV.getTimestamp() + " ");
	                System.out.print("列族名:" + new String(rowKV.getFamily()) + " ");
	                System.out.print("列名:" + new String(rowKV.getQualifier()) + " ");
	                System.out.println("值:" + new String(rowKV.getValue()));
	            }
	      }
		
		
	}
}
