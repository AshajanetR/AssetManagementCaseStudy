package com.java.assetmanagement.main;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.java.assetmanagement.dao.AssetManagementService;
import com.java.assetmanagement.dao.AssetManagementServiceImpl;
import com.java.assetmanagement.exceptions.AssetNotFoundException;
import com.java.assetmanagement.exceptions.AssetNotMaintainException;
import com.java.assetmanagement.model.Assets;
import com.java.assetmanagement.model.Status;
import com.java.assetmanagement.model.Type;


public class AssetManagementApp {
	
	
		static Scanner sc;
		static AssetManagementService assetdao;
	    
		static {
			assetdao = new AssetManagementServiceImpl();
			sc = new Scanner(System.in);
		}
	
      public static void main(String[] args) throws ParseException, ClassNotFoundException, SQLException, AssetNotFoundException, AssetNotMaintainException {
    	  int choice;
    	  
		do {
			  System.out.println("\n--------------------------------------\n");
	    	  System.out.println("O P T I O N S");
	    	  System.out.println("-------------");
	    	  System.out.println("1. Add Asset");
	    	  System.out.println("2. Update Asset");
	    	  System.out.println("3. Delete Asset");
	    	  System.out.println("4. Allocate Asset");
	    	  System.out.println("5. DeAllocateAsset");
	    	  System.out.println("6. Add Maintenance Record");
	    	  System.out.println("7. Reserve Asset");
	    	  System.out.println("8. Withdraw Reservation");
	    	  System.out.println("9. Exit");
	    	  System.out.println("Enter your Choice: ");
			  choice = sc.nextInt();
			  
			  switch(choice) {
			  case 1:
				  AddAssetMain();
				  break;
			  case 2:
				  UpdateAssetMain();
				  break;
			  case 3:
				  DeleteAssetMain();
				  break;
			  case 4:
				  AllocateassetMain();
				  break;
			  case 5:
				  DeAllocateassetMain();
				  break;
			  case 6:
				  MaintenanceRecordsMain();
				  break;
			  case 7:
				  AssetReservationMain();
				  break;
			  case 8:
				  WithdrwalReservationMain();
				  break;
			  }
		}while(choice!=9);
		
	}
    
    public static Date conversql(java.util.Date util1) {
    	return new Date(util1.getTime()); 
    }
    
    public static void AddAssetMain() throws ParseException, ClassNotFoundException, SQLException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Assets asset = new Assets();

        System.out.println("Enter asset name: ");
        sc.nextLine(); 
        asset.setName(sc.nextLine());

        System.out.println("Enter asset type (vehicle/laptop/equipment): ");
        asset.setType(Type.valueOf(sc.next().toUpperCase()));

        System.out.println("Enter asset serial number: ");
        asset.setSerial_number(sc.next());

        System.out.println("Enter asset purchase date (yyyy-MM-dd) (past): ");
        String purchasedate = sc.next();
        java.util.Date util1 = sf.parse(purchasedate);
        asset.setPurchase_date(conversql(util1));

        System.out.println("Enter asset location: ");
        sc.nextLine(); 
        asset.setLocation(sc.nextLine());

        System.out.println("Enter asset owner id: ");
        asset.setOwner_id(sc.nextInt());

        asset.setStatus(Status.AVAILABLE);

        boolean result = assetdao.addAsset(asset);
        if (result)
            System.out.println("Asset Added Successfully");
        else
            System.out.println("Asset can't be Added. Please try again.");
    }

    
    public static void DeleteAssetMain() {
        int assetid;
        System.out.println("Enter the assetid to delete: ");
        assetid = sc.nextInt();
        boolean result = false;
        try {
            result = assetdao.deleteAsset(assetid);
            if (result)
                System.out.println("Asset Deleted Successfully.");
            else
                System.out.println("Failed to Delete. Please try again.");
        } catch (AssetNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void UpdateAssetMain() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Assets a = new Assets();

        System.out.println("Enter asset id: ");
        a.setAsset_id(sc.nextInt());
        sc.nextLine();

        System.out.println("Enter asset name: ");
        a.setName(sc.nextLine());

        System.out.println("Enter asset type (vehicle/laptop/equipment): ");
        a.setType(Type.valueOf(sc.next().toUpperCase()));

        System.out.println("Enter asset serial number: ");
        a.setSerial_number(sc.next());

        System.out.println("Enter asset purchase date (yyyy-MM-dd)(past): ");
        String purchasedate = sc.next();
        java.util.Date utilDate = sf.parse(purchasedate);
        a.setPurchase_date(conversql(utilDate));

        sc.nextLine(); 
        System.out.println("Enter asset location: ");
        a.setLocation(sc.nextLine());

        System.out.println("Enter asset status (inuse/decomissioned/undermaintainence): ");
        a.setStatus(Status.valueOf(sc.next().toUpperCase()));

        System.out.println("Enter asset owner id: ");
        a.setOwner_id(sc.nextInt());

        boolean result;
        try {
            result = assetdao.updateAsset(a);
            if (result)
                System.out.println("Asset Updated Successfully.");
            else
                System.err.println("Failed to Update. Please try Again.");
        } catch (AssetNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    
    public static void AllocateassetMain() {
        System.out.println("Enter the asset id: ");
        int assetid = sc.nextInt();

        System.out.println("Enter the employee id: ");
        int employeeid = sc.nextInt();

        System.out.println("Enter the allocation date (yyyy-MM-dd): ");
        String alldate = sc.next();

        try {
            boolean result = assetdao.allocateAsset(assetid, employeeid, alldate);
            if (result)
                System.out.println("Asset allocation is successfully done.");
            else
                System.out.println("Failed to allocate asset. Please try again.");
        } catch (AssetNotFoundException | AssetNotMaintainException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            System.err.println("System error during allocation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public static void DeAllocateassetMain() {
        System.out.println("Enter the asset id: ");
        int assetid = sc.nextInt();

        System.out.println("Enter the employee id: ");
        int employeeid = sc.nextInt();

        System.out.println("Enter the return date: ");
        String returndate = sc.next();

        try {
            boolean result = assetdao.deallocateAsset(assetid, employeeid, returndate);
            if (result)
                System.out.println("Deallocated asset based on return date");
            else
                System.out.println("Deallocation cannot be done. Please try again.");
        } catch (ClassNotFoundException | ParseException | SQLException e) {
            System.err.println("Error during deallocation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public static void MaintenanceRecordsMain() {
        System.out.println("Enter the asset id: ");
        int assetid = sc.nextInt();
        System.out.println("Enter the maintenance date: ");
        String mdate = sc.next();
        System.out.println("Enter the description: ");
        sc.nextLine(); 
        String desc = sc.nextLine(); 
        System.out.println("Enter the cost:");
        double cost = sc.nextDouble();

        try {
            boolean result = assetdao.performMaintenance(assetid, mdate, desc, cost);
            if (result)
                System.out.println("Maintenance history saved.");
            else
                System.out.println("Maintenance history can't be saved. Please check details.");
        } catch (AssetNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
    
    public static void AssetReservationMain() {
        System.out.println("Enter the asset id: ");
        int assetid = sc.nextInt();

        System.out.println("Enter the employee id: ");
        int employeeid = sc.nextInt();

       
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String reservedate = sdf.format(new java.util.Date());

        System.out.println("Enter the start date: ");
        String startdate = sc.next();

        System.out.println("Enter the end date: ");
        String enddate = sc.next();

        try {
            boolean result = assetdao.reserveAsset(assetid, employeeid, reservedate, startdate, enddate);
            if (result)
                System.out.println("Reservation is successful");
            else
                System.out.println("Reservation failed. Please try again.");
        } catch (AssetNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (AssetNotMaintainException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    
    public static void WithdrwalReservationMain() {
    	System.out.println("Enter the reservation id: ");
    	int reservationid = sc.nextInt();
    	
    	try {
			boolean result = assetdao.withdrawReservation(reservationid);
			if(result)
				System.out.println("Withdrawal of reservation is succesful");
			else
				System.out.println("Cannot Withdraw the reservation.Please try again.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
