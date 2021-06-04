package de.cookiebook.restservice.materials;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "materials1")
public class Material {
    @Id
    @GeneratedValue
    private Long id;
    private String materialName;

    public Material(String materialName) {
        this.materialName = materialName;
    }
}
