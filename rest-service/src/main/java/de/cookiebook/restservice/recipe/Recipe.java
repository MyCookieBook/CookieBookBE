package de.cookiebook.restservice.recipe;

import de.cookiebook.restservice.category.Category;
import de.cookiebook.restservice.category.Subcategory;
import de.cookiebook.restservice.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "recipes3")
public class Recipe {
/* To do: Objekte implementieren
 * - category objekt (Kategorien + Unterkategorien sind auf google drive)
 */
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Column(nullable = true)
    private Integer duration;
    @Column(nullable = true)
    private Integer difficultyLevel;
    private String ingredients; 
    private String material;
    private String steps;
    private Category category;
    private Subcategory subcategory;
    private String link;
    @Column(nullable = true)
    private Integer calories;
    private String otherInformation;
    
    
    @ManyToMany(cascade = {CascadeType.MERGE })
    @JoinTable(
    		  name = "recipe_user", 
    		  joinColumns = @JoinColumn(name = "idRecipe", referencedColumnName = "id"), 
    		  inverseJoinColumns = @JoinColumn(name = "idUser", referencedColumnName = "id"))
    private List<User> bookmarks = new ArrayList<User>();

	public Recipe(	String title, 
    				Integer duration, 
    				Integer difficultyLevel,
    				String material, 
    				String steps, 
    				String link, 
    				Integer calories, 
    				String otherInformation,
    				String ingredients,
    				List<User> bookmarks,
    				Category category,
    				Subcategory subcategory) {
    	
        this.title = title;
        this.duration = duration;
        this.difficultyLevel = difficultyLevel;
        this.material = material;
        this.steps = steps;
        this.link = link;
        this.calories = calories;
        this.otherInformation = otherInformation;
        this.ingredients = ingredients;
        this.bookmarks = bookmarks;
        this.category = category;
        this.subcategory = subcategory;
    }
	
	public void addBookmark(User user) {
		this.bookmarks.add(user);
	}
	
	public void deleteBookmark(User user) {
		this.deleteBookmark(user);
	}

	@Override
    public String toString() {
    	
        return "Recipe{" +
                "id=" + this.id +
                ", title='" + this.title + '\'' +
                ", duration='" + this.duration + '\'' +
                ", difficultyLevel=" + this.difficultyLevel +
                ", steps='" + this.steps + '\'' +
                ", link='" + this.link + '\'' +
                ", otherInformation='" + this.otherInformation + '\'' +
                ", ingredients='" + this.ingredients + '\'' +
                '}';
    }

}

