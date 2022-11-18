package com.example.DEM.model;

import com.example.DEM.entity.CategoryEntity;
import com.example.DEM.entity.UserEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddTransactionResponse {
  private int history_id;
  private int userHistory;
  private String categoryGroup;
  private String category;
  private String note;
  private BigDecimal amount;
  private Date date;
  private BigDecimal savedAmount;

}
