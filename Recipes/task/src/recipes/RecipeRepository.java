package recipes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findRecipeById(String id);
    List<Recipe> findRecipesByNameIgnoreCaseContainsOrderByDateDesc(String name);
    List<Recipe> findRecipesByCategoryIgnoreCaseLikeOrderByDateDesc(String category);
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String category);
}
