package de.cookiebook.restservice.steps;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "steps1")
public class Step {
    @Id
    @GeneratedValue
    private Long id;
    private String stepName;

    public Step(String stepName) {
        this.stepName = stepName;
    }
}