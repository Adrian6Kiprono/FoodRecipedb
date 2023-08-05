package com.example.cat2recipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    state: RecipeState,
    onEvent:(RecipeEvent) ->Unit
){
    Scaffold(
        floatingActionButton = {
        FloatingActionButton(onClick = {
            onEvent(RecipeEvent.ShowDialog)
        }) {
            Icon(imageVector = Icons.Default.Add,
                contentDescription = "Add recipe")
             }


    }) {padding ->
        if (state.isAddingRecipe){
            AddRecipeDialog(state = state, onEvent =onEvent )
        }


        LazyColumn(
            contentPadding = padding,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp))
        {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically
                ) {
                    SortType.values().forEach { sortType ->
                        Row(modifier = Modifier
                            .clickable { onEvent(RecipeEvent.SortRecipe(sortType))
                                       },
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(selected = state.sortType == sortType,
                                onClick = { onEvent(RecipeEvent.SortRecipe(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.recipe){ recipe ->
            Row(modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "${recipe.Name}")
                    Text(
                        text = "${recipe.Food_type}")
                    Text(
                        text = "${recipe.Difficulty}")
                    Text(
                        text = "${recipe.Ingredients}")
                }
            }
            IconButton(onClick = {
                onEvent(RecipeEvent.DeleteRecipe(recipe))
            }) {
                Icon(imageVector = Icons.Default.Delete,
                    contentDescription = "delete recipe")
            }

            }

        }


    }
}