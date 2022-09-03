package gu_android_team.modernbooklibrary.data.repository


import gu_android_team.modernbooklibrary.domain.FakeBook

class FakeRepo {

    fun returnFakeBooksList(): List<FakeBook> {
        return mutableListOf(
            FakeBook(
                "Practical MongoDB",
                "Architecting, Developing, and Administering MongoDB",
                "https://itbook.store/img/books/9781484206485.png",
                "9781484206485"
            ),
            FakeBook(
                "The Definitive Guide to MongoDB, 3rd Edition",
                "A complete guide to dealing with Big Data using MongoDB",
                "https://itbook.store/img/books/9781484211830.png",
                "9781484211830"
            ),
            FakeBook(
                "MongoDB in Action, 2nd Edition",
                "Covers MongoDB version 3.0",
                "https://itbook.store/img/books/9781617291609.png",
                "9781617291609"
            )
        )
    }
}