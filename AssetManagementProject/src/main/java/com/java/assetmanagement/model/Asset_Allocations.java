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

public class Asset_Allocations {
       private int allocation_id;
       private int asset_id;
       private int employee_id;
       private Date allocation_date;
       private Date return_date;
}
