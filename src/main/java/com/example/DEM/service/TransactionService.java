package com.example.DEM.service;

import com.example.DEM.entity.CategoryEntity;
import com.example.DEM.entity.HistoryEntitty;
import com.example.DEM.entity.SavedAmountEntity;
import com.example.DEM.entity.UserEntity;
import com.example.DEM.model.AddTransactionRequest;
import com.example.DEM.model.AddTransactionResponse;
import com.example.DEM.repository.CategoryRepository;
import com.example.DEM.repository.HistoryRepository;
import com.example.DEM.repository.SavedAmountRepository;
import com.example.DEM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class TransactionService implements ITransactionService {
  @Autowired
  HistoryRepository historyRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  SavedAmountRepository savedAmountRepository;
  @Autowired
  CategoryRepository categoryRepository;
  @Override
  public List<HistoryEntitty> getListHistory() {
    String username=getUser();
    return historyRepository.findByUserHistory_Username(username);
  }

  @Override
  public List<HistoryEntitty> getListHistoryByYear(Date year) {
    String username=getUser();
    return historyRepository.findAllByDateContainingAndUserHistory_Username(year,username);
  }

  @Override
  public HistoryEntitty getDetailHistory(int id) {
    return historyRepository.findByHistoryId(id);
  }


  @Override
  public AddTransactionResponse updateTransaction(int id, AddTransactionRequest request) {
    String username=getUser();
    System.out.println(username);
    UserEntity user= userRepository.findByUsername(username);
    SavedAmountEntity savedAmount= savedAmountRepository.findByUserAmount_Username(username);
    CategoryEntity categoty = categoryRepository.findByCategoryName(request.getCategory());
    HistoryEntitty history = historyRepository.findByHistoryId(id);
    if (history.getCategory().getCategoryGroup().getCategoryGroupName().equals("EXPENSE")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().add(history.getAmount()));
    }else if (history.getCategory().getCategoryGroup().getCategoryGroupName().equals("INCOME")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().subtract(history.getAmount()));
    }
    if (categoty.getCategoryGroup().getCategoryGroupName().equals("EXPENSE")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().subtract(request.getAmount()));
    }
    else if (categoty.getCategoryGroup().getCategoryGroupName().equals("INCOME")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().add(request.getAmount()));
    }

    history.setCategory(categoty);
    history.setAmount(request.getAmount());
    history.setDate(request.getDate());
    history.setNote(request.getNote());
    history.setUserHistory(user);
    historyRepository.save(history);
    return AddTransactionResponse.builder()
        .amount(history.getAmount())
        .category(history.getCategory().getCategoryName())
        .date(history.getDate())
        .history_id(history.getHistoryId())
        .note(history.getNote())
        .savedAmount(savedAmount.getSavedAmount())
        .userHistory(history.getUserHistory().getId())
        .build();

  }

  @Override
  public boolean deleteTransaction(int id) {
    HistoryEntitty history= historyRepository.findByHistoryId(id);
    SavedAmountEntity savedAmount= savedAmountRepository.findByUserAmount_Username(history.getUserHistory().getUsername());
    if (history.getCategory().getCategoryGroup().getCategoryGroupName().equals("EXPENSE")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().add(history.getAmount()));
    }
    else if (history.getCategory().getCategoryGroup().getCategoryGroupName().equals("INCOME")){
      savedAmount.setSavedAmount(savedAmount.getSavedAmount().subtract(history.getAmount()));
    }
    historyRepository.deleteByHistoryId(id);
    return true;
  }

  @Override
  public AddTransactionResponse addTransaction(AddTransactionRequest request) {
    String username=getUser();
    System.out.println(username);
    UserEntity user= userRepository.findByUsername(username);
    SavedAmountEntity savedAmount= savedAmountRepository.findByUserAmount_Username(username);
    CategoryEntity categoty = categoryRepository.findByCategoryName(request.getCategory());
   if (categoty.getCategoryGroup().getCategoryGroupName().equals("EXPENSE")){
     savedAmount.setSavedAmount(savedAmount.getSavedAmount().subtract(request.getAmount()));
   }
   else if (categoty.getCategoryGroup().getCategoryGroupName().equals("INCOME")){
     savedAmount.setSavedAmount(savedAmount.getSavedAmount().add(request.getAmount()));
   }
      HistoryEntitty history = new HistoryEntitty();
      history.setCategory(categoty);
      history.setAmount(request.getAmount());
      history.setDate(request.getDate());
      history.setNote(request.getNote());
      history.setUserHistory(user);
      historyRepository.save(history);


    return AddTransactionResponse.builder()
        .amount(history.getAmount())
        .category(history.getCategory().getCategoryName())
        .date(history.getDate())
        .history_id(history.getHistoryId())
        .note(history.getNote())
        .savedAmount(savedAmount.getSavedAmount())
        .userHistory(history.getUserHistory().getId())
        .build();
  }
  public String getUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUserName = authentication.getName();
      return currentUserName;
    }
    return null;
  }
}
