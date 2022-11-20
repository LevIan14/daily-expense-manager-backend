package com.example.DEM.repository;

import com.example.DEM.entity.Category;
import com.example.DEM.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
  List<Transaction> findByUserHistory_Username (String Username);
  Transaction findByTransactionId (int id);

  void deleteByTransactionId(int id);
  Transaction findByCategoryAndUserHistory_Username(Category category, String username);
  @Query(value = "select * from transaction where year(date) like ?1", nativeQuery = true)
  List<Transaction> findAllByDateContainingAndUserHistory_Username(String year, String user);
}
