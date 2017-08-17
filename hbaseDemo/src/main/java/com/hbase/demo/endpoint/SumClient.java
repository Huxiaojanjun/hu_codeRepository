package com.hbase.demo.endpoint;  
  
import java.io.IOException;  
import java.util.Map;  
  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.hbase.HBaseConfiguration;  
import org.apache.hadoop.hbase.TableName;  
import org.apache.hadoop.hbase.client.Connection;  
import org.apache.hadoop.hbase.client.ConnectionFactory;  
import org.apache.hadoop.hbase.client.HTable;  
import org.apache.hadoop.hbase.client.coprocessor.Batch;  
import org.apache.hadoop.hbase.ipc.BlockingRpcCallback;  
  
import com.google.protobuf.ServiceException;  
import com.hbase.demo.endpoint.Sum.SumRequest;  
import com.hbase.demo.endpoint.Sum.SumResponse;  
import com.hbase.demo.endpoint.Sum.SumService;  
  
  
/** 
 * 说明：hbase协处理器endpooint的客户端代码 
 * 功能：从服务端获取对hbase表指定列的数据的求和结果 
 */  
public class SumClient {  
  
    public static void main(String[] args) throws ServiceException, Throwable {  
          
        long sum = 0L;  //累加表的行数 
        int count = 0; //统计region的个数  
        // 配置Habse  
        Configuration conf = HBaseConfiguration.create();  
        conf.set("hbase.zookeeper.quorum", "node4");  
        conf.set("hbase.zookeeper.property.clientPort", "2181");  
        // 建立一个数据库的连接  
        Connection conn = ConnectionFactory.createConnection(conf);  
        // 获取表  
        HTable table = (HTable) conn.getTable(TableName.valueOf("sum_table"));  
        // 设置请求对象  
        final SumRequest request = SumRequest.newBuilder().setFamily("info").setColumn("age").build();  
        // 获得返回值  
        Map<byte[], Long> result = table.coprocessorService(SumService.class, null, null,   
                new Batch.Call<SumService, Long>() {
  
                    @Override  
                    public Long call(SumService service) throws IOException {  
                        BlockingRpcCallback<SumResponse> rpcCallback = new BlockingRpcCallback<SumResponse>();  
                        service.getSum(null, request, rpcCallback);  
                        SumResponse response = (SumResponse) rpcCallback.get();  
                        return response.hasSum() ? response.getSum() : 0L;  
                    }  
        });  
        // 将返回值进行迭代相加  
        for (Long v : result.values()) {  
            sum += v; 
            count++;
        }  
        // 输出行统计数
        System.out.println("sum: " + sum);
        //输出region的统计数
        System.out.println("region count = " + count);
        // 关闭资源  
        table.close();  
        conn.close();  
    }  
  
}  