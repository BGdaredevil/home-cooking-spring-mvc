package com.cooking.home_recipes.services;

import com.cooking.home_recipes.models.Recipe;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    @Nullable
    public List<Recipe> getLatestThree() {
        return null;
    }
}
