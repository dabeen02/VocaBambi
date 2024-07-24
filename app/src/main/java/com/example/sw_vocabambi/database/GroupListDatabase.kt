package com.example.wordbook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

//해당 이름의 data base 생성. 추후 GroupWord가 누락될 시 여기에 테이블 생성되는지 확인.
@Database(entities = [Group::class, GroupWord::class], version = 1)
abstract class GroupListDatabase: RoomDatabase(){
    abstract val groupDao: GroupDao
    abstract val groupwordDao: GroupWordDao
}

private const val DATABASE_NAME = "groups.db"
private lateinit var INSTANCE: GroupListDatabase

fun getDatabaseGroupList(context: Context): GroupListDatabase {
    synchronized(GroupListDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                GroupListDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        fillInDb(context.applicationContext)
                    }
                })
                .build()
        }
    }
    return INSTANCE
}