package com.example.DEM.repository;

import com.example.DEM.entity.CategoryEntity;
import com.example.DEM.entity.HistoryEntitty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryEntitty,Integer> {
  List<HistoryEntitty> findByUserHistory_Username (String Username);
  HistoryEntitty findByHistoryId (int id);
  void deleteByHistoryId(int id);
  HistoryEntitty findByCategoryAndUserHistory_Username(CategoryEntity category, String username);
  List<HistoryEntitty> findAllByDateContainingAndUserHistory_Username(Date year, String user);
}
