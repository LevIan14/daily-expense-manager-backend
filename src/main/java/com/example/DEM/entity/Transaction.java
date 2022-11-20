package com.example.DEM.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_id")
  private int transactionId;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userHistory;
  
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(name = "note")
  private String note;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "date")
  private Date date;
}
