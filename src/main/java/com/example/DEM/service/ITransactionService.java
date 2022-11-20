package com.example.DEM.service;

import com.example.DEM.entity.Transaction;
import com.example.DEM.model.AddTransactionRequest;
import com.example.DEM.model.AddTransactionResponse;

import java.util.Date;
import java.util.List;

public interface ITransactionService {
  List<Transaction> getListHistory();
  List<Transaction> getListHistoryByYear(String year);
  Transaction getDetailHistory(int id);
  AddTransactionResponse updateTransaction (int id,AddTransactionRequest request );
  boolean deleteTransaction (int id);
  AddTransactionResponse addTransaction (AddTransactionRequest request);


}
