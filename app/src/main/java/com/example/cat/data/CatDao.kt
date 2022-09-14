package com.example.cat.data

import androidx.room.*

@Dao
interface CatDao {
    @Query("SELECT * FROM CAT WHERE (COUNTRY_CODE LIKE '%' || :query || '%' OR NAME LIKE '%' || :query || '%')")
    suspend fun getFilteredCatsByNameOrCountry(query: String): List<CatEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cat: CatEntity)
}