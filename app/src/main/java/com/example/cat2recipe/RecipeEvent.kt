package com.example.cat2recipe

sealed interface RecipeEvent {
    object SaveRecipe:RecipeEvent
    data class  SetName(val Name: String): RecipeEvent
    data class  SetFoodType(val FoodType: String): RecipeEvent
    data class  SetDifficulty(val Difficulty: String):RecipeEvent
    data class  SetIngredients(val Ingredients: String): RecipeEvent
    object ShowDialog:RecipeEvent
    object HideDialog:RecipeEvent
    data class  SortRecipe(val sortType: SortType): RecipeEvent
    data class  DeleteRecipe(val recipe: Recipe): RecipeEvent


}