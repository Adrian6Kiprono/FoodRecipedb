package com.example.cat2recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeDialog(
    state: RecipeState,
    onEvent: (RecipeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(RecipeEvent.HideDialog)
        },
        title = { Text(text = "Add Recipe") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.Name,
                    onValueChange = {
                        onEvent(RecipeEvent.SetName(it))
                    },
                    placeholder = {
                        Text(text = " name")
                    }
                )
                TextField(
                    value = state.Food_type,
                    onValueChange = {
                        onEvent(RecipeEvent.SetFoodType(it))
                    },
                    placeholder = {
                        Text(text = "Food type")
                    }
                )
                TextField(
                    value = state.Difficulty,
                    onValueChange = {
                        onEvent(RecipeEvent.SetDifficulty(it))
                    },
                    placeholder = {
                        Text(text = "Difficulty")
                    }
                )
                TextField(
                    value = state.Ingredients,
                    onValueChange = {
                        onEvent(RecipeEvent.SetIngredients(it))
                    },
                    placeholder = {
                        Text(text = "Ingredients")
                    }
                )

            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(RecipeEvent.SaveRecipe)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}