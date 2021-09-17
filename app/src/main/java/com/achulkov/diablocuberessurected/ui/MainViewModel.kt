package com.achulkov.diablocuberessurected.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.achulkov.diablocuberessurected.data.DCubeDataRepo
import com.achulkov.diablocuberessurected.data.models.DCubeItem
import com.achulkov.diablocuberessurected.data.models.DCubeRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepo : DCubeDataRepo
) : ViewModel() {

    private val disposables : CompositeDisposable = CompositeDisposable()

    val recipesList : MutableLiveData<List<DCubeRecipe>> = MutableLiveData()
    val itemsList : MutableLiveData<List<DCubeItem>> = MutableLiveData()

    init {
        getRecipesList()
        getItemsList()
    }

    fun getRecipesList() {
        disposables.add(RxFirebaseDatabase.observeSingleValueEvent(dataRepo.getFirebaseDbReference().child("recipes"))
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({dataSnap ->
                val list : MutableList<DCubeRecipe> = mutableListOf()
                dataSnap.children.forEach {
                    val singleRecipe = it.getValue(DCubeRecipe::class.java)
                    singleRecipe?.let { it1 -> list.add(it1) }
                }
                recipesList.postValue(list)

            })
            {throwable -> Timber.e(throwable)}
        )

    }

    fun getItemsList() {
        disposables.add(RxFirebaseDatabase.observeSingleValueEvent(dataRepo.getFirebaseDbReference().child("items"))
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({dataSnap ->
                val list : MutableList<DCubeItem> = mutableListOf()
                dataSnap.children.forEach {
                    val singleItem = it.getValue(DCubeItem::class.java)
                    singleItem?.let { it1 -> list.add(it1) }
                }
                itemsList.postValue(list)

            })
            {throwable -> Timber.e(throwable)}
        )

    }



    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}