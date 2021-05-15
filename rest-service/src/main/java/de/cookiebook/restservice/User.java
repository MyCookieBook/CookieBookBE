package de.cookiebook.restservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
@Entity
@Table(name = "users")
public class User {
    private @Id @GeneratedValue long id;
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
    
    public User() {
    }
    public User(@NotBlank String email, 
                @NotBlank String password) {
        this.email = email;
        this.password = password;
        this.loggedIn = false;
    }
    public long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isLoggedIn() {
        return loggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
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