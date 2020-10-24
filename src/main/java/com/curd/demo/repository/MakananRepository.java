package com.curd.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curd.demo.entity.DemoPenjualanEntity;

public interface MakananRepository extends JpaRepository<DemoPenjualanEntity,Integer > {
	List<DemoPenjualanEntity> findByNamaMakananContaining(String namaMakanan);

	
	DemoPenjualanEntity findByMakananId(Integer id);
}
