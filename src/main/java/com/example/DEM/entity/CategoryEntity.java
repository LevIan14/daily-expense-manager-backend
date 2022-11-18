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
public class CategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private int categoryId;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "category_group_id")
  private CategoryGroupEntity categoryGroup;
  @Column(name = "category_name")
  private String categoryName;
  @OneToMany(mappedBy = "category")
  private List<HistoryEntitty> category;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity userCategory;
}
