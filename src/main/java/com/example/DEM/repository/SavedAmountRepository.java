package com.example.DEM.repository;

import com.example.DEM.entity.SavedAmountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedAmountRepository extends JpaRepository<SavedAmountEntity,Integer> {
  SavedAmountEntity findByUserAmount_Username (String User);
}
