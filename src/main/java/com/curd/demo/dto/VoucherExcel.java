package com.curd.demo.dto;

import java.io.Serializable;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;

import lombok.Data;

@Data
public class VoucherExcel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ExcelRow                  
    private int rowIndex;

    @ExcelCell(0)                
    private String kodeVoucher;

    @ExcelCell(1)
    private String noPonta;
}
