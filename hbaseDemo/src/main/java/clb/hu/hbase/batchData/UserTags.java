package clb.hu.hbase.batchData;

public class UserTags {
	private Integer tagid;
    private Integer userid;
    private Float realrank;
    
    public Integer getTagid() {
        return tagid;
    }
    
    public void setTagid(Integer tagid) {
    	this.tagid = tagid;
    }
    
    public  Integer getUid() {
        return this.userid;
    }
    
    public void setUid(Integer uid) {
        this.userid = uid;
    }
    
    public Float getRealrank() {
        return realrank;  
    }
    
    public void setRealrank(Float realrank) {  
    	this.realrank = realrank;  
    }
}