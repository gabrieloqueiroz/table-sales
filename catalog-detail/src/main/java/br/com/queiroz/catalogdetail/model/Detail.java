package br.com.queiroz.catalogdetail.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Detail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "description")
  private String description;

  @NotNull
  @Column(name = "manual")
  private String manual;

  @NotNull
  @Column(name = "color")
  private String color;

  @Override
  public String toString() {
    return id + " " + description + " " + manual + " " + color;
  }
}
