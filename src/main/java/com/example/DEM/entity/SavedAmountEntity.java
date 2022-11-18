package com.example.DEM.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "seved_amount")
public class SavedAmountEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name= "id")
  private int id;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity userAmount;
  @Column(name= "save_amount")
  private BigDecimal savedAmount;

}
