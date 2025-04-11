package com.java.assetmanagement.model;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

public class Maintenance_RecordsTest {

	@Test
	public void test() throws ParseException {
		Maintenance_Records m1 = new Maintenance_Records();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String d1 ="2025-01-01";
		java.util.Date ud1 = sf.parse(d1);
		Date sd1 = new Date(ud1.getTime());
		assertNotNull(m1);
		Maintenance_Records m = new Maintenance_Records(1,4,sd1,"camera issue",2000.00);
        assertEquals(1,m.getMaintenance_id());
        assertEquals(4, m.getAsset_id());
        assertEquals(sd1, m.getMaintenance_date());
        assertEquals("camera issue", m.getDescription());
        assertEquals(2000.00, m.getCost(),2);
	}
	
	@Test
	public void testgettersandsetters() throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String d1 ="2025-01-01";
		java.util.Date ud1 = sf.parse(d1);
		Date sd1 = new Date(ud1.getTime());
		
		Maintenance_Records m = new Maintenance_Records();
		m.setMaintenance_id(1);
		m.setAsset_id(4);
		m.setMaintenance_date(sd1);
		m.setDescription("camera issue");
		m.setCost(2000.00);
        assertEquals(1,m.getMaintenance_id());
        assertEquals(4, m.getAsset_id());
        assertEquals(sd1, m.getMaintenance_date());
        assertEquals("camera issue", m.getDescription());
        assertEquals(2000.00, m.getCost(),2);
	}
	
	@Test
	public void testtostring() throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String d1 ="2025-01-01";
		java.util.Date ud1 = sf.parse(d1);
		Date sd1 = new Date(ud1.getTime());
		Maintenance_Records m = new Maintenance_Records(1,4,sd1,"camera issue",2000.00);
		String res ="Maintenance_Records(maintenance_id=1, asset_id=4, maintenance_date=2025-01-01, description=camera issue, cost=2000.0)";
		assertEquals(res, m.toString());

	}

}
