package clb.hu.hbase.coprocess;

import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

/**
 * java实现hbase表中行数的统计，速度交hbase shell交互模式中执行hbase(main):002:0> count 'tableName'快很多
 * 在后端跑统计表行数的方法时，只需指定表名和列族名即可
 */
public class TableRowCountCoprocess {

	private static final Logger logger = Logger.getLogger(TableRowCountCoprocess.class);

	// 声明静态配置
    private static Configuration conf = null;
 
    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "node4:2181,node2:2181,node3:2181");  
    }
    
	public static void addTableCoprocessor(String tableName, String coprocessorClassName) {  
	    try {  
			Connection connection = ConnectionFactory.createConnection(conf);
	    	Admin admin = connection.getAdmin();
	        admin.disableTable(TableName.valueOf(Bytes.toBytes(tableName)));  
	        HTableDescriptor htd = admin.getTableDescriptor(TableName.valueOf(Bytes.toBytes(tableName)));
	        htd.addCoprocessor(coprocessorClassName);  
	        admin.modifyTable(TableName.valueOf(Bytes.toBytes(tableName)), htd);  
	        admin.enableTable(TableName.valueOf(Bytes.toBytes(tableName)));  
	    } catch (IOException e) {  
	        logger.info(e.getMessage(), e);  
	    }  
	}  
	      
	public static long rowCount(String tableName, String family) {  
	    AggregationClient ac = new AggregationClient(conf);    
	    Scan scan = new Scan();  
	    scan.addFamily(Bytes.toBytes(family));  
	    long rowCount = 0;  
	    try {  
	        rowCount = ac.rowCount(TableName.valueOf(Bytes.toBytes(tableName)), new LongColumnInterpreter(), scan);  
	    } catch (Throwable e) {  
	        logger.info(e.getMessage(), e);  
	    }    
	    return rowCount;  
	}  
	
 
	public static void main(String[] args) {

	    String coprocessorClassName = "org.apache.hadoop.hbase.coprocessor.AggregateImplementation";
	    /**
	     * 每次首次需要加载统计hbase表行数的协处理器，加载后，以后无需再次添加，直接可以统计即可
	     * 若再次添加协处理器，则会报错
	     */
//	    TableRowCountCoprocess.addTableCoprocessor("split_table_test", coprocessorClassName);
	    
	    long start = System.currentTimeMillis();
	    long rowCount = TableRowCountCoprocess.rowCount("split_table_test", "info");  
	    System.out.println("rowCount: " + rowCount);  
	    long end = System.currentTimeMillis();
        System.out.println("运行时间：" + (end - start) + "毫秒");//应该是end - start 
}

}
