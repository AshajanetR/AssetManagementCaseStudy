package com.java.assetmanagement.model;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

public class ReservationsTest {

	@Test
	public void testconstructor() throws ParseException {
		Reservations r = new Reservations();
		assertNotNull(r);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String d1 ="2025-01-01";
		java.util.Date ud1 = sf.parse(d1);
		Date sd1 = new Date(ud1.getTime());
		String s1 ="2025-01-01";
		java.util.Date ud2 = sf.parse(s1);
		Date sd2 = new Date(ud2.getTime());
		String e1 ="2025-01-01";
		java.util.Date ed1 = sf.parse(e1);
		Date sd3 = new Date(ed1.getTime());
		Reservations r1 = new Reservations(1,1,1,sd1,sd2,sd3,ReservationStatus.PENDING);
		assertEquals(1, r1.getReservation_id());
		assertEquals(1, r1.getAsset_id());
		assertEquals(1, r1.getEmployee_id());
		assertEquals(sd1, r1.getReservation_date());
		assertEquals(sd2, r1.getStart_date());
		assertEquals(sd3, r1.getEnd_date());
		assertEquals(ReservationStatus.PENDING, r1.getRstatus());
		
	}
	
	@Test
	public void testgettersandsetters() throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String d1 ="2025-01-01";
		java.util.Date ud1 = sf.parse(d1);
		Date sd1 = new Date(ud1.getTime());
		String s1 ="2025-01-01";
		java.util.Date ud2 = sf.parse(s1);
		Date sd2 = new Date(ud2.getTime());
		String e1 ="2025-01-01";
		java.util.Date ed1 = sf.parse(e1);
		Date sd3 = new Date(ed1.getTime());
		Reservations r = new Reservations();
		r.setReservation_id(1);
		r.setAsset_id(1);
		r.setEmployee_id(1);
		r.setReservation_date(sd1);
		r.setStart_date(sd2);
		r.setEnd_date(sd3);
		r.setRstatus(ReservationStatus.PENDING);
		
		assertEquals(1, r.getReservation_id());
		assertEquals(1, r.getAsset_id());
		assertEquals(1, r.getEmployee_id());
		assertEquals(sd1, r.getReservation_date());
		assertEquals(sd2, r.getStart_date());
		assertEquals(sd3, r.getEnd_date());
		assertEquals(ReservationStatus.PENDING, r.getRstatus());
	}
	
	@Test
	public void testtostring() throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String d1 ="2025-01-01";
		java.util.Date ud1 = sf.parse(d1);
		Date sd1 = new Date(ud1.getTime());
		String s1 ="2025-01-01";
		java.util.Date ud2 = sf.parse(s1);
		Date sd2 = new Date(ud2.getTime());
		String e1 ="2025-01-01";
		java.util.Date ed1 = sf.parse(e1);
		Date sd3 = new Date(ed1.getTime());
		Reservations r1 = new Reservations(1,1,1,sd1,sd2,sd3,ReservationStatus.PENDING);
		String res = "Reservations(reservation_id=1, asset_id=1, employee_id=1, reservation_date=2025-01-01, start_date=2025-01-01, end_date=2025-01-01, rstatus=PENDING)";
		assertEquals(res, r1.toString());
	}

}
