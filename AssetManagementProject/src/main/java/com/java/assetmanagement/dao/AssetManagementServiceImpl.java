package com.java.assetmanagement.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.java.assetmanagement.exceptions.AssetNotFoundException;
import com.java.assetmanagement.exceptions.AssetNotMaintainException;
import com.java.assetmanagement.model.Assets;
import com.java.assetmanagement.model.Status;
import com.java.assetmanagement.util.ConnectionHelper;

public class AssetManagementServiceImpl implements AssetManagementService {

	Connection connection;
	PreparedStatement pst;
	
	@Override
	public boolean addAsset(Assets asset) throws ClassNotFoundException, SQLException {
		
		java.util.Date utildate = asset.getPurchase_date();
		java.util.Date today = new java.util.Date();
		if(Diff(today,utildate) >0) {
			System.out.println("Purchase Date cannot be in the future.");
		    return false;
		}
		
		connection = ConnectionHelper.getConnection();
		String cmd = "Insert into assets (name, type, serial_number, purchase_date, location, status, owner_id) values(?,?,?,?,?,?,?)";
		
		pst = connection.prepareStatement(cmd);
		pst.setString(1,asset.getName() );
		pst.setString(2,asset.getType().toString());
		pst.setString(3, asset.getSerial_number());
		pst.setDate(4, asset.getPurchase_date());
		pst.setString(5,asset.getLocation());
		pst.setString(6,asset.getStatus().toString());
		pst.setInt(7, asset.getOwner_id());
		return pst.executeUpdate() > 0;
	}

	@Override
	public boolean deleteAsset(int assetid) throws ClassNotFoundException, SQLException, AssetNotFoundException {
		connection = ConnectionHelper.getConnection();
		
	    String checkAssetSql = "SELECT COUNT(*) FROM assets WHERE asset_id = ?";
	    pst = connection.prepareStatement(checkAssetSql);
	    pst.setInt(1, assetid);
	    ResultSet rs = pst.executeQuery();
	    if (rs.next() && rs.getInt(1) == 0) {
	        rs.close();
	        pst.close();
	        throw new AssetNotFoundException("Asset with id " + assetid + " not found.");
	    }
	    rs.close();
	    pst.close();
	    
		String cmd ="Delete from assets where asset_id = ?";
		pst = connection.prepareStatement(cmd);
		pst.setInt(1, assetid);
		return pst.executeUpdate() >0;
	}

	@Override
	public boolean updateAsset(Assets asset) throws ClassNotFoundException, SQLException, AssetNotFoundException {
		connection = ConnectionHelper.getConnection();
		
		String checksql = "SELECT COUNT(*) FROM assets WHERE asset_id = ?";
		pst = connection.prepareStatement(checksql);
		pst.setInt(1,asset.getAsset_id());
		ResultSet rs = pst.executeQuery();
		if(rs.next()&& rs.getInt(1) == 0) {
			rs.close();
			pst.close();
			throw new AssetNotFoundException("Asset with id " + asset.getAsset_id() + " not found.");
		}
		java.util.Date utildate =asset.getPurchase_date();
		java.util.Date today = new java.util.Date();
		if(Diff(today,utildate) >0) {
			System.out.println("Entered Purchase Date cannot be in the future.");
		    return false;
		}
		String cmd ="Update assets set  name = ?, type = ?, serial_number = ?, purchase_date = ?, location = ?, status = ?, owner_id = ? where asset_id =?";
		pst = connection.prepareStatement(cmd);
		pst.setString(1, asset.getName());
		pst.setString(2,asset.getType().toString());
		pst.setString(3, asset.getSerial_number());
		pst.setDate(4, asset.getPurchase_date());
		pst.setString(5,asset.getLocation());
		pst.setString(6, asset.getStatus().toString());
		pst.setInt(7, asset.getOwner_id());
		pst.setInt(8, asset.getAsset_id());
		return pst.executeUpdate()>0;
		
	}
    
	public static Date conversql(java.util.Date utildate) {
		return new Date(utildate.getTime());
	}
	
	public static int Diff(java.util.Date start, java.util.Date end) {
		long ms = end.getTime() - start.getTime();
		int day = (int) (ms /(1000*60*60*24));
		return day;
	}
	
	@Override
	public boolean allocateAsset(int assetId, int employeeId, String allocationDate)
	        throws ClassNotFoundException, SQLException, ParseException, AssetNotFoundException, AssetNotMaintainException {

	    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date utilDate = sf.parse(allocationDate);
	    java.util.Date today = new java.util.Date();

	    if (Diff(today, utilDate) <0) {
	        System.out.println("Allocation date cannot be in the past.");
	        return false;
	    }

	    connection = ConnectionHelper.getConnection();

	    
	    String assetSql = "SELECT status FROM assets WHERE asset_id = ?";
	    pst = connection.prepareStatement(assetSql);
	    pst.setInt(1, assetId);
	    ResultSet rs = pst.executeQuery();

	    if (!rs.next()) {
	        pst.close();
	        rs.close();
	        throw new AssetNotFoundException("Asset with ID " + assetId + " does not exist.");
	    }

	    String status = rs.getString("status");
	    pst.close();
	    rs.close();

	    if ("DECOMISSIONED".equalsIgnoreCase(status)) {
	        System.out.println("Asset is decommissioned and cannot be allocated.");
	        return false;
	    }

	    
	    String maintenanceSql = "SELECT MAX(maintenance_date) FROM maintenance_records WHERE asset_id = ?";
	    pst = connection.prepareStatement(maintenanceSql);
	    pst.setInt(1, assetId);
	    rs = pst.executeQuery();

	    if (rs.next()) {
	        java.sql.Date lastMaintained = rs.getDate(1);
	        if (lastMaintained == null || Diff(today, lastMaintained) > 730) {
	            pst.close();
	            rs.close();
	            throw new AssetNotMaintainException("Asset hasn't been maintained for over 2 years.");
	        }
	    }
	    pst.close();
	    rs.close();

	    
	    String allocCheckSql = "SELECT return_date FROM asset_allocations WHERE asset_id = ? ORDER BY allocation_id DESC LIMIT 1";
	    pst = connection.prepareStatement(allocCheckSql);
	    pst.setInt(1, assetId);
	    rs = pst.executeQuery();

	    if (rs.next()) {
	        java.sql.Date returnDate = rs.getDate("return_date");
	        if (returnDate == null || Diff(utilDate, returnDate) > 0) {
	            pst.close();
	            rs.close();
	            System.out.println("Asset is currently allocated or return date is after requested allocation date.");
	            return false;
	        }
	    }
	    pst.close();
	    rs.close();


	    String insertSql = "INSERT INTO asset_allocations (asset_id, employee_id, allocation_date) VALUES (?, ?, ?)";
	    pst = connection.prepareStatement(insertSql);
	    pst.setInt(1, assetId);
	    pst.setInt(2, employeeId);
	    pst.setDate(3, conversql(utilDate));
	    
	    boolean inserted = pst.executeUpdate() > 0;

	    if (inserted) {
	        String updateStatusSQL = "UPDATE assets SET status = ? WHERE asset_id = ?";
	        pst = connection.prepareStatement(updateStatusSQL);
	        pst.setString(1,(Status.INUSE).toString());
	        pst.setInt(2, assetId);
	        pst.executeUpdate();
	    }

	    return inserted;
	}


  
	@Override
	public boolean deallocateAsset(int assetid, int employeeid, String returnDate) 
	        throws ParseException, ClassNotFoundException, SQLException {

	    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date utildate = sf.parse(returnDate);

	    connection = ConnectionHelper.getConnection();

	    String checkSql = "SELECT allocation_date FROM asset_allocations WHERE asset_id = ? AND employee_id = ? AND return_date IS NULL";
	    pst = connection.prepareStatement(checkSql);
	    pst.setInt(1, assetid);
	    pst.setInt(2, employeeid);
	    ResultSet rs = pst.executeQuery();

	    if (!rs.next()) {
	        System.out.println("No active allocation found for this asset and employee.");
	        return false;
	    }

	    java.util.Date allocationDate = rs.getDate("allocation_date");

	    if (Diff(allocationDate, utildate) < 0) {
	        System.out.println("Return date cannot be before allocation date.");
	        return false;
	    }

	    String updateSql = "UPDATE asset_allocations SET return_date = ? WHERE asset_id = ? AND employee_id = ? AND return_date IS NULL";
	    pst = connection.prepareStatement(updateSql);
	    pst.setDate(1, conversql(utildate));
	    pst.setInt(2, assetid);
	    pst.setInt(3, employeeid);

	    boolean inserted = pst.executeUpdate() > 0;

	    if (inserted) {
	        String updateStatusSQL = "UPDATE assets SET status = ? WHERE asset_id = ?";
	        pst = connection.prepareStatement(updateStatusSQL);
	        pst.setString(1,(Status.AVAILABLE).toString());
	        pst.setInt(2, assetid);
	        pst.executeUpdate();
	    }

	    return inserted;
	}




	@Override
	public boolean performMaintenance(int assetId, String maintenanceDate, String description, double cost)
	        throws ClassNotFoundException, SQLException, ParseException, AssetNotFoundException {

	    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date utildate = sf.parse(maintenanceDate);
	    connection = ConnectionHelper.getConnection();
	    java.util.Date today = new java.util.Date();

	    String checksql = "SELECT COUNT(*) FROM assets WHERE asset_id = ?";
	    pst = connection.prepareStatement(checksql);
	    pst.setInt(1, assetId);
	    ResultSet rs = pst.executeQuery();
	    if (rs.next() && rs.getInt(1) == 0) {
	        rs.close();
	        pst.close();
	        throw new AssetNotFoundException("Asset with id " + assetId + " not found.");
	    }

	    if (Diff(today, utildate) > 0) {
	        System.out.println("Maintenance Date cannot be in the future.");
	        return false;
	    }

	    String cmd = "INSERT INTO maintenance_records (asset_id, maintenance_date, description, cost) VALUES (?, ?, ?, ?)";
	    pst = connection.prepareStatement(cmd);
	    pst.setInt(1, assetId);
	    pst.setDate(2, conversql(utildate));
	    pst.setString(3, description);
	    pst.setDouble(4, cost);

	    boolean inserted = pst.executeUpdate() > 0;

	    if (inserted) {
	        String updateStatusSQL = "UPDATE assets SET status = ? WHERE asset_id = ?";
	        pst = connection.prepareStatement(updateStatusSQL);
	        pst.setString(1,(Status.UNDERMAINTAINENCE).toString());
	        pst.setInt(2, assetId);
	        pst.executeUpdate();
	    }

	    return inserted;
	}


	@Override
	public boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate)
	        throws ClassNotFoundException, SQLException, ParseException, AssetNotFoundException, AssetNotMaintainException {

	    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date resDate = sf.parse(reservationDate);
	    java.util.Date start = sf.parse(startDate);
	    java.util.Date end = sf.parse(endDate);

	  
	    if (Diff(resDate, start) < 0) {
	        System.out.println("Start date cannot be before reservation date.");
	        return false;
	    }

	    if (Diff(start, end) < 0) {
	        System.out.println("End date cannot be before start date.");
	        return false;
	    }

	    connection = ConnectionHelper.getConnection();

	
	    String checkAssetSql = "SELECT COUNT(*) FROM assets WHERE asset_id = ?";
	    pst = connection.prepareStatement(checkAssetSql);
	    pst.setInt(1, assetId);
	    ResultSet rs = pst.executeQuery();
	    if (rs.next() && rs.getInt(1) == 0) {
	    	pst.close();
	    	rs.close();
	        throw new AssetNotFoundException("Asset with id " + assetId + " not found.");
	    }
        
	    String maintenanceSql = "SELECT MAX(maintenance_date) FROM maintenance_records WHERE asset_id = ?";
	    pst = connection.prepareStatement(maintenanceSql);
	    pst.setInt(1, assetId);
	    rs = pst.executeQuery();
	    if (rs.next()) {
	        java.sql.Date lastMaintained = rs.getDate(1);
	        java.util.Date today = new java.util.Date();
	        if (lastMaintained == null || Diff(today,lastMaintained) > 730) {
	        	pst.close();
	        	rs.close();
	            throw new AssetNotMaintainException("Asset with id " + assetId + " hasn't been maintained for over 2 years.");
	        }
	    }
	 
	    String overlapSql = "SELECT COUNT(*) FROM reservations WHERE asset_id = ? AND " +
	            "(start_date <= ? AND end_date >= ?)";
	    pst = connection.prepareStatement(overlapSql);
	    pst.setInt(1, assetId);
	    pst.setDate(2, conversql(end));
	    pst.setDate(3, conversql(start));
	    rs = pst.executeQuery();
	    if (rs.next() && rs.getInt(1) > 0) {
	        System.out.println("Asset already reserved for the given dates.");
	        return false;
	    }

	    String insertSql = "INSERT INTO reservations (asset_id, employee_id, reservation_date, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
	    pst = connection.prepareStatement(insertSql);
	    pst.setInt(1, assetId);
	    pst.setInt(2, employeeId);
	    pst.setDate(3, conversql(resDate));
	    pst.setDate(4, conversql(start)); 
	    pst.setDate(5, conversql(end));

	    return pst.executeUpdate() > 0;
	}

	public boolean withdrawReservation(int reservationId) throws ClassNotFoundException, SQLException {
	    connection = ConnectionHelper.getConnection();

	    
	    String checkSql = "SELECT COUNT(*) FROM reservations WHERE reservation_id = ?";
	    pst = connection.prepareStatement(checkSql);
	    pst.setInt(1, reservationId);
	    ResultSet rs = pst.executeQuery();
	    if (rs.next() && rs.getInt(1) == 0) {
	        System.out.println("Reservation ID not found.");
	        return false;
	    }

	    
	    String deleteSql = "DELETE FROM reservations WHERE reservation_id = ?";
	    pst = connection.prepareStatement(deleteSql);
	    pst.setInt(1, reservationId);

	    return pst.executeUpdate() > 0;
	}
    
}
