package com.example.DEM.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private int categoryId;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userCategory;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "category_group_id")
  private CategoryGroup categoryGroup;

  @Column(name = "category_name")
  private String categoryName;

  @OneToMany(mappedBy = "category")
  private List<Transaction> category;


}
