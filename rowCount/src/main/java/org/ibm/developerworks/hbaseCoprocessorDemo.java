package org.ibm.developerworks;

import clb_jdbc_hive.*;


import org.apache.hadoop.hbase.zookeeper.ZKUtil;



import java.io.IOException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.coprocessor.Batch;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.ipc.BlockingRpcCallback;
import org.apache.hadoop.hbase.ipc.ServerRpcController;
import org.apache.hadoop.hbase.util.Bytes;

import org.apache.hadoop.hbase.ipc.CoprocessorRpcChannel;
import org.ibm.developerworks.getRowCount;
import org.ibm.developerworks.getRowCount.getRowCountResponse;
import org.ibm.developerworks.getRowCount.getRowCountRequest;
import org.ibm.developerworks.getRowCount.ibmDeveloperWorksService;
import org.ibm.developerworks.coprocessor.RowCountEndpoint;
import org.ibm.developerworks.coprocessor.RowCountObserver;
/**
 * demo program to show how to program with HBase coprocessor 
 * 演示程序演示如何使用HBase coprocessor程序进行编程
 */
public class hbaseCoprocessorDemo
{
    void createTable(String tableName) 
    {
      try{
        Configuration config = new Configuration();
        config.set("hbase.zookeeper.quorum", "node4:2181,node2:2181,node3:2181"); 
        HBaseAdmin admin = new HBaseAdmin(config);
        HTableDescriptor tableDesc = new HTableDescriptor(tableName);
        if(admin.tableExists(tableName) == true) {
          admin.disableTable(tableName);
          admin.deleteTable(tableName);
        }
        tableDesc.addFamily(new HColumnDescriptor("c1")); //add column family
        tableDesc.addCoprocessor("org.ibm.developerworks.coprocessor.RowCountObserver");
//        tableDesc.addCoprocessor("org.ibm.developerworks.coprocessor.RowCountEndpoint");
        admin.createTable(tableDesc);
      
      }
      catch(Exception e) {e.printStackTrace();}
    }
 
    
    

    void populateTenRows(String tableName, int rowCount)
    {
      try{
        Configuration config = new Configuration();
        config.set("hbase.zookeeper.quorum", "node4:2181,node2:2181,node3:2181");  
        HConnection conn = HConnectionManager.createConnection(config);
        HTableInterface tbl = conn.getTable(tableName);
        
        int seqID = 1;
        String hivetable = "dim_employee";
        Connection conn1 = HiveJdbcCli.getConn(); 
	    Statement  stmt = conn1.createStatement();  
	    String sql = "select * from clb_hospital_dw." + hivetable+" limit 150";  
	    System.out.println("Running:" + sql);  
	    ResultSet res = stmt.executeQuery(sql);
	    System.out.println("执行 select * query 运行结果:"); 
	    
	    while (res.next()) {  
//	        System.out.println(res.getInt(1) + "\t" + res.getString(2)+ "\t" + res.getString(3));
//	        for(int i=0; i< 100; i++)
//	        {
	          String rowkey =  hivetable + res.getString(2);
	          rowkey= rowkey.hashCode()+"";
	          Put put = new Put(rowkey.getBytes());
//	          put.add("c1".getBytes(),"col".getBytes(),"v".getBytes());
	          put.add("c1".getBytes(),"col_tabname".getBytes(),hivetable.getBytes());
	          put.add("c1".getBytes(),"col_codeId".getBytes(),res.getString(2).getBytes());
	          put.add("c1".getBytes(),"seqID".getBytes(),(""+seqID).getBytes());
	         
	          //这里生成的列值会相同
//	          put.add("c1".getBytes(),("col"+i).getBytes(),("v"+i).getBytes());
//	          boolean checkAndPut = tbl.checkAndPut(Bytes.toBytes(rowkey), Bytes.toBytes("c1"), Bytes.toBytes(""), Bytes.toBytes(""), put);
	          tbl.put(put);
//	          if(checkAndPut){
//	        	  put.add("c1".getBytes(),"seqID".getBytes(),(""+seqID).getBytes());
//	        	  tbl.put(put);
//	          }else{
//	        	  tbl.put(put);
//	          }
	         
	          seqID++;
//	        }
//	        for(int i=0; i< 100 - rowCount; i++){
////	          String rowkey = "r" + Integer.toString(i);
//	          String rowkey = hivetable + Integer.toString(i);
//	          Delete d = new Delete(rowkey.getBytes());
//	          tbl.delete(d);
//	        }
	    }  
	    
	
        
        
        
        
        /*
        //insert 1000
        for(int i=0; i< 1000; i++)
        {
          String rowkey = "r" + Integer.toString(i);
          Put put = new Put(rowkey.getBytes());
          put.add("c1".getBytes(),"col".getBytes(),"v".getBytes());
          //这里生成的列值会相同
//          put.add("c1".getBytes(),("col"+i).getBytes(),("v"+i).getBytes());
          tbl.put(put);           
        }
        for(int i=0; i< 1000 - rowCount; i++){
          String rowkey = "r" + Integer.toString(i);
          Delete d = new Delete(rowkey.getBytes());
          tbl.delete(d);
        }
*/
        
        
      }
      catch(Exception e) {e.printStackTrace();}
    }
    
    
    void populateTenRows1(String tableName, int rowCount)
    {
      try{
        Configuration config = new Configuration();
        config.set("hbase.zookeeper.quorum", "node4:2181,node2:2181,node3:2181");  
        HConnection conn = HConnectionManager.createConnection(config);
        HTableInterface tbl = conn.getTable(tableName);
        int seqID = 1;
        String hivetable = "customer";
        Connection conn1 = HiveJdbcCli.getConn(); 
	    Statement  stmt = conn1.createStatement();  
	    String sql = "select * from tpcds_bin_partitioned_parquet_100." + hivetable;  
	    System.out.println("Running:" + sql);  
	    ResultSet res = stmt.executeQuery(sql);
	    System.out.println("执行 select * query 运行结果:"); 
	    
	    while (res.next()) {  
	          String rowkey =  hivetable + res.getString(2);
	          rowkey= rowkey.hashCode()+"";
	          Put put = new Put(rowkey.getBytes());
	          put.add("c1".getBytes(),"col_tabname".getBytes(),hivetable.getBytes());
	          put.add("c1".getBytes(),"col_codeId".getBytes(),res.getString(2).getBytes());
	          //put.add("c1".getBytes(),"seqID".getBytes(),(""+seqID).getBytes());
//	          boolean checkAndPut = tbl.checkAndPut(Bytes.toBytes(rowkey), Bytes.toBytes("c1"), Bytes.toBytes("seqID"), ("").getBytes(), put);
	         tbl.put(put);
	          seqID++;

	    }  
      }
      catch(Exception e) {e.printStackTrace();}
    }
    
    
    long getTableRowCountBatch(String tableName) {
      long totalRowCount = 0;
	  Map<byte[], getRowCountResponse> results =null; 
      try{
        Configuration config = new Configuration();
        config.set("hbase.zookeeper.quorum", "node4:2181,node2:2181,node3:2181");  
        HConnection connection = HConnectionManager.createConnection(config);
        HTableInterface table = connection.getTable(tableName);
  
        org.ibm.developerworks.getRowCount.getRowCountRequest.Builder builder = getRowCountRequest.newBuilder();   
        builder.setReCount(false);
           
	byte[] s= Bytes.toBytes("r1");
	byte[] e= Bytes.toBytes("t1");
	results = 
        table.batchCoprocessorService(ibmDeveloperWorksService.getDescriptor().findMethodByName("getRowCount"), builder.build(),s, e, getRowCountResponse.getDefaultInstance()); //, callback);
      }
      catch(Exception e) {e.printStackTrace();}  
      catch(Throwable t) {;}
        Collection<getRowCountResponse> resultsc = results.values();
        for( getRowCountResponse r : resultsc)
        {
            totalRowCount += r.getRowCount();  
        }
        
      return totalRowCount;        
    }

    long getTableRowCountFast(String tableName) {
      System.out.println("getTableRowCountFast invoked for " + tableName);
        final AtomicLong totalRowCount = new AtomicLong();
      try{
        Configuration config = new Configuration();
        config.set("hbase.zookeeper.quorum", "node4:2181,node2:2181,node3:2181");  
        HConnection connection = HConnectionManager.createConnection(config);
        HTableInterface table = connection.getTable(tableName);
  

        Batch.Call<ibmDeveloperWorksService, getRowCountResponse> callable = 
         new Batch.Call<ibmDeveloperWorksService, getRowCountResponse>() {
           ServerRpcController controller = new ServerRpcController();
           BlockingRpcCallback<getRowCountResponse> rpcCallback = 
           new BlockingRpcCallback<getRowCountResponse>();

           @Override
           public getRowCountResponse call(ibmDeveloperWorksService instance) throws IOException {
           org.ibm.developerworks.getRowCount.getRowCountRequest.Builder builder = getRowCountRequest.newBuilder();   
           builder.setReCount(false);
           instance.getRowCount(controller, builder.build(), rpcCallback);
           return rpcCallback.get();
          }
         };
        Batch.Callback< getRowCountResponse> callback = 
        new Batch.Callback<getRowCountResponse>() {
        @Override
        public void update(byte[] region, byte[] row, getRowCountResponse result) {
                totalRowCount.getAndAdd(result.getRowCount());
            }
        };

        table.coprocessorService(ibmDeveloperWorksService.class, null, null, callable, callback);
        }
        catch(Exception e) {e.printStackTrace();}  
        catch(Throwable t) {;}
        return totalRowCount.get();
    }
    
    long getTableRowCountSlow(String tableName) {
      System.out.println("getTableRowCountSlow invoked for " + tableName);
        Map<byte[], getRowCountResponse> results=null;
      try{
        Configuration config = new Configuration();
        config.set("hbase.zookeeper.quorum", "node4:2181,node2:2181,node3:2181");  
        HConnection connection = HConnectionManager.createConnection(config);
        HTableInterface table = connection.getTable(tableName);
  
        Batch.Call<ibmDeveloperWorksService, getRowCountResponse> callable = 
         new Batch.Call<ibmDeveloperWorksService, getRowCountResponse>() {
           ServerRpcController controller = new ServerRpcController();
           BlockingRpcCallback<getRowCountResponse> rpcCallback = 
           new BlockingRpcCallback<getRowCountResponse>();

           @Override
           public getRowCountResponse call(ibmDeveloperWorksService instance) throws IOException {
           org.ibm.developerworks.getRowCount.getRowCountRequest.Builder builder = getRowCountRequest.newBuilder();   
           builder.setReCount(true);
           instance.getRowCount(controller, builder.build(), rpcCallback);
           return rpcCallback.get();
          }
         };

        results = table.coprocessorService(ibmDeveloperWorksService.class, null, null,
                                 callable);
        }
        catch(Exception e) {e.printStackTrace();}
        catch(Throwable t) {;}
                                 
        long totalRowCount = 0;
        Collection<getRowCountResponse> resultsc = results.values();
        for( getRowCountResponse r : resultsc)
        {
            totalRowCount += r.getRowCount();  
        }
        
        return totalRowCount;
    }

    long wholeTableRegionCount(String tableName, boolean fastMethod)
    {
      if(fastMethod == false){
        return getTableRowCountSlow(tableName);
        // 没有使用batch方法，读者可以自己修改这行代码，进行测试。注释掉上面一行，使用下面这行代码即可。
        //return getTableRowCountBatch(tableName);
      }
      else
          return getTableRowCountFast(tableName);
    }
    
    long singleRegionCount(String tableName, String rowkey,boolean reCount)
    {
      long rowcount = 0;
     try{
        Configuration config = new Configuration();
        config.set("hbase.zookeeper.quorum", "node4:2181,node2:2181,node3:2181");  
        HConnection conn = HConnectionManager.createConnection(config);
        HTableInterface tbl = conn.getTable(tableName);
      CoprocessorRpcChannel channel = tbl.coprocessorService(rowkey.getBytes()); 
      org.ibm.developerworks.getRowCount.ibmDeveloperWorksService.BlockingInterface service = org.ibm.developerworks.getRowCount.ibmDeveloperWorksService.newBlockingStub(channel);
      org.ibm.developerworks.getRowCount.getRowCountRequest.Builder request = org.ibm.developerworks.getRowCount.getRowCountRequest.newBuilder();
      request.setReCount(reCount);
      org.ibm.developerworks.getRowCount.getRowCountResponse ret = service.getRowCount(null, request.build());
      rowcount = ret.getRowCount();
     }
     catch(Exception e) {e.printStackTrace();}
      return rowcount;
    }
    
    
    
    public static void main( String[] args ){
        System.out.println( "Hello HBase Coprocessor!" );
        int argsize = args.length;
        System.out.println("input " + argsize + " arguments");
        if (argsize < 3) return ;
        String tblName = args[0];
        boolean fastMethod = (Integer.parseInt(args[1]) == 1) ? true : false;
        int num= Integer.parseInt(args[2]) ;
        String userRowKey = "r900"; //default one
        boolean wholeTableCount = true;
        if(argsize == 4) 
        {
            userRowKey = args[3];
            wholeTableCount = false;
        }
        hbaseCoprocessorDemo demo = new hbaseCoprocessorDemo();
//        demo.createTable(tblName);
        demo.populateTenRows1(tblName,num);
        if(wholeTableCount)
        {
            long wholeCount = demo.wholeTableRegionCount(tblName,fastMethod);   
            System.out.println("Get whole table count : " + wholeCount );
        }
        else
        {
            long singleCount = demo.singleRegionCount(tblName,userRowKey, fastMethod);
            System.out.println("Get single count: " + singleCount);
        }
        System.out.println( "bye!" );
    }
}
