package com.java.assetmanagement.dao;

import java.sql.SQLException;
import java.text.ParseException;

import com.java.assetmanagement.exceptions.AssetNotFoundException;
import com.java.assetmanagement.exceptions.AssetNotMaintainException;
import com.java.assetmanagement.model.Assets;

public interface AssetManagementService {
	 boolean addAsset(Assets asset) throws ClassNotFoundException, SQLException;
	 boolean updateAsset(Assets asset) throws ClassNotFoundException, SQLException, AssetNotFoundException;
	 boolean deleteAsset(int assetid) throws ClassNotFoundException, SQLException, AssetNotFoundException;
	 boolean allocateAsset(int assetid,int employeeid,String allocationDate) throws ClassNotFoundException, SQLException, ParseException, AssetNotFoundException, AssetNotMaintainException;
	 boolean deallocateAsset(int assetid,int employeeid,String returnDate) throws ParseException, ClassNotFoundException, SQLException;
	 boolean performMaintenance(int assetId, String maintenanceDate,String description, double cost) throws ClassNotFoundException, SQLException, ParseException, AssetNotFoundException;
	 public boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate)
		        throws ClassNotFoundException, SQLException, ParseException, AssetNotFoundException, AssetNotMaintainException;
	 boolean withdrawReservation(int reservationId) throws ClassNotFoundException, SQLException;
}
