package com.example.DEM.service;

import com.example.DEM.entity.Category;
import com.example.DEM.entity.SavedAmountEntity;
import com.example.DEM.entity.Transaction;
import com.example.DEM.entity.User;
import com.example.DEM.model.AddTransactionRequest;
import com.example.DEM.model.AddTransactionResponse;
import com.example.DEM.repository.CategoryRepository;
import com.example.DEM.repository.SavedAmountRepository;
import com.example.DEM.repository.TransactionRepository;
import com.example.DEM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class TransactionService implements ITransactionService {
  @Autowired
  TransactionRepository transactionRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  SavedAmountRepository savedAmountRepository;
  @Autowired
  CategoryRepository categoryRepository;
  @Override
  public List<AddTransactionResponse> getListHistory() {
    String username = getUser();
    List<AddTransactionResponse> responses= new ArrayList<>();
    List<Transaction> history = transactionRepository.findByUserHistory_UsernameOrderByDateDesc(username);
    history.forEach(a-> responses.add( AddTransactionResponse.builder()
        .transactionId(a.getTransactionId())
        .userHistory(a.getUserHistory().getId())
        .categoryGroupId(a.getCategory().getCategoryGroup().getCategoryGroupId())
        .categoryGroup(a.getCategory().getCategoryGroup().getCategoryGroupName())
        .category(a.getCategory().getCategoryName())
        .categoryId(a.getCategory().getCategoryId())
        .note(a.getNote())
        .amount(a.getAmount())
        .date(a.getDate())
        .build()));
    return responses;
  }

  @Override
  public List<AddTransactionResponse> getListHistoryByYear(String year) {
    String username = getUser();
    List<AddTransactionResponse> responses= new ArrayList<>();
    List<Transaction> history = transactionRepository.findAllByDateContainingAndUserHistory_Username(year,username);
    history.forEach(a-> responses.add( AddTransactionResponse.builder()
        .transactionId(a.getTransactionId())
        .userHistory(a.getUserHistory().getId())
        .categoryGroupId(a.getCategory().getCategoryGroup().getCategoryGroupId())
        .categoryGroup(a.getCategory().getCategoryGroup().getCategoryGroupName())
        .category(a.getCategory().getCategoryName())
        .categoryId(a.getCategory().getCategoryId())
        .note(a.getNote())
        .amount(a.getAmount())
        .date(a.getDate())
        .build()));
    return responses;
  }

  @Override
  public AddTransactionResponse getDetailHistory(int id) {
    Transaction history = transactionRepository.findByTransactionId(id);
    return AddTransactionResponse.builder()
        .transactionId(history.getTransactionId())
        .userHistory(history.getUserHistory().getId())
        .categoryGroupId(history.getCategory().getCategoryGroup().getCategoryGroupId())
        .categoryGroup(history.getCategory().getCategoryGroup().getCategoryGroupName())
        .category(history.getCategory().getCategoryName())
        .categoryId(history.getCategory().getCategoryId())
        .note(history.getNote())
        .amount(history.getAmount())
        .date(history.getDate())
        .build();
  }

  @Override
  public AddTransactionResponse updateTransaction(int id, AddTransactionRequest request) throws ParseException {
    String username = getUser();
    User user = userRepository.findByUsername(username);
    SavedAmountEntity savedAmount = savedAmountRepository.findByUserAmount_Username(username);
    Category category = categoryRepository.findByCategoryId(request.getCategoryId());
    Transaction history = transactionRepository.findByTransactionId(id);

    if (history.getCategory().getCategoryGroup().getCategoryGroupName().equals("EXPENSE")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().add(history.getAmount()));
    } else if (history.getCategory().getCategoryGroup().getCategoryGroupName().equals("INCOME")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().subtract(history.getAmount()));
    }

    if (category.getCategoryGroup().getCategoryGroupName().equals("EXPENSE")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().subtract(request.getAmount()));
    } else if (category.getCategoryGroup().getCategoryGroupName().equals("INCOME")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().add(request.getAmount()));
    }

    history.setCategory(category);
    history.setAmount(request.getAmount());
    history.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getDate()));
    history.setNote(request.getNote());
    history.setUserHistory(user);
    transactionRepository.save(history);

    return AddTransactionResponse.builder()
        .transactionId(history.getTransactionId())
        .userHistory(history.getUserHistory().getId())
        .categoryGroupId(history.getCategory().getCategoryGroup().getCategoryGroupId())
        .categoryGroup(history.getCategory().getCategoryGroup().getCategoryGroupName())
        .category(history.getCategory().getCategoryName())
        .categoryId(history.getCategory().getCategoryId())
        .note(history.getNote())
        .amount(history.getAmount())
        .date(history.getDate())
        .savedAmount(savedAmount.getSavedAmount())
        .build();
  }

  @Override
  public boolean deleteTransaction(int id) {
    Transaction history= transactionRepository.findByTransactionId(id);
    SavedAmountEntity savedAmount= savedAmountRepository.findByUserAmount_Username(history.getUserHistory().getUsername());
    if (history.getCategory().getCategoryGroup().getCategoryGroupName().equals("EXPENSE")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().add(history.getAmount()));
    }
    else if (history.getCategory().getCategoryGroup().getCategoryGroupName().equals("INCOME")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().subtract(history.getAmount()));
    }
    transactionRepository.deleteById(id);
//    transactionRepository.deleteByTransactionId(id);
    return true;
  }

  @Override
  public AddTransactionResponse addTransaction(AddTransactionRequest request) throws ParseException {
    String username = getUser();
    User user = userRepository.findByUsername(username);
    SavedAmountEntity savedAmount = savedAmountRepository.findByUserAmount_Username(username);
    Category category = categoryRepository.findByCategoryId(request.getCategoryId());
    if (category.getCategoryGroup().getCategoryGroupName().equals("EXPENSE")) {
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().subtract(request.getAmount()));
    } else if (category.getCategoryGroup().getCategoryGroupName().equals("INCOME")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().add(request.getAmount()));
    }

    Transaction transaction = new Transaction();
    transaction.setCategory(category);
    transaction.setAmount(request.getAmount());
    transaction.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getDate()));
    transaction.setNote(request.getNote());
    transaction.setUserHistory(user);
    transactionRepository.save(transaction);

    return AddTransactionResponse.builder()
        .transactionId(transaction.getTransactionId())
        .userHistory(transaction.getUserHistory().getId())
        .categoryGroupId(transaction.getCategory().getCategoryGroup().getCategoryGroupId())
        .categoryGroup(transaction.getCategory().getCategoryGroup().getCategoryGroupName())
        .category(transaction.getCategory().getCategoryName())
        .categoryId(transaction.getCategory().getCategoryId())
        .note(transaction.getNote())
        .amount(transaction.getAmount())
        .date(transaction.getDate())
        .savedAmount(savedAmount.getSavedAmount())
        .build();
  }

  public String getUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      return authentication.getName();
    }
    return null;
  }
}
