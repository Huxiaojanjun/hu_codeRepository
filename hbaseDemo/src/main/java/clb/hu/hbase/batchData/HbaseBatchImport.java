package clb.hu.hbase.batchData;


  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.hbase.client.Put;  
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;  
import org.apache.hadoop.hbase.mapreduce.TableReducer;  
import org.apache.hadoop.hbase.util.Bytes;  
import org.apache.hadoop.io.LongWritable;  
import org.apache.hadoop.io.NullWritable;  
import org.apache.hadoop.io.Text;  
import org.apache.hadoop.mapreduce.Counter;  
import org.apache.hadoop.mapreduce.Job;  
import org.apache.hadoop.mapreduce.Mapper;  
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;  
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

import clb.hbase.utils.StringReverseUtil;  

/**
 * 利用读取文件数据加载到hbase表中
 * 测试数据200万条数据，批量插入时间需要1分40s左右
 *
 */
public class HbaseBatchImport {  
  
    public static void main(String[] args) throws Exception {  
    	
        long start = System.currentTimeMillis();
     
    	
    	System.setProperty("hadoop.home.dir", "E:\\software\\hadoop-2.6.0");
        Configuration configuration = new Configuration();  
        // 设置zookeeper  
//        configuration.set("hbase.rootdir", "hdfs://master:8020/hbase");
        configuration.set("hbase.zookeeper.quorum", "node4");  
  
        // 设置hbase表名称  
        configuration.set(TableOutputFormat.OUTPUT_TABLE, "split_table_test");  
  
        // 将该值改大，防止hbase超时退出  
        configuration.set("dfs.socket.timeout", "180000");  
  
        final Job job = new Job(configuration, "HBaseBatchImport");  
  
//        job.setMapperClass(BatchImportMapper.class);  
        job.setReducerClass(BatchImportReducer.class);  
        // 设置map的输出，不设置reduce的输出类型  
        job.setMapOutputKeyClass(LongWritable.class);  
        job.setMapOutputValueClass(Text.class);  
  
        job.setInputFormatClass(TextInputFormat.class);  
        // 不再设置输出路径，而是设置输出格式类型  
        job.setOutputFormatClass(TableOutputFormat.class);  
  
        FileInputFormat.setInputPaths(job, "hdfs://master:8020/test/bulkLoadHbase/000000_0");  
//        FileInputFormat.setInputPaths(job, "hdfs://master:8020/test/bulkLoadHbase/1.txt");  
  
        job.waitForCompletion(true); 
        
        long end = System.currentTimeMillis();
        System.out.println("运行时间：" + (end - start) + "毫秒");//应该是end - start 
    }  
    
    static String dataTable = "customer";
    static class BatchImportMapper extends Mapper<LongWritable, Text, LongWritable, Text> {  
        Text v2 = new Text();  
  
        protected void map(LongWritable key, Text value, Context context)  
                throws java.io.IOException, InterruptedException {  
            String[] splited = value.toString().split(" ");  
            try {  
                String rowKey = getRowkey(dataTable+splited[0]); 
                v2.set(rowKey + " " + value.toString()); 
                context.write(key, v2);  
            } catch (NumberFormatException e) {  
                final Counter counter = context.getCounter("BatchImport","ErrorFormat");  
                counter.increment(1L);  
                System.out.println("出错了" + splited[1] + " " + e.getMessage());  
            }  
        };  
    }  
  
    static class BatchImportReducer extends TableReducer<LongWritable, Text, NullWritable> {  
        protected void reduce(LongWritable key,java.lang.Iterable<Text> values, Context context)  
                throws java.io.IOException, InterruptedException { 
        	
        	
            for (Text text : values) {  
                String[] splited = text.toString().split(" "); 
                //对rowkey进行反转
//                Put put = new Put(Bytes.toBytes(new StringReverseUtil().reverse(dataTable+splited[1]))); 
//                Put put = new Put(Bytes.toBytes(dataTable+splited[1])); 
                Put put = new Put(Bytes.toBytes(getRowkey(dataTable+splited[1]))); 
                put.add("info".getBytes(),"seqID".getBytes(),splited[0].getBytes());
                put.add("info".getBytes(),"tab_name".getBytes(),dataTable.getBytes());
                put.add("info".getBytes(),"c_customer_sk".getBytes(),splited[1].getBytes());
            	
            	
//            	  String[] splited = text.toString().split(""); 
//                  Put put = new Put(Bytes.toBytes(splited[0])); 
//                  put.add("info".getBytes(),"ss_sold_time_sk".getBytes(),splited[1].getBytes());
//                  put.add("info".getBytes(),"ss_item_sk".getBytes(),splited[2].getBytes());
//                  put.add("info".getBytes(),"ss_customer_sk".getBytes(),splited[3].getBytes());
                
                // 省略其他字段，调用put.add(....)即可  
                context.write(NullWritable.get(), put);  
            }  
       
        };  
    }  
    
    /**
     * hbase数据加盐（Salting）存储
     */
    public static String getRowkey(String rowKey){
        int splitsCount= 10;

        int saltingCode = rowKey.hashCode()%splitsCount;

        String saltingKey= ""+ saltingCode;

        if(saltingCode < 10)
        {
            saltingKey = "00" + saltingKey;
        }else {
        	saltingKey = "0" + saltingKey;
        }
        rowKey = saltingKey + rowKey;
        return rowKey;
    }
    
  
}  