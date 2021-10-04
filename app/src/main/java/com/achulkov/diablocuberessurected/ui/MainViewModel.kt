package com.achulkov.diablocuberessurected.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.achulkov.diablocuberessurected.data.DCubeDataRepo
import com.achulkov.diablocuberessurected.data.models.*
import com.google.firebase.database.DataSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import durdinapps.rxfirebase2.RxFirebaseDatabase
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.Flowables
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepo : DCubeDataRepo
) : ViewModel() {

    private val disposables : CompositeDisposable = CompositeDisposable()

    val recipesList : MutableLiveData<List<DCubeMappedRecipe>> = MutableLiveData()
    val filteredRecipesList : MutableLiveData<List<DCubeMappedRecipe>> = MutableLiveData()
    val itemsList : MutableLiveData<List<DCubeItem>> = MutableLiveData()
    val runeWords : MutableLiveData<List<DCubeRuneword>> = MutableLiveData()
    val selectedRecipe : MutableLiveData<DCubeMappedRecipe> = MutableLiveData()
    val selectedItem : MutableLiveData<DCubeItem> = MutableLiveData()


    init {
        getItemsList()
        getRunewordsList()

    }


    /**
     * get all items(misc and base) from DB and triggers recipes list mapping
     */
    private fun getItemsList() {
        disposables.add(
            Flowables.combineLatest(
                RxJavaBridge.toV3Flowable(
                    RxFirebaseDatabase.observeValueEvent(dataRepo.getFirebaseDbReference().child("items_parsed"))),
                RxJavaBridge.toV3Flowable(
                    RxFirebaseDatabase.observeValueEvent(dataRepo.getFirebaseDbReference().child("items_parsed_base")))
            ) { snap1, snap2 ->
                val list: MutableList<DCubeItem> = mutableListOf()
                snap1.children.forEach {
                    val singleItem = it.getValue(DCubeItem::class.java)
                    singleItem?.let { it1 -> list.add(it1) }
                }
                snap2.children.forEach {
                    val singleItem = it.getValue(DCubeItem::class.java)
                    singleItem?.let { it1 -> list.add(it1) }
                }
                list
            }
                .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({list ->

                itemsList.postValue(list)
                getRecipesList(items = list)

            })
            {throwable -> Timber.e(throwable)}
        )

    }

    /**
     * get all runewords from DB
     */
    private fun getRunewordsList() {
        disposables.add(RxJavaBridge.toV3Flowable(RxFirebaseDatabase.observeValueEvent(dataRepo.getFirebaseDbReference().child("runewords_parsed")))
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({dataSnap ->
                val list : MutableList<DCubeRuneword> = mutableListOf()
                dataSnap.children.forEach {
                    val singleItem = it.getValue(DCubeRuneword::class.java)
                    singleItem?.let { it1 -> list.add(it1) }
                }
                runeWords.postValue(list)

            })
            {throwable -> Timber.e(throwable)}
        )

    }



    /**
     * gets all recipes from DB and maps them to DCubeMappedItems using items
     * triggered by getting list from getItemsList()
     * @param items list af all items
     */
    private fun getRecipesList(items: List<DCubeItem>) {
        disposables.add(RxJavaBridge.toV3Flowable(
            RxFirebaseDatabase.observeValueEvent(dataRepo.getFirebaseDbReference().child("recipes_parsed")))
            .map { dataSnap ->
                val list : MutableList<DCubeMappedRecipe> = mutableListOf()
                dataSnap.children.forEach {
                    val singleRecipe = it.getValue(DCubeRecipe::class.java)
                    singleRecipe?.let { recipe ->
                        var output : DCubeItem = DCubeItem()
                        val inputs : MutableList<DCubeMappedInput> = mutableListOf()
                        for(item in items){
                            if(recipe.output == item.itemname) {
                                output = item
                            }
                            for(input in recipe.inputs){
                                if(input.name == item.itemname) {
                                    inputs.add(DCubeMappedInput(input.count, item))
                                }
                            }
                        }
                        //used for data consistency debugging
                        if(inputs.size != recipe.inputs.size) list.add(DCubeMappedRecipe("ZZ" + recipe.name, inputs, output))
                        else list.add(DCubeMappedRecipe(recipe.name, inputs, output))
                    }
                }
                list
            }
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                recipesList.postValue(list)

            })
            {throwable -> Timber.e(throwable)}
        )

    }



    fun filterRecipes(filter : String){
        filteredRecipesList.postValue(recipesList.value?.filter { it.name.contains(filter, true) })
    }


    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}