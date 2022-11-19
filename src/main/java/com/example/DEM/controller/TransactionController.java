package com.example.DEM.controller;

import com.example.DEM.entity.Transaction;
import com.example.DEM.model.AddTransactionRequest;
import com.example.DEM.model.AddTransactionResponse;
import com.example.DEM.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/transaction")
public class TransactionController {
  @Autowired
  private ITransactionService iTransactionService;

  @GetMapping("/list")
  public List<Transaction> getListHistory(){
    return iTransactionService.getListHistory();
  }

  @GetMapping("/list/{year}")
  public List<Transaction> getListHistoryByYear(@PathVariable ("year") Date year){
    return iTransactionService.getListHistoryByYear(year);
  }

  @GetMapping("/detail/{id}")
  public Transaction getDetailHistory(@PathVariable ("id") int id){
    return iTransactionService.getDetailHistory(id);
  }

  @PutMapping("/update/{id}")
  public AddTransactionResponse getUpdateHistory(@PathVariable ("id") int id, @RequestBody AddTransactionRequest request ){
    return iTransactionService.updateTransaction(id,request);
  }

  @PutMapping("/delete/{id}")
  public boolean getDeleteHistory(@PathVariable ("id") int id){
return iTransactionService.deleteTransaction(id);
  }

  @PostMapping("/add")
  public AddTransactionResponse addTransaction(@RequestBody AddTransactionRequest request) {
    return iTransactionService.addTransaction(request);
  }
}
