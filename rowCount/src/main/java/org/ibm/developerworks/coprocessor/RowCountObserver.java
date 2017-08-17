package org.ibm.developerworks.coprocessor;

import java.io.IOException;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.IsolationLevel;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.HStore;
import org.apache.hadoop.hbase.regionserver.Store;
import org.apache.hadoop.hbase.regionserver.ScanInfo;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.regionserver.KeyValueScanner;
import org.apache.hadoop.hbase.regionserver.ScanType;
import org.apache.hadoop.hbase.regionserver.Store;
import org.apache.hadoop.hbase.regionserver.StoreScanner;
import org.apache.hadoop.hbase.regionserver.HRegion;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.EnvironmentEdgeManager;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.joni.Region;
import org.apache.hadoop.hbase.zookeeper.ZooKeeperWatcher;
import org.apache.hadoop.hbase.zookeeper.ZKUtil;
import org.apache.hadoop.hbase.regionserver.RegionServerServices;

public class RowCountObserver extends BaseRegionObserver {
  RegionCoprocessorEnvironment env;
  public static final Log LOG = LogFactory.getLog(RowCountObserver.class);
  public String zNodePath = "/hbase/ibmdeveloperworks/demo/";
  public ZooKeeperWatcher zkw = null;
  public long myrowcount=0;
  public boolean initcount = false;
  public HRegion m_region;

  @Override
  public void start(CoprocessorEnvironment e) throws IOException {
	  env = (RegionCoprocessorEnvironment) e;
	  RegionCoprocessorEnvironment re =(RegionCoprocessorEnvironment)e; 
	  RegionServerServices rss = re.getRegionServerServices();
//	  m_region =  re.getRegion();
	  m_region = (HRegion) re.getRegion();
//	  zNodePath = zNodePath+m_region.getRegionNameAsString();
	  zNodePath = zNodePath+m_region.getRegionInfo().getRegionNameAsString();
	  zkw = rss.getZooKeeper();
	  myrowcount = 0 ; //count;
	  initcount = false;
	
	  try{
	    if(ZKUtil.checkExists(zkw,zNodePath) == -1) {	
	       LOG.error("LIULIUMI: cannot find the znode");
	       ZKUtil.createWithParents(zkw,zNodePath);
	       LOG.info("znode path is : " + zNodePath);
	     }
	   } catch (Exception ee) {LOG.error("LIULIUMI: create znode fail"); }
  }

  @Override
  public void stop(CoprocessorEnvironment e) throws IOException {
    // nothing to do here
  }
  
  @Override
  public void preDelete (ObserverContext<RegionCoprocessorEnvironment> e,
              Delete delete,
              WALEdit edit,
              Durability durability)
                throws IOException {

   myrowcount--;
   try{
       ZKUtil.setData(zkw,zNodePath,Bytes.toBytes(myrowcount));
   }
   catch (Exception ee) {LOG.info("setData exception");}
    }

  @Override
  public void postOpen(ObserverContext<RegionCoprocessorEnvironment> e)
  {
   LOG.info("LIULIUMI post open invoked");
   long count = 0;
   try{
   if(initcount == false) {
    Scan scan = new Scan();
    InternalScanner scanner = null;
    scanner = m_region.getScanner(scan);
    List<Cell> results = new ArrayList<Cell>();
    boolean hasMore = false;
    do {
          hasMore = scanner.next(results);
          if(results.size()>0)
          count++;
    } while (hasMore);
    initcount = true;
   }
   ZKUtil.setData(zkw,zNodePath,Bytes.toBytes(count));
   }
   catch (Exception ee) {LOG.info("setData exception");}
  }

  @Override
  public void prePut (ObserverContext<RegionCoprocessorEnvironment> e,
              Put put,
              WALEdit edit,
              Durability durability)
                throws IOException {
  
   myrowcount++;
   try{
     ZKUtil.setData(zkw,zNodePath,Bytes.toBytes(myrowcount));
   }
   catch (Exception ee) {LOG.info("setData exception");}
    }

  @Override
  public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, 
	      Put put, WALEdit edit, Durability durability) {
	byte[] data;
	try {
		HTableInterface table = e.getEnvironment().getTable(TableName.valueOf(m_region.getTableDesc().getTableName()+"_seq"));
		data = ZKUtil.getData(zkw, zNodePath);
		Long rc = Bytes.toLong(data);
		put.add("c1".getBytes(), "seqID".getBytes(), String.valueOf(rc).getBytes());
		table.put(put);
	} catch (KeeperException e1) {
		e1.printStackTrace();
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}catch (Exception e1) {
		e1.printStackTrace();
	}
  }
}
