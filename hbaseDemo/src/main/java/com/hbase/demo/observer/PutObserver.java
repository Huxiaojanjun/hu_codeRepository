package com.hbase.demo.observer;  
  
import java.io.IOException;  

import java.util.List;  
  
import org.apache.hadoop.hbase.Cell;  
import org.apache.hadoop.hbase.CellUtil;  
import org.apache.hadoop.hbase.TableName;  
import org.apache.hadoop.hbase.client.Durability;  
import org.apache.hadoop.hbase.client.HTableInterface;  
import org.apache.hadoop.hbase.client.Put;  
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;  
import org.apache.hadoop.hbase.coprocessor.ObserverContext;  
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;  
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;  
import org.apache.hadoop.hbase.util.Bytes;  
  
/** 
 * 二级索引
 * 说明：hbase协处理器observer的应用逻辑代码 
 * 功能：在应用了该observer的hbase表中，所有的put操作，都会将每行数据的info:name列值作为rowkey、info:score列值作为value 
 * 写入另一张二级索引表index_ob_table，可以提高对于特定字段的查询效率 
 */  



  /**
   * 一般来说,对数据库建立索引，往往需要单独的数据结构来存储索引的数据。
   * 在hbase表中，除了使用rowkey索引数据外，还可以另外建立一张索引表，查询时先查询索引表，
   * 然后用查询结果查询数据表。下面这个示例演示如何使用Observer协处理器生成HBase表的二级索引：
   * 将数据表ob_table中列info:name的值作为索引表index_ob_table的rowkey，
   * 将数据表ob_table中列info:score的值作为索引表index_ob_table中列info:score的值建立二级索引，
   * 当用户向数据表中插入数据时，索引表将自动插入二级索引，从而为查询业务数据提供了便利。
   */
@SuppressWarnings("deprecation")  
public class PutObserver extends BaseRegionObserver{  
      
    @Override  
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e,   
            Put put, WALEdit edit, Durability durability) throws IOException {  
        // 获取二级索引表  
        HTableInterface table = e.getEnvironment().getTable(TableName.valueOf("index_ob_table"));  
        // 获取值  
        List<Cell> cellList1 = put.get(Bytes.toBytes("info"), Bytes.toBytes("name"));  
        List<Cell> cellList2 = put.get(Bytes.toBytes("info"), Bytes.toBytes("score"));  
        // 将数据插入二级索引表  
        for (Cell cell1 : cellList1) {  
            // 列info:name的值作为二级索引表的rowkey  
            Put indexPut = new Put(CellUtil.cloneValue(cell1));  
            for (Cell cell2 : cellList2) {  
                // 列info:score的值作为二级索引表中列info:score的值  
                indexPut.add(Bytes.toBytes("info"), Bytes.toBytes("score"), CellUtil.cloneValue(cell2));  
            }  
            // 数据插入二级索引表  
            table.put(indexPut);  
        }  
        // 关闭资源  
        table.close();  
    }  
      
}  