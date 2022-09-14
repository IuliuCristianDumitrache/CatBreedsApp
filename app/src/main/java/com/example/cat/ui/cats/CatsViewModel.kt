package com.example.cat.ui.cats

import androidx.lifecycle.*
import com.example.cat.data.CatDao
import com.example.cat.data.repository.CatRepository
import com.example.cat.extensions.disposeIfNotAlready
import com.example.cat.models.CatModel
import com.example.cat.network.exceptions.NetworkExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val catDao: CatDao,
    private val state: SavedStateHandle,
    private val catRepository: CatRepository,
    private val networkExceptionHandler: NetworkExceptionHandler
) : ViewModel() {

    val catsList: MutableLiveData<List<CatModel>> = MutableLiveData()
    val showProgress: MutableLiveData<Boolean> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()
    var catsDisposable: Disposable? = null
    val searchQuery = state.getLiveData("searchQuery", "")

    private val catFlow =
        searchQuery.asFlow().map { query ->
            catDao.getFilteredCatsByNameOrCountry(query)
        }.map {
            it.map { catEntity -> catEntity.toCatModel() }
        }

    val catsFiltered = catFlow.asLiveData()

    fun getCatsList() {
        showProgress.postValue(true)
        catsDisposable = catRepository.fetchCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .doAfterTerminate { showProgress.postValue(false) }
            .subscribeBy(
                onNext = {
                    catsList.postValue(it)
                },
                onError = {
                    showError.postValue(networkExceptionHandler.map(it).errorMessage)
                }
            )
    }

    override fun onCleared() {
        catsDisposable?.disposeIfNotAlready()
        super.onCleared()
    }
}