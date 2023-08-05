package com.example.cat2recipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.cat2recipe.ui.theme.Cat2recipeTheme


class MainActivity : ComponentActivity() {

    private val database by lazy{
        Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java,
            "recipe.db"
        ).build()
    }
    private val viewModel by viewModels<RecipeViewModel> (
         factoryProducer = {
             object : ViewModelProvider.Factory{
                 override fun < T : ViewModel> create (modelClass: Class<T>): T {
                     return RecipeViewModel(database.dao) as T
                 }
             }
         }
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cat2recipeTheme {
                val state by viewModel.state.collectAsState()
                RecipeScreen(state = state, onEvent =viewModel::onEvent)
            }
        }
    }
}

