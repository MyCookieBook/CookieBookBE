package de.cookiebook.restservice.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import de.cookiebook.restservice.recipe.Recipe;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private @NotBlank String email;
    private @NotBlank String password;
    private @NotBlank boolean loggedIn;
    
	@ManyToMany
    private List<Recipe> bookmarkRecipes = new ArrayList<Recipe>();
    private Date durration;

    /*
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
    
	public void addRecipe(Recipe recipe) {
		bookmarkRecipes.add(recipe);
			//recipe.addBookmark(this);
		}
	public void deleteBookmark(Recipe recipe) {
		bookmarkRecipes.remove(recipe);
		//recipe.deleteBookmark(this);
	}

	public void setPassword(String newPassword) {
		this.password = newPassword;
		
	}

	public void setLoggedIn(boolean b) {
		this.loggedIn = b;
	}

}