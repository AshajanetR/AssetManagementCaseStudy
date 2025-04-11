package com.java.assetmanagement.model;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

public class AssetsTest {

	@Test
	public void testconstructor() throws ParseException {
		Assets asset = new Assets();
		assertNotNull(asset);
		String d = "2025-01-07";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date ut = sf.parse(d);
		Date u = new Date(ut.getTime());
		
		Assets a = new Assets(4,"janet",Type.VEHICLE,"156",u,"cbe",Status.DECOMISSIONED,1);
		assertEquals(4, a.getAsset_id());
		assertEquals("janet", a.getName());
		assertEquals(Type.VEHICLE, a.getType());
		assertEquals("156", a.getSerial_number());
		assertEquals(u, a.getPurchase_date());
		assertEquals("cbe", a.getLocation());
		assertEquals(Status.DECOMISSIONED, a.getStatus());
		assertEquals(1,a.getOwner_id());
		
	}
	
	@Test
	public void testsettersandgetters() throws ParseException {
		String d = "2025-01-07";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date ut = sf.parse(d);
		Date u = new Date(ut.getTime());
		Assets a = new Assets();
		a.setAsset_id(4);
		a.setName("janet");
		a.setType(Type.VEHICLE);
		a.setSerial_number("156");
		a.setPurchase_date(u);
		a.setLocation("cbe");
		a.setStatus(Status.DECOMISSIONED);
		a.setOwner_id(1);
		
		assertEquals(4, a.getAsset_id());
		assertEquals("janet", a.getName());
		assertEquals(Type.VEHICLE, a.getType());
		assertEquals("156", a.getSerial_number());
		assertEquals(u, a.getPurchase_date());
		assertEquals("cbe", a.getLocation());
		assertEquals(Status.DECOMISSIONED, a.getStatus());
		assertEquals(1,a.getOwner_id());
		
	}
	
	@Test
	public void testToString() throws ParseException {
	    String d = "2025-01-07";
	    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date ut = sf.parse(d);
	    Date u = new Date(ut.getTime());

	    Assets a = new Assets(4, "janet", Type.VEHICLE, "156", u, "cbe", Status.DECOMISSIONED, 1);

	    String expected = "Assets [asset_id=4, name=janet, type=VEHICLE, serial_number=156, purchase_date=" 
	                      + u + ", location=cbe, status=DECOMISSIONED, owner_id=1]";

	    assertEquals(expected, a.toString());
	}

}
