package gu_android_team.modernbooklibrary.ui.mainscreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gu_android_team.modernbooklibrary.MainActivity.Companion.APP_SHARED_PREFERENCES
import gu_android_team.modernbooklibrary.MainActivity.Companion.LAST_CHECKED_TIME_KEY
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.usecases.screens.MainScreenUseCase
import gu_android_team.modernbooklibrary.ui.mainscreen.MainScreenFragment.Companion.FOURTH_TITLE_INDEX
import gu_android_team.modernbooklibrary.ui.mainscreen.MainScreenFragment.Companion.SECOND_TITLE_INDEX
import gu_android_team.modernbooklibrary.ui.mainscreen.MainScreenFragment.Companion.THIRD_TITLE_INDEX
import gu_android_team.modernbooklibrary.utils.AppState
import gu_android_team.modernbooklibrary.utils.ONE_VALUE
import gu_android_team.modernbooklibrary.utils.ZERO_VAL
import kotlinx.coroutines.*
import kotlin.random.Random.Default.nextInt

class MainScreenViewModel(
    private val usecase: MainScreenUseCase,
    app: Application
) : ViewModel() {

    companion object {
        private const val NEW_BOOKS_TITLE = "new books"
        private const val ANDROID_BOOKS_TITLE = "android"
        private const val KOTLIN_BOOKS_TITLE = "kotlin"
        private const val JAVA_BOOKS_TITLE = "java"
        private const val FIRST_PAGE = "1"
        private const val TODAY_TITLES_LIST_KEY = "TODAY_TITLES_LIST_KEY"
        private const val TWENTY_FOUR_HOURS: Long = 3600000 * 24
        private const val SECONDARY_TITLES_COUNT = 2
    }

    private val sharedPref = app.getSharedPreferences(
        APP_SHARED_PREFERENCES,
        Context.MODE_PRIVATE
    )

    private val possibleTitlesList =
        app.resources.getStringArray(R.array.array_of_categories).toList()
    private val todayTitlesSet = mutableSetOf<String>()
    private val todayTitlesList = mutableListOf<String>()

    private val defaultTitlesSet =
        mutableSetOf(NEW_BOOKS_TITLE, ANDROID_BOOKS_TITLE, KOTLIN_BOOKS_TITLE, JAVA_BOOKS_TITLE)

    private val _livedataToObserve = MutableLiveData<AppState>()
    val livedataToObserve: LiveData<AppState> = _livedataToObserve

    private val listOfLists = linkedMapOf<String, List<Book>>()

    fun getLists() {

        _livedataToObserve.postValue(AppState.AppStateLoading)

        fillTodayTitlesList()

        viewModelScope.launch {

            launch {
                getNewBooksList()
            }.join()

            getSearchedBooksListAsync(todayTitlesList[SECOND_TITLE_INDEX], FIRST_PAGE).join()
            getSearchedBooksListAsync(todayTitlesList[THIRD_TITLE_INDEX], FIRST_PAGE).join()
            getSearchedBooksListAsync(todayTitlesList[FOURTH_TITLE_INDEX], FIRST_PAGE).join()

            _livedataToObserve.postValue(AppState.AppStateSuccess(listOfLists))
        }
    }

    private fun fillTodayTitlesList() {

        if (checkIfItIsTimeToUpdateTitles()) {
            updateTodayTitlesSet()

            todayTitlesList.clear()
            todayTitlesList.add(NEW_BOOKS_TITLE)
            todayTitlesList.addAll(todayTitlesSet)

            with(sharedPref.edit()) {
                putStringSet(TODAY_TITLES_LIST_KEY, todayTitlesSet)
                apply()
            }

        } else {
            todayTitlesList.clear()
            todayTitlesList.add(NEW_BOOKS_TITLE)
            sharedPref.getStringSet(
                TODAY_TITLES_LIST_KEY,
                defaultTitlesSet
            )?.let { todayTitlesList.addAll(it) } ?: todayTitlesList.addAll(defaultTitlesSet)
        }
    }

    private fun updateTodayTitlesSet() {
        val randomTitlesCount = possibleTitlesList.size - ONE_VALUE

        todayTitlesSet.clear()

        while (todayTitlesSet.size <= SECONDARY_TITLES_COUNT) {
            val newTitle = possibleTitlesList[nextInt(ZERO_VAL, randomTitlesCount)]
            todayTitlesSet.add(newTitle)
        }
    }

    private fun checkIfItIsTimeToUpdateTitles(): Boolean {

        val currentTime = System.currentTimeMillis()
        val lastCheckedTime = if (!sharedPref.contains(LAST_CHECKED_TIME_KEY)) {
            with(sharedPref.edit()) {
                putLong(LAST_CHECKED_TIME_KEY, currentTime)
                apply()
            }

            currentTime
        } else {
            sharedPref.getLong(LAST_CHECKED_TIME_KEY, currentTime)
        }

        val passedTime = currentTime - lastCheckedTime

        return if (passedTime >= TWENTY_FOUR_HOURS) {

            with(sharedPref.edit()) {
                putLong(LAST_CHECKED_TIME_KEY, currentTime)
                apply()
            }

            true
        } else {
            false
        }
    }

    private suspend fun getNewBooksList() {
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            usecase.getNewBooksFromRemoteSource().collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val data = dataState.data as List<Book>
                        addListToLinkedHashMap(data, NEW_BOOKS_TITLE)
                    }

                    is DataState.Error -> {
                        _livedataToObserve.postValue(dataState.message?.let {
                            AppState.AppStateError(
                                it
                            )
                        })
                    }
                }
            }
        }
    }

    private suspend fun getSearchedBooksListAsync(
        searchWord: String,
        page: String
    ): Deferred<Unit> {

        return viewModelScope.async(Dispatchers.IO) {
            usecase.getSearchingBooksList(searchWord, page).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val data = dataState.data as List<Book>
                        addListToLinkedHashMap(data, searchWord)
                    }

                    is DataState.Error -> {
                        _livedataToObserve.postValue(dataState.message?.let {
                            AppState.AppStateError(
                                it
                            )
                        })
                    }
                }
            }
        }
    }

    private fun addListToLinkedHashMap(list: List<Book>, title: String) {
        listOfLists[title] = list
    }
}