package com.example.cat.data.remotedatasource

import com.example.cat.models.CatModel
import com.example.cat.network.ApiService
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    fun fetchCats(): Observable<List<CatModel>> =
        apiService.getCats()
}