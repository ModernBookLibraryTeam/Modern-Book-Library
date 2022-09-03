package gu_android_team.modernbooklibrary.domain.usecases.methods

import gu_android_team.modernbooklibrary.domain.FakeBook

interface GetFakeBooksListUsaCase {
    fun getFakeBooksList(): List<FakeBook>
}