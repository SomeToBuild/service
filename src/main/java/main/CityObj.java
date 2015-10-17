package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CityObj implements GameObject {
	private String GUID;
	private String Owner;
	private int Lat;
	private int Lng;
	
	public CityObj(String owner,int lat,int lng){
		GUID=UUID.randomUUID().toString();
		Owner=owner;
		Lat=lat;
		Lng=lng;
	}
	public CityObj() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void GetDBData(Connection con, String GUID) {
		// TODO Auto-generated method stub
		PreparedStatement stmt=null;
		
		try {
			stmt=con.prepareStatement("SELECT GUID,Owner,Lat,Lng from cities WHERE GUID=? LIMIT 0,1");
			stmt.setString(1, GUID);
			ResultSet rs=stmt.executeQuery();
			rs.first();
			Owner=rs.getString("Owner");
			GUID=rs.getString("GUID");
			Lat=rs.getInt("Lat");
			Lng=rs.getInt("Lng");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void SetDBData(Connection con) {
		// TODO Auto-generated method stub
		PreparedStatement stmt=null;
		
		try {
			stmt=con.prepareStatement("INSERT IGNORE INTO Cities(GUID,Owner,Lat,Lng) VALUES "
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?"
					+ ")");
			stmt.setString(1, GUID);
			stmt.setString(2, Owner);
			stmt.setInt(3, Lat);
			stmt.setInt(4, Lng);
			stmt.execute();
			stmt=con.prepareStatement("INSERT IGNORE INTO AOBJECT(GUID,TYPE,Lat,Lng) VALUES("
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ ")");
			stmt.setString(1, GUID);
			stmt.setString(2, "CITY");
			stmt.setInt(3, Lat);
			stmt.setInt(4, Lng);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public String toString(){
		String result="Player:{"+
				"GUID:"+'"'+GUID+'"'+
				"Owner:"+'"'+Owner+'"'+
				"Lat:"+Lat+
				"Lng:"+Lng+
				"}";
		return result;
	}

}