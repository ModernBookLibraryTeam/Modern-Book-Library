package gu_android_team.modernbooklibrary.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gu_android_team.modernbooklibrary.domain.FakeBook
import gu_android_team.modernbooklibrary.domain.usecases.screens.MainScreenUsaCase

class MainScreenViewModel(private val usecase: MainScreenUsaCase) : ViewModel() {

    companion object {
        private const val FIRST_TITLE = "New Books"
        private const val SECOND_TITLE = "Android Books"
        private const val THIRD_TITLE = "Kotlin Books"
        private const val FOURTH_TITLE = "Compose Books"
    }

    private val _livedataToObserve = MutableLiveData<LinkedHashMap<String, List<FakeBook>>>()
    val livedataToObserve: LiveData<LinkedHashMap<String, List<FakeBook>>> = _livedataToObserve

    private val listOfLists = linkedMapOf<String, List<FakeBook>>()

    fun getLists() {
        listOfLists[FIRST_TITLE] = usecase.getFakeBooksList()
        listOfLists[SECOND_TITLE] = usecase.getFakeBooksList()
        listOfLists[THIRD_TITLE] = usecase.getFakeBooksList()
        listOfLists[FOURTH_TITLE] = usecase.getFakeBooksList()
        _livedataToObserve.postValue(listOfLists)
    }
}