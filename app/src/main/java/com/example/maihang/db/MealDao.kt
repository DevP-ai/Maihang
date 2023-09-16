package com.example.maihang.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maihang.model.Meal

@Dao
interface MealDao {

    /*
     onConflict = OnConflictStrategy.REPLACE  is for updating the data if same data present in
    database then older one will replaced by new one
    */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeal():LiveData<List<Meal>>
}