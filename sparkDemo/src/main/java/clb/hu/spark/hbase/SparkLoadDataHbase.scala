package clb.hu.spark.hbase

import org.apache.hadoop.hbase.{HBaseConfiguration, HTableDescriptor, TableName}  
import org.apache.hadoop.hbase.client.HBaseAdmin  
import org.apache.hadoop.hbase.mapreduce.TableInputFormat  
import org.apache.spark._  
import org.apache.hadoop.hbase.client.HTable  
import org.apache.hadoop.hbase.client.Put  
import org.apache.hadoop.hbase.util.Bytes  
import org.apache.hadoop.hbase.io.ImmutableBytesWritable  
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat  
import org.apache.hadoop.mapred.JobConf  
import org.apache.hadoop.io._  

/**
 *  Spark 批量写数据入HBase
 *  工作中常常会遇到这种情形，需要将hdfs中的大批量数据导入Hbase。
 *  本文使用Spark+hbase的方式将RDD中的数据导入HBase中。
 *  没有使用官网提供的newAPIHadoopRDD接口的方式。使用本文的方式将数据导入HBase, 7000W条数据，花费时间大概20分钟左右，本文Spark可用核数量为20。
 */
object SparkLoadDataHbase {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("SparkLoadDataHbase").setMaster("local[3]")
    val sc = new SparkContext(sparkConf)
    val readFile = sc.textFile("file:///F://hbase_test.txt").map(x => x.split(" "))
    val tableName = "hbase_test"
    var start = System.currentTimeMillis()
    readFile.foreachPartition {
      x =>
        {
          val myConf = HBaseConfiguration.create()
          myConf.set("hbase.zookeeper.quorum", "node3,node4,node2")
          myConf.set("hbase.zookeeper.property.clientPort", "2181")
          myConf.set("hbase.defaults.for.version.skip", "true")
          val myTable = new HTable(myConf, TableName.valueOf(tableName))
          myTable.setAutoFlush(false, false) //关键点1
          myTable.setWriteBufferSize(5 * 1024 * 1024) //关键点2
          x.foreach { y =>
            {
              println(y(0) + ":::" + y(1))
              val p = new Put(Bytes.toBytes(y(0)))
//              p.add("info".getBytes, "ss_sold_time_sk".getBytes, Bytes.toBytes(y(1)))
//              p.add("info".getBytes, "ss_item_sk".getBytes, Bytes.toBytes(y(2)))
//              p.add("info".getBytes, "ss_customer_sk".getBytes, Bytes.toBytes(y(3)))
              p.add("info".getBytes, "seqID".getBytes, Bytes.toBytes(y(1)))
              myTable.put(p)
            }
          }
          myTable.flushCommits() //关键点3
        }
    }
    var end = System.currentTimeMillis()
    print("插入数据所需时间为"+(end-start)+"毫秒")
  }
}