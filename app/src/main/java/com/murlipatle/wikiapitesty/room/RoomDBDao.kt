package com.murlipatle.wikiapitesty.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.murlipatle.wikiapitesty.retrofit.responce.Allcategory

@Dao
interface RoomDBDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllcategorys(list: List<Allcategory>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllcategorySingle(article: Allcategory)

    @Query("SELECT * FROM Allcategory ")
     fun  getAllAllcategorys(): PagingSource<Int, Allcategory>

    @Query("DELETE FROM Allcategory")
    suspend fun deleteAllAllcategory()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategoryRemoteKeys(list: List<CategoryRemoteKey>)


    @Query("SELECT * FROM CategoryRemoteKey WHERE id = :id")
    suspend fun getAllCategoryRemoteKey(id: String): CategoryRemoteKey?

    @Query("DELETE FROM CategoryRemoteKey")
    suspend fun deleteAllCategoryRemoteKeys()



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(list: List<ArticleDetail>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleSingle(article: ArticleDetail)

    @Query("SELECT * FROM ArticleDetail ")
     fun  getAllArticles(): PagingSource<Int, ArticleDetail>

    @Query("DELETE FROM ArticleDetail")
    suspend fun deleteAllArticles()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(list: List<ArticleRemoteKey>)


    @Query("SELECT * FROM ArticleRemoteKey WHERE id = :id")
    suspend fun getAllREmoteKey(id: Int): ArticleRemoteKey?

    @Query("DELETE FROM ArticleRemoteKey")
    suspend fun deleteAllRemoteKeys()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(list: List<ImageDetail>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAImagesSingle(article: ImageDetail)

    @Query("SELECT * FROM ImageDetail ")
     fun  getAllImages(): PagingSource<Int, ImageDetail>

    @Query("DELETE FROM ImageDetail")
    suspend fun deleteAllImages()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllImageRemoteKeys(list: List<ImagesRemoteKey>)


    @Query("SELECT * FROM ImagesRemoteKey WHERE id = :id")
    suspend fun getAllImagesRemoteKey(id: Int): ImagesRemoteKey?

    @Query("DELETE FROM ImagesRemoteKey")
    suspend fun deleteAllImagesRemoteKeys()

}