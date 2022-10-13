package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@RequiredArgsConstructor
@Entity//(name = "Recipe")
//@Table//(name = "RECIPES")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   // @Column//(name = "id", nullable = false)
    @JsonIgnore
    private long id;

    @NotBlank
    @NonNull
    //@Column//(name = "name")
    private String name;

    @NotBlank
    @NonNull
   // @Column//(name = "category")
    private String category;


   // @Column//(name = "date")
    @NonNull
    private LocalDateTime date; // = LocalDateTime.now();

    @NotBlank
    @NonNull
 //   @Column//(name = "description")
    private String description;

   // @NotEmpty
    @NonNull
    @NotNull
    @Size(min = 1)
    @ElementCollection
//    @CollectionTable//(name = "INGREDIENTS", joinColumns = @JoinColumn(name = "id"))
  //  @Column//(name = "ingredients")
    private List<String> ingredients; // = new ArrayList<>();

   // @NotEmpty
    @NonNull
    @NotNull
    @Size(min = 1)
    @ElementCollection
  //  @CollectionTable//(name = "DIRECTIONS", joinColumns = @JoinColumn(name = "id"))
 //   @Column//(name = "directions")
    private List<String> directions; // = new ArrayList<>();

    @JsonIgnore
    private String email;




   /* @Column//(name = "author")
    private String author;*/



 /*   public Map<String, Object> objectToReturn() {
        Map<String, Object> recipeFormatted = new LinkedHashMap<String, Object>();
        recipeFormatted.put("name", this.getName());
        recipeFormatted.put("category", this.getCategory());
        recipeFormatted.put("date", this.getDate());
        recipeFormatted.put("description", this.getDescription());
        recipeFormatted.put("ingredients", this.getIngredients());
        recipeFormatted.put("directions", this.getDirections());
        return recipeFormatted;
    }

    public static List<Object> listToReturn(List<Recipe> list) {
        List<Object> listToReturn = new ArrayList<>();
        for (Recipe recipe : list) {
            listToReturn.add(recipe.objectToReturn());
        }
        return listToReturn;
    }*/

}
