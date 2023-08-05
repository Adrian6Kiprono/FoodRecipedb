package com.example.cat2recipe

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query

import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Upsert
    fun upsertRecipe(recipe: Recipe)
    @Delete
    fun deleteRecipe(recipe: Recipe)


    @Query("SELECT * FROM Recipe ORDER BY Name ASC")
    fun getRecipeOrderedByName():Flow<List<Recipe>>

}