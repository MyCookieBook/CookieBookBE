package de.cookiebook.restservice;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
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
    private Category category; // to do 
    private String link;
    @Column(nullable = true)
    private Integer calories;
    private String otherInformation;
    
    
    @ManyToMany(cascade = {CascadeType.MERGE })
    @JoinTable(
    		  name = "recipe_tag", 
    		  joinColumns = @JoinColumn(name = "idRecipe", referencedColumnName = "id"), 
    		  inverseJoinColumns = @JoinColumn(name = "idTag", referencedColumnName = "id"))
    private List<Tag> tags = new ArrayList<Tag>();
    
    
 /*
  * Der Text der Beziehung zwischen username und recipe wird im FE zusammengebaut. (Cheesecake by @RitterSchlagedrein)
  * Pflichtfelder werden im FE geprüft
  */

    public Recipe(){};

    public void setTitle(String title) {
		this.title = title;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Recipe(	String title, 
    				Integer duration, 
    				Integer difficultyLevel,
    				String material, 
    				String steps, 
    				String link, 
    				Integer calories, 
    				String otherInformation,
    				String ingredients,
    				List<Tag> tags,
    				Category category) {
    	
        this.title = title;
        this.duration = duration;
        this.difficultyLevel = difficultyLevel;
        this.material = material;
        this.steps = steps;
        this.link = link;
        this.calories = calories;
        this.otherInformation = otherInformation;
        this.ingredients = ingredients;
        this.tags = tags;
        this.category = category;
    }

    public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getDuration() {
		return duration;
	}

	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public String getIngredients() {
		return ingredients;
	}

	public String getMaterial() {
		return material;
	}

	public String getSteps() {
		return steps;
	}

	public Category getCategory() {
		return category;
	}

	public String getLink() {
		return link;
	}

	public int getCalories() {
		return calories;
	}

	public String getOtherInformation() {
		return otherInformation;
	}

	public List<Tag> getTags() {
		return tags;
	}

	@Override
    public String toString() {
    	String tagString = ", tags= [";
    	if(tags != null) {
    	for(int i = 0; i < tags.size(); i++) {
    		if(i > 0) {
    			tagString+=",";
    		}
    		tagString += "{id='" + tags.get(i).getId() + "',title='" + tags.get(i).getTitle() + "'}";
    		
    	}}
    	tagString += "]";
        return "Recipe{" +
                "id=" + this.id +
                ", title='" + this.title + '\'' +
                ", duration='" + this.duration + '\'' +
                ", difficultyLevel=" + this.difficultyLevel +
                ", steps='" + this.steps + '\'' +
                ", link='" + this.link + '\'' +
                ", otherInformation='" + this.otherInformation + '\'' +
                ", ingredients='" + this.ingredients + '\'' +
                tagString +
                '}';
    }

	public void setDifficultyLevel(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public void setOtherInformation(String otherInformation) {
		this.otherInformation = otherInformation;
	}

}

