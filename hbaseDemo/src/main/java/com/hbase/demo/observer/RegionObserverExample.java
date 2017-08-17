package com.hbase.demo.observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.KeyValueUtil;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.util.Bytes;

public class RegionObserverExample extends BaseRegionObserver {
	 
    private static final byte[] ADMIN = Bytes.toBytes("001");
    private static final byte[] COLUMN_FAMILY = Bytes.toBytes("f1");
    private static final byte[] COLUMN = Bytes.toBytes("age");
    private static final byte[] VALUE = Bytes.toBytes("You can’t see Admin details");
     
    public void preGetOp(final ObserverContext<RegionCoprocessorEnvironment> e, final Get get, final List<Cell> results) throws IOException {
         
        if (Bytes.equals(get.getRow(),ADMIN)) {
            Cell c = CellUtil.createCell(get.getRow(),COLUMN_FAMILY, COLUMN, System.currentTimeMillis(), (byte)4, VALUE);
            results.add(c);
            e.bypass();
        }
         
        List kvs = new ArrayList(results.size());
        for (Cell c : results) {
            kvs.add(KeyValueUtil.ensureKeyValue(c));
        }
        preGetOp(e, get, kvs);
        results.clear();
        results.addAll(kvs);
    }
    
}
