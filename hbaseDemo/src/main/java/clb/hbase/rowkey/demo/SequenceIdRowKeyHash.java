package clb.hbase.rowkey.demo;

import org.apache.hadoop.hbase.util.Bytes;  
import org.apache.hadoop.hbase.util.MD5Hash;  

/**
 *  hbase 顺序序列rowkey设计
 */
public class SequenceIdRowKeyHash {

	public static void main(String[] args) {  
        long userid = 1000L;  
        byte[] bytes = Bytes.toBytes(userid);  
        String hashPrefix = MD5Hash.getMD5AsHex(bytes).substring(0, 4);  
        System.out.println("hashPrefix: "+hashPrefix);  
  
        byte[] bytes2 = Bytes.toBytes(hashPrefix);  
        //rowkey取md5(userid)的前四位+userid.前四位用来散列userid,避免写入热点。缺点，不支持顺序scan userId.  
        byte[] rowkey = Bytes.add(bytes2, bytes);  
          
        System.out.println("rowkey: "+rowkey);  
          
        //可通过rowkey逆推得到 userid  
        System.out.println(Bytes.toLong(rowkey, 4, rowkey.length - 4));  
    }  
}
