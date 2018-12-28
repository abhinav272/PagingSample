package com.abhinav.pagingsample.data

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.abhinav.pagingsample.data.model.RepoEntity

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<RepoEntity>)

    @Query("SELECT * FROM repos WHERE (name LIKE :queryString) OR (description LIKE " +
            ":queryString) ORDER BY stars DESC, name ASC")
    fun fetchRepo(queryString: String): DataSource.Factory<Int, RepoEntity>

    @Delete
    fun deleteRepo(repoEntity: RepoEntity)

    @Query("UPDATE repos SET stars = :stars WHERE id = :id")
    fun starRepo(stars: Int, id: Long)
}