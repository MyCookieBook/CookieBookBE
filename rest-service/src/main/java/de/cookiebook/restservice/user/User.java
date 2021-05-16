package de.cookiebook.restservice.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private @NotBlank String email;
    private @NotBlank String password;
    private @NotBlank boolean loggedIn;

    /* @AllArgsContructor macht keinen Sinn loggedIn per Konstruktor zu setzen,
     * weil das soll ja nicht von außen gesetzt werden können, sondern nur bei erfolgreicher Anmeldung.
     */

    /*
     * beim einloggen User-ID
     * Fehlermeldung übergeben, z.b. mit den Zahlenwerten zurückgeben "ID=40 Fehler aufgetreten"
     * 4x freilassen für die ID wegen der Fehlermeldung
     * Fehler abfangen
     *
     */

    public User(@NotBlank String email,
                @NotBlank String password) {
        this.email = email;
        this.password = password;
        this.loggedIn = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, 
                            loggedIn);
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", loggedIn=" + loggedIn +
                '}';
    }
}