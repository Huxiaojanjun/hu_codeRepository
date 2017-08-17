package clb.hbase.rowkey.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 在使用HBase API建表的时候，需要产生splitkeys二维数组,这个数组存储的rowkey的边界值
 * 
 */
public class HBasePartition {

	private static final Log log = LogFactory.getLog(HBasePartition.class);

	/**
	 * 产生splitkeys二维数组，即预分区个数 用treeset对rowkey进行排序，必须要对rowkey排序，
	 * 否则在调用admin.createTable(tableDescriptor,splitKeys)的时候会出错。
	 */
	public static byte[][] getSplitKeys() {
		String[] keys = new String[] { "10|", "20|", "30|", "40|", "50|", "60|", "70|", "80|", "90|" };
		byte[][] splitKeys = new byte[keys.length][];
		TreeSet<byte[]> rows = new TreeSet<byte[]>(Bytes.BYTES_COMPARATOR);// 升序排序
		for (int i = 0; i < keys.length; i++) {
			rows.add(Bytes.toBytes(keys[i]));
		}
		Iterator<byte[]> rowKeyIter = rows.iterator();
		int i = 0;
		while (rowKeyIter.hasNext()) {
			byte[] tempRow = rowKeyIter.next();
			rowKeyIter.remove();
			splitKeys[i] = tempRow;
			i++;
		}
		return splitKeys;
	}

	/**
	 * 创建预分区hbase表
	 */
	public static boolean createTableBySplitKeys(String tableName, List<String> columnFamily) {
		if (StringUtils.isBlank(tableName) || columnFamily == null || columnFamily.size() < 0) {
			log.error("===Parameters tableName|columnFamily should not be null,Please check!===");
		}
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "node4");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		try {
			HBaseAdmin admin = new HBaseAdmin(conf);
			if (admin.tableExists(TableName.valueOf(tableName))) {
				return true;
			} else {
				HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
				for (String cf : columnFamily) {
					hTableDescriptor.addFamily(new HColumnDescriptor(cf));
				}
				byte[][] splitKeys = getSplitKeys();
				admin.createTable(hTableDescriptor, splitKeys);// 指定splitkeys
				log.info("===Create Table " + tableName + " Success!columnFamily:" + columnFamily.toString() + "===");
			}
		} catch (MasterNotRunningException e1) {
			e1.printStackTrace();
		} catch (ZooKeeperConnectionException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 获得随机的两位数
	 */
	private static String getRandomNumber() {
		String ranStr = Math.random() + "";
		int pointIndex = ranStr.indexOf(".");
		return ranStr.substring(pointIndex + 1, pointIndex + 3);
	}

	/**
	 * 批量插入数据
	 */
	private static List<Put> batchPut() {
		List<Put> list = new ArrayList<Put>();
		for (int i = 1; i <= 10000; i++) {
			byte[] rowkey = Bytes.toBytes(getRandomNumber() + "-" + System.currentTimeMillis() + "-" + i);
			Put put = new Put(rowkey);
			put.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("zs" + i));
			list.add(put);
		}
		return list;
	}

	public static void main(String[] args) {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "node4");
		List<String> list = new ArrayList<String>();
		list.add("info");
		createTableBySplitKeys("test88", list);
		HTable table;
		try {
			table = new HTable(conf, "test88");
			table.put(batchPut());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
