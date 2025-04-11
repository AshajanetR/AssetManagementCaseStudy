package com.java.assetmanagement.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


public class Assets {
     private int asset_id;
     private String name;
     private Type type;
     private String serial_number;
     private Date purchase_date;
     private String location;
     private Status status;
     private int owner_id;
	public int getAsset_id() {
		return asset_id;
	}
	public void setAsset_id(int asset_id) {
		this.asset_id = asset_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public Date getPurchase_date() {
		return purchase_date;
	}
	public void setPurchase_date(Date purchase_date) {
		this.purchase_date = purchase_date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public int getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}
	@Override
	public String toString() {
		return "Assets [asset_id=" + asset_id + ", name=" + name + ", type=" + type + ", serial_number=" + serial_number
				+ ", purchase_date=" + purchase_date + ", location=" + location + ", status=" + status + ", owner_id="
				+ owner_id + "]";
	}
	public Assets() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Assets(int asset_id, String name, Type type, String serial_number, Date purchase_date, String location,
			Status status, int owner_id) {
		this.asset_id = asset_id;
		this.name = name;
		this.type = type;
		this.serial_number = serial_number;
		this.purchase_date = purchase_date;
		this.location = location;
		this.status = status;
		this.owner_id = owner_id;
	}
     
     
}
