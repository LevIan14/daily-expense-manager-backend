package com.example.DEM.controller;

import com.example.DEM.entity.Transaction;
import com.example.DEM.model.AddTransactionRequest;
import com.example.DEM.model.AddTransactionResponse;
import com.example.DEM.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/transaction")
public class TransactionController {
  @Autowired
  private ITransactionService iTransactionService;

  @GetMapping("/list")
  public List<AddTransactionResponse> getListHistory(){
    return iTransactionService.getListHistory();
  }

  @GetMapping("/list/{year}")
  public List<AddTransactionResponse> getListHistoryByYear(@PathVariable ("year") String year){
    return iTransactionService.getListHistoryByYear(year);
  }

  @GetMapping("/detail/{id}")
  public AddTransactionResponse getDetailHistory(@PathVariable ("id") int id){
    return iTransactionService.getDetailHistory(id);
  }

  @PutMapping("/update/{id}")
  public AddTransactionResponse getUpdateHistory(@PathVariable ("id") int id, @RequestBody AddTransactionRequest request ) throws ParseException {
    return iTransactionService.updateTransaction(id,request);
  }

  @DeleteMapping("/delete/{id}")
  public boolean getDeleteHistory(@PathVariable ("id") int id){
return iTransactionService.deleteTransaction(id);
  }

  @PostMapping("/add")
  public AddTransactionResponse addTransaction(@RequestBody AddTransactionRequest request) throws ParseException {
    return iTransactionService.addTransaction(request);
  }
}
