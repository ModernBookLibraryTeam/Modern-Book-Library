package gu_android_team.modernbooklibrary.data.usacases

import gu_android_team.modernbooklibrary.data.repository.FakeRepo
import gu_android_team.modernbooklibrary.domain.FakeBook
import gu_android_team.modernbooklibrary.domain.usecases.screens.MainScreenUsaCase

class MainScreenUseCaseImpl(private val fakeRepo: FakeRepo) : MainScreenUsaCase {
    override fun getNewBooksFromRemoteSource() {
        TODO("добавить реализацию, когда появится remoteSource")
    }

    override fun getFakeBooksList(): List<FakeBook> {
        return fakeRepo.returnFakeBooksList()
    }
}