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
@Table(name = "category_group")
public class CategoryGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_group_id")
  private int categoryGroupId;

  @Column(name = "category_group_name")
  private String categoryGroupName;

  @OneToMany(mappedBy = "categoryGroup")
  private List<Category> category;
}
