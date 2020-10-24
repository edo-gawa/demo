package com.curd.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table
@Entity(name = "demo_penjualan")
public class DemoPenjualanEntity implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "makanan_id")
	private Integer makananId;

	@Column(name = "nama_manakan")
	private String namaMakanan;

	@Column(name = "jenis_makanan")
	private String jenisMakanan;

	@Column(name = "harga")
	private Integer harga;
	
	@Column(name = "stock")
	private Integer stock;
}
