package com.houmei.workout

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity) //https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread

    @Query("SELECT * FROM `history-table`")
    fun fetchAllDates(): Flow<List<HistoryEntity>>
}
