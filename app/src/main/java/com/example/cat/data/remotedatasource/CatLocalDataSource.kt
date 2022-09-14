package com.example.cat.data.remotedatasource

import com.example.cat.data.CatDao
import com.example.cat.models.CatModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatLocalDataSource @Inject constructor(
    private val catDao: CatDao
) {
    fun insertCats(cats: List<CatModel>) {
        cats.forEach { cat ->
            catDao.insert(cat.mapCatModelToCatEntity())
        }
    }
}