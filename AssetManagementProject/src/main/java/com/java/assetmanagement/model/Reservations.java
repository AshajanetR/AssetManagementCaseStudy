package com.java.assetmanagement.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Reservations {
      private int reservation_id;
      private int asset_id;
      private int employee_id;
      private Date reservation_date;
      private Date start_date;
      private Date end_date;
      private ReservationStatus rstatus;
      
}
