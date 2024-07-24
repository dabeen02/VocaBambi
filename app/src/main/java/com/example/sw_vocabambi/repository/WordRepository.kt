package com.example.wordbook.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.wordbook.database.Word
import com.example.wordbook.database.WordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepository(private val database: WordDatabase) {
    fun getWordListByLiveData(): LiveData<List<Word>> {
        return database.wordDao.selectAllWithLiveData()
    }

    suspend fun getWordList(): List<Word> {
        return withContext(Dispatchers.IO) {
            database.wordDao.selectAll()
        }
    }

    suspend fun save(word: Word): Long {
        return withContext(Dispatchers.IO) {
            database.wordDao.insert(word)
        }
    }

    suspend fun update(word: Word): Int {
        return withContext(Dispatchers.IO) {
            database.wordDao.update(word)
        }
    }

    suspend fun findById(id: Int): Word {
        return withContext(Dispatchers.IO) {
            database.wordDao.findById(id)
        }
    }

    suspend fun searchWords(searchKeyword: String): List<Word> {
        return withContext(Dispatchers.IO) {
            database.wordDao.searchWords(searchKeyword)
        }
    }

    suspend fun getWordByEnglish(english: String): Word? {
        return withContext(Dispatchers.IO) {
            database.wordDao.getWordByEnglish(english)
        }
    }

    suspend fun getCounts(): Int {
        return withContext(Dispatchers.IO) {
            database.wordDao.getCount()
        }
    }

    suspend fun delete(word: Word): Int {
        return withContext(Dispatchers.IO) {
            database.wordDao.delete(word)
        }
    }
}