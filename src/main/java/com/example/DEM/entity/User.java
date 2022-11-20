package com.example.DEM.entity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private int id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private String role;

  @OneToMany(mappedBy = "userAmount")
  private List<SavedAmountEntity> savedAmount;

  @OneToMany(mappedBy = "userHistory")
  private List<Transaction> transactions;

  @OneToMany(mappedBy = "userCategory")
  private List<Category> category;
}
