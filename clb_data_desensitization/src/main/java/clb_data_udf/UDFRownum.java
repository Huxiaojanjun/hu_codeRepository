package clb_data_udf;

import org.apache.hadoop.hive.ql.exec.UDF;

public class UDFRownum extends UDF{

	private int counter=0;

	public int evaluate(final String key) {
	   return this.counter++;
	}

	public int evaluate(final int key){
	  return this.counter++;
	}
	
	public static void main(String[] args) {

	}
	
}
