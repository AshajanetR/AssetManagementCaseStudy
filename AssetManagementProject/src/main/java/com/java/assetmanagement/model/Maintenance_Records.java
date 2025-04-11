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

public class Maintenance_Records {
      private int maintenance_id;
      private int asset_id;
      Date maintenance_date;
      String description;
      private double cost;
}
