package com.example.cat2recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val Id: Int =0,
    val Name: String,
    val Food_type:String,
    val Difficulty:String,
    val Ingredients:String

)
