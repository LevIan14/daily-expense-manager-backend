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
public class UserEntity {
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
  private List<HistoryEntitty> history;
  @OneToMany(mappedBy = "userCategory")
  private List<CategoryEntity> category;

}
