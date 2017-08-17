package clb_data_udf;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;
import org.apache.hadoop.io.LongWritable;

/**
 * UDFRowSequence.
 */
@Description(name = "row_sequence",
    value = "_FUNC_() - Returns a generated row sequence number starting from 1")
@UDFType(deterministic = false, stateful = true)
public class UDFRowSequence extends UDF{
  private LongWritable result = new LongWritable();

//  public UDFRowSequence() {
//    result.set(0);
//  }
  public UDFRowSequence(int id) {
	    result.set(id);
  }
  public LongWritable evaluate() {
    result.set(result.get() + 1);
    return result;
  }
  
  public static void main(String[] args) {
	  UDFRowSequence udfRowSequence = new UDFRowSequence(8);
	System.out.println(udfRowSequence.evaluate());
  }
}