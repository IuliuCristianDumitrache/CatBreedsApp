package com.example.cat.data.repository

import com.example.cat.data.remotedatasource.CatLocalDataSource
import com.example.cat.data.remotedatasource.CatRemoteDataSource
import com.example.cat.models.CatModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatRepositoryImpl @Inject constructor(
    private val catRemoteDataSource: CatRemoteDataSource,
    private val catLocalDataSource: CatLocalDataSource
) : CatRepository {
    override fun fetchCats(): Observable<List<CatModel>> =
        catRemoteDataSource.fetchCats().doOnNext {
            catLocalDataSource.insertCats(it)
        }
}