package com.example.cat2recipe

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Recipe::class],
    version = 1
)

abstract class RecipeDatabase: RoomDatabase() {
    abstract val dao: RecipeDao

}