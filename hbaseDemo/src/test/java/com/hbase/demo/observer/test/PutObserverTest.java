package com.hbase.demo.observer.test;
import java.io.IOException;  

import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.hbase.HBaseConfiguration;  
import org.apache.hadoop.hbase.TableName;  
import org.apache.hadoop.hbase.client.Connection;  
import org.apache.hadoop.hbase.client.ConnectionFactory;  
import org.apache.hadoop.hbase.client.HTable;  
import org.apache.hadoop.hbase.client.Put;  
import org.apache.hadoop.hbase.util.Bytes;  
  
public class PutObserverTest {  
  
    public static void main(String[] args) throws IOException {  
          
        // 配置HBse  
        Configuration conf = HBaseConfiguration.create();  
        conf.set("hbase.zookeeper.quorum", "node4");  
        conf.set("hbase.zookeeper.property.clientPort", "2181");  
        // 建立一个数据库的连接  
        Connection conn = ConnectionFactory.createConnection(conf);  
        // 获取表  
        HTable table = (HTable) conn.getTable(TableName.valueOf("ob_table"));  
        // 插入测试数据  
        Put put = new Put(Bytes.toBytes("rowkey03"));  
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("changhai"));  
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("score"), Bytes.toBytes("66"));  
        table.put(put);  
        // 关闭资源  
        table.close();  
        conn.close();  
    }  
  
}  
