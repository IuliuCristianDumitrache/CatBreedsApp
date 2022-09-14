package com.example.cat.data.repository

import com.example.cat.models.CatModel
import io.reactivex.rxjava3.core.Observable

interface CatRepository {
    fun fetchCats(): Observable<List<CatModel>>
}