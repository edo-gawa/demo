package com.curd.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.curd.demo.dto.MakananDto;
import com.curd.demo.dto.ResponseMakanan;
import com.curd.demo.service.MakananService;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/makanan")
public class CtrlMakananApi {

	@Autowired
	private MakananService service;

	@PostMapping(value = "/insert")
	public String insertMakanan(@RequestBody MakananDto body) {
		return service.saveMakanan(body) ? "Berhasil Insert Makanan" : "GAGAL Insert";
	}

	@PostMapping(value = "/update")
	public String updateMakanan(@RequestBody MakananDto body) {
		return service.updateMakanan(body) ? "Berhasil update Makanan" : "GAGAL Update";
	}

	@GetMapping(value = "/list-makanan")
	public List<MakananDto> getAll() {
		return service.getAllMakanan();
	}

	@PostMapping(value = "/find-by-id")
	public MakananDto findById(@RequestBody MakananDto body) {
		return service.getById(body.getPrimaryKey());
	}

	@PostMapping(value = "/read-excel")
	public void readExcel(@RequestParam("file") MultipartFile file) {
		service.readExcel(file);
	}

	@PostMapping(value = "/list-makanan")
	public ResponseEntity<?> postAndGet(@RequestBody List<String> example) {
		return new ResponseEntity<ResponseMakanan>(service.postAllMakanan(example), HttpStatus.OK);
	}

	@GetMapping(value = "/test")
	public List<MakananDto> test() {
		return service.test();
	}

}
