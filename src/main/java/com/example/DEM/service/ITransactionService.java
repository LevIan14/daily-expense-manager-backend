package com.example.DEM.service;

import com.example.DEM.entity.Transaction;
import com.example.DEM.model.AddTransactionRequest;
import com.example.DEM.model.AddTransactionResponse;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ITransactionService {
  List<Transaction> getListHistory();
  List<Transaction> getListHistoryByYear(String year);
  AddTransactionResponse getDetailHistory(int id);
  AddTransactionResponse updateTransaction (int id,AddTransactionRequest request ) throws ParseException;
  boolean deleteTransaction (int id);
  AddTransactionResponse addTransaction (AddTransactionRequest request) throws ParseException;


}
