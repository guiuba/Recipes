package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private final RecipeRepository recipeRepository;

    //  @Autowired
    public RecipeService(RecipeRepository recipeRepository) {

        this.recipeRepository = recipeRepository;
    }

    public Optional<Recipe> findRecipeById(String id) {
        if (recipeRepository.findById(Long.parseLong(id)).isPresent()) {
            return Optional.of(recipeRepository.findById(Long.parseLong(id)).get());
        } else {
            return Optional.empty();
        }
    }

    public void saveRecipe(Recipe recipe) {

        recipeRepository.save(recipe);
    }

    public void delete(String id) {

        recipeRepository.deleteById(Long.valueOf(id));
    }

    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public void updateRecipe(String id, Recipe newRecipe) {
        Optional<Recipe> recipe = findRecipeById(id);
        if (recipe.isPresent()) {
            recipe.get().setName(newRecipe.getName());
            recipe.get().setCategory(newRecipe.getCategory());
            recipe.get().setDate(LocalDateTime.now());
            recipe.get().setDescription(newRecipe.getDescription());
            recipe.get().setIngredients(newRecipe.getIngredients());
            recipe.get().setDirections(newRecipe.getDirections());
            saveRecipe(recipe.get());
        }

    }

    public Long saveOrUpdate(Recipe recipeToSave) {
        Recipe savedRecipe = recipeRepository.save(recipeToSave);
        return savedRecipe.getId();
        //  return repository.save(recipe);
    }



    public Optional<Recipe> getRecipeById(String id) {

        return recipeRepository.findById(Long.valueOf(id));
    }



    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeAll = new ArrayList<>();
        recipeRepository.findAll().forEach(recipeAll::add);
        return recipeAll;
    }








    /*    List<Object> getRecipeByName(String name) {
        try {
            List<Recipe> list = recipeRepository.findRecipesByNameIgnoreCaseContainsOrderByDateDesc(name);
            return Recipe.listToReturn(list);
        } catch (Exception e) {
            return List.of();
        }
    }*/

/*    List<Object> getRecipeByCategory(String category) {
        try {
            List<Recipe> list = recipeRepository.findRecipesByCategoryIgnoreCaseLikeOrderByDateDesc(category);
            return Recipe.listToReturn(list);
        } catch (Exception e) {
            return List.of();
        }
    }*/


}
