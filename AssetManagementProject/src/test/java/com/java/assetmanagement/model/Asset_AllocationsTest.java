package com.java.assetmanagement.model;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

public class Asset_AllocationsTest {

	@Test
	public void testconstructor() throws ParseException {
		String d1 ="2025-04-10";
		String d2 = "2025-04-10";
		SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date ud1 = sf.parse(d1);
		java.util.Date ud2 = sf.parse(d2);
		Date sd1 = new Date(ud1.getTime());
		Date sd2 = new Date(ud2.getTime());
		
		
		Asset_Allocations a = new Asset_Allocations(1,4,1,sd1,sd2);
		assertEquals(1, a.getAllocation_id());
		assertEquals(4, a.getAsset_id());
		assertEquals(1, a.getEmployee_id());
		assertEquals(sd1, a.getAllocation_date());
		assertEquals(sd2, a.getReturn_date());
		
	}
	
	@Test 
	public void testsettersandgetters() throws ParseException {
		String d1 ="2025-04-10";
		String d2 = "2025-04-10";
		SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date ud1 = sf.parse(d1);
		java.util.Date ud2 = sf.parse(d2);
		Date sd1 = new Date(ud1.getTime());
		Date sd2 = new Date(ud2.getTime());
		
		Asset_Allocations a = new Asset_Allocations();
		a.setAllocation_id(1);
		a.setAsset_id(4);
		a.setEmployee_id(1);
		a.setAllocation_date(sd1);
        a.setReturn_date(sd2);
        
        assertEquals(1, a.getAllocation_id());
		assertEquals(4, a.getAsset_id());
		assertEquals(1, a.getEmployee_id());
		assertEquals(sd1, a.getAllocation_date());
		assertEquals(sd2, a.getReturn_date());
		
        
	}
	
	@Test
	public void testtostring() throws ParseException {
		String d1 ="2025-04-10";
		String d2 = "2025-04-10";
		SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date ud1 = sf.parse(d1);
		java.util.Date ud2 = sf.parse(d2);
		Date sd1 = new Date(ud1.getTime());
		Date sd2 = new Date(ud2.getTime());
		
		Asset_Allocations a = new Asset_Allocations(1,4,1,sd1,sd2);
		String res = "Asset_Allocations(allocation_id=1, asset_id=4, employee_id=1, allocation_date="+sd1+", return_date="+sd2+")";
		assertEquals(res, a.toString());
	}

}
