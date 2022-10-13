package recipes;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@Validated
@RequestMapping("/api/recipe")
public class RecipeController {


    RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {

        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(HttpServletResponse response, @PathVariable String id) {
        response.addHeader("Content-type", "application/json");
        Optional<Recipe> recipe = recipeService.findRecipeById(id);
        if (recipe.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found.");
        return recipe.get();
    }

    @PostMapping("/new")
    public String addRecipeID(HttpServletResponse response, @Valid @RequestBody Recipe recipe) {
        response.addHeader("Content-type", "application/json");
        Recipe newRecipe = new Recipe(recipe.getName(), recipe.getCategory(), LocalDateTime.now(), recipe.getDescription(),
                recipe.getIngredients(), recipe.getDirections());
        newRecipe.setEmail(getLoggedInUser());
        recipeService.saveRecipe(newRecipe);
        return String.format("{ \"id\":%s }", newRecipe.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable String id) {
        Optional<Recipe> recipe = recipeService.findRecipeById(id);
        if (recipe.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found.");
        if (!recipe.get().getEmail().equals(getLoggedInUser()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your own recipes.");
        recipeService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable String id,
                             @Valid @RequestBody Recipe newRecipe) {
        Optional<Recipe> recipe = recipeService.findRecipeById(id);

        if (recipe.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found.");

        if (!recipeService.findRecipeById(id).get().getEmail().equals(getLoggedInUser()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your own recipes.");

        recipeService.updateRecipe(id, newRecipe);
    }

    @GetMapping("/search/")
    @ResponseBody
    public List<Recipe> searchRecipes(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "category", required = false) String category)
            throws ConstraintViolationException {
        Optional<String> nameCheck = Optional.ofNullable(name);
        Optional<String> categoryCheck = Optional.ofNullable(category);
        if ((nameCheck.isEmpty() && categoryCheck.isEmpty()) || (nameCheck.isPresent()) && categoryCheck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); // 400 (No Content) status code
        } else if (nameCheck.isPresent()) {
            return recipeService.findByName(name);
        } else if (categoryCheck.isPresent()) {
            return recipeService.findByCategory(category);
        } else {
            return List.of();
        }
    }


    public String getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal().toString();
    }

}
