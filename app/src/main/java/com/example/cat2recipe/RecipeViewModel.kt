package com.example.cat2recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.jar.Attributes.Name

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeViewModel(
    private val dao: RecipeDao
):ViewModel() {

    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _recipes = _sortType
        .flatMapLatest { _sortType ->
            when(_sortType){
                SortType.NAME -> dao.getRecipeOrderedByName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(RecipeState())
    val state = combine(_state,_sortType,_recipes){
        _state,sortType, recipes ->_state.copy(
        recipe = recipes,
        sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RecipeState())

    fun onEvent(event: RecipeEvent) {
        when(event){
            is RecipeEvent.DeleteRecipe -> {
                viewModelScope.launch {
                    dao.deleteRecipe(event.recipe)
                }

            }
            RecipeEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingRecipe = false
                ) }
            }
            RecipeEvent.SaveRecipe -> {
                val name= state.value.Name
                val foodType= state.value.Food_type
                val difficulty= state.value.Difficulty
                val ingredients= state.value.Ingredients

                if (name.isBlank() || foodType.isBlank() || difficulty.isBlank() || ingredients.isBlank()){
                    return
                }
                val recipe =  Recipe(
                    Name = name,
                    Food_type = foodType,
                    Difficulty = difficulty,
                    Ingredients = ingredients
                )
                viewModelScope.launch{
                    dao.upsertRecipe(recipe)
                }
                _state.update { it.copy(
                    isAddingRecipe = false,
                    Name = "",
                    Food_type = "",
                    Difficulty = "",
                    Ingredients = ""
                ) }
            }
            is RecipeEvent.SetName -> {
                _state.update { it.copy(
                    Name = event.Name
                ) }
            }
            is RecipeEvent.SetFoodType -> {
                _state.update {
                    it.copy(
                        Food_type = event.FoodType
                    )
                }
            }
            is RecipeEvent.SetDifficulty -> {
                _state.update {
                    it.copy(
                        Difficulty = event.Difficulty
                    )
                }
            }
            is RecipeEvent.SetIngredients -> {
                _state.update {
                    it.copy(
                        Ingredients = event.Ingredients
                    )
                }
            }
            RecipeEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingRecipe = true
                ) }
            }
            is RecipeEvent.SortRecipe -> {
                _sortType.value =event.sortType
            }


        }
    }
}