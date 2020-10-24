package com.curd.demo.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MakananDto implements Serializable{
	private Integer primaryKey;
	private String name;
	private String jenisMakanan;
	private Integer harga;
	private Integer stock;
	private List<VoucherExcel> voucherExcel;

}
