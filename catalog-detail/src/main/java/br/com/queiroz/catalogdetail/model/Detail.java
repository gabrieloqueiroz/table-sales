package br.com.queiroz.catalogdetail.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Detail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @NotNull
  @Column(name = "description")
  private String description;

  @NotNull
  @Column(name = "manual")
  private String manual;

  @NotNull
  @Column(name = "color")
  private String color;
}
