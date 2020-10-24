package com.curd.demo.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.curd.demo.dto.ExampleResponse;
import com.curd.demo.dto.MakananDto;
import com.curd.demo.dto.ResponseMakanan;
import com.curd.demo.dto.VoucherExcel;
import com.curd.demo.entity.DemoPenjualanEntity;
import com.curd.demo.repository.MakananRepository;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;

@Service
public class MakananService {

	@Autowired
	private MakananRepository makananRepo;

	public Boolean saveMakanan(MakananDto makanDto) {

		try {
			// logic olah data
			DemoPenjualanEntity makanan = new DemoPenjualanEntity();
			makanan.setJenisMakanan(makanDto.getJenisMakanan());
			if (makanDto.getHarga() < 6000) {
				makanan.setHarga(10000);
			} else {
				makanan.setHarga(makanDto.getHarga());
			}
			makanan.setStock(makanDto.getStock());
			makanan.setNamaMakanan(makanDto.getName());
			// end logic
			makananRepo.save(makanan);// insert to database
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public List<MakananDto> getAllMakanan() {
		// TODO Auto-generated method stub
		List<MakananDto> listMakanan = new ArrayList<MakananDto>();
		makananRepo.findAll().stream().forEach(makanan -> {
			MakananDto makananDto = new MakananDto();
			makananDto.setHarga(makanan.getHarga());
			makananDto.setName(makanan.getNamaMakanan());
			makananDto.setJenisMakanan(makanan.getJenisMakanan());
			makananDto.setStock(makanan.getStock());
			makananDto.setPrimaryKey(makanan.getMakananId());
			listMakanan.add(makananDto);
		});
		return listMakanan;
	}

	public MakananDto getById(Integer makananId) {
		DemoPenjualanEntity makanan = makananRepo.findByMakananId(makananId);
		MakananDto makananDto = new MakananDto();
		makananDto.setHarga(makanan.getHarga());
		makananDto.setName(makanan.getNamaMakanan());
		makananDto.setJenisMakanan(makanan.getJenisMakanan());
		makananDto.setStock(makanan.getStock());
		makananDto.setPrimaryKey(makanan.getMakananId());
		return makananDto;
	}

	public boolean updateMakanan(MakananDto body) {
		try {
			// logic olah data
			DemoPenjualanEntity makanan = makananRepo.findByMakananId(body.getPrimaryKey());
			if (makanan != null) {
				makanan.setJenisMakanan(body.getJenisMakanan());
				if (body.getHarga() < 6000) {
					makanan.setHarga(10000);
				} else {
					makanan.setHarga(body.getHarga());
				}
				makanan.setStock(body.getStock());
				makanan.setNamaMakanan(body.getName());
				// end logic
				makananRepo.save(makanan);// update to database
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public void readExcel(MultipartFile excel) {
		PoijiOptions options = PoijiOptionsBuilder.settings(0).preferNullOverDefault(true).build();
		Instant start = Instant.now();
		try {
			MakananDto makananDto = new MakananDto();
			makananDto.setVoucherExcel(
					Poiji.fromExcel(excel.getInputStream(), PoijiExcelType.XLSX, VoucherExcel.class, options));
			// System.out.println("end======================================================");
			Instant finish = Instant.now();
			long timeElapsed = Duration.between(start, finish).toMillis();
			makananDto.getVoucherExcel().stream().forEach(v->{
				System.out.println(v.getNoPonta());
			});
			System.out.println("waktu use = " + TimeUnit.MILLISECONDS.toSeconds(timeElapsed));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
			start = Instant.now();
			Workbook workbook = new XSSFWorkbook(excel.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);
			sheet.rowIterator().forEachRemaining(row -> {
				row.getCell(0);
				if (row.getCell(1) == null) {

				}
			});
			System.out.println("end======================================================");
			Instant finish = Instant.now();
			long timeElapsed = Duration.between(start, finish).toMillis();
			System.out.println("waktu non = " + TimeUnit.MILLISECONDS.toSeconds(timeElapsed));
			workbook.close();
		} catch (Exception e) {
			// TODO: handle exception
		}*/
	}

	public ResponseMakanan postAllMakanan(List<String> example) {
		// TODO Auto-generated method stub
		example.stream().forEach(data -> {
			System.out.println("data : " + data);
		});
		ResponseMakanan response = new ResponseMakanan();
		List<MakananDto> listMakanan = new ArrayList<MakananDto>();
		for (AtomicInteger increment = new AtomicInteger(1); increment.get() <= 100000; increment.incrementAndGet()) {
			makananRepo.findAll().stream().forEach(makanan -> {
				MakananDto makananDto = new MakananDto();
				makananDto.setHarga(makanan.getHarga());
				makananDto.setName(makanan.getNamaMakanan());
				makananDto.setJenisMakanan(makanan.getJenisMakanan());
				makananDto.setStock(makanan.getStock());
				makananDto.setPrimaryKey(increment.get());
				listMakanan.add(makananDto);
			});
		}
		response.setListMakanan(listMakanan);
		return response;
	}

	public List<MakananDto> test() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String resourceUrl = "http://localhost:9093/makanan/list-makanan";
		List<String> makanan = new ArrayList<>();
		makanan.add("satu");
		makanan.add("dua");
		ResponseEntity<ResponseMakanan> listMember = restTemplate.postForEntity(resourceUrl, makanan,
				ResponseMakanan.class);
		return listMember.getBody().getListMakanan();
	}

}
