package com.example.DEM.service;

import com.example.DEM.entity.HistoryEntitty;
import com.example.DEM.model.AddTransactionRequest;
import com.example.DEM.model.AddTransactionResponse;

import java.util.Date;
import java.util.List;

public interface ITransactionService {
  List<HistoryEntitty> getListHistory();
  List<HistoryEntitty> getListHistoryByYear(Date year);
  HistoryEntitty getDetailHistory(int id);
  AddTransactionResponse updateTransaction (int id,AddTransactionRequest request );
  boolean deleteTransaction (int id);
  AddTransactionResponse addTransaction (AddTransactionRequest request);


}
