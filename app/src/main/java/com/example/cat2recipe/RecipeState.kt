package com.example.cat2recipe

data class RecipeState(
    val recipe: List<Recipe> = emptyList(),
    val Name: String ="",
    val Food_type: String ="",
    val Difficulty: String ="",
    val Ingredients: String ="",
    val isAddingRecipe: Boolean = false,
    val sortType: SortType=SortType.NAME

)
