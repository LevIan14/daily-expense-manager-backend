package com.example.DEM.model;

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
  private int transactionId;
  private int userHistory;
  private int categoryGroupId;
  private String categoryGroup;
  private int categoryId;
  private String category;
  private String note;
  private BigDecimal amount;
  private Date date;
  private BigDecimal savedAmount;

}
