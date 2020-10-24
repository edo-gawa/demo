package com.curd.demo.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ExampleResponse implements Serializable{

	private List<MakananDto> listMakanan;
}
