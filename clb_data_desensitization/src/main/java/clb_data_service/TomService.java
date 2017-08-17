package clb_data_service;

import java.sql.SQLException;

import java.util.List;

import clb_data_bean.Tom;
import clb_data_dao.TomDao;

public class TomService {

	
	TomDao td = new TomDao();
	
	public List<Tom> getToms() throws SQLException{
		return td.getToms();
	}
	
	
	
}
