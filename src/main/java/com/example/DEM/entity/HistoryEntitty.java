package com.example.DEM.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "history")
public class HistoryEntitty {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "history_id")
  private int historyId;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity userHistory;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "category")
  private CategoryEntity category;
  @Column(name = "note")
  private String note;
  @Column(name = "amount")
  private BigDecimal amount;
  @Column(name = "date")
  private Date date;
}
