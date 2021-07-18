package com.murlipatle.wikiapitesty.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.murlipatle.wikiapitesty.retrofit.RestApiInterface
import com.murlipatle.wikiapitesty.retrofit.responce.Allcategory
import com.murlipatle.wikiapitesty.room.CategoryRemoteKey
import com.murlipatle.wikiapitesty.room.RoomDBDao

@ExperimentalPagingApi
class CategoryRemoteMediator(private val roomDBDao: RoomDBDao,
                             private val restApiInterface: RestApiInterface,
) : RemoteMediator<Int, Allcategory>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Allcategory>
    ): MediatorResult {
        try {
            // Judging the page number
            val page = when (loadType) {
                LoadType.APPEND -> {

                    val remoteKey = getLastRemoteKey(state)
                    if (remoteKey == null) {
                        ""
                    } else {
                        remoteKey.next
                    }
                    //  remoteKey!!.next ?"": return MediatorResult.Success(endOfPaginationReached = true)

                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.REFRESH -> {
                    val remoteKey = getLastRemoteKey(state)
                    if (remoteKey == null) {
                        ""
                    } else {
                        remoteKey.next
                    }
                }
            }

            // make network request
            val response = restApiInterface.getAllCategoryList(
                "query",
                "allcategories",
                "List of",
                2,
                "json",
                page
            )

            if (response.isSuccessful) {

                response.body()?.let {
                    if (it.continueX != null) {
                        // flush our data
                        if (loadType == LoadType.REFRESH) {
                            roomDBDao.deleteAllAllcategory()
                            roomDBDao.deleteAllCategoryRemoteKeys()
                        }


                        val list = response.body()?.query!!.allcategories.map {
                            CategoryRemoteKey(it.category, response.body()!!.continueX.accontinue)
                        }
                        // make list of remote keys

                        roomDBDao.insertAllCategoryRemoteKeys(list)
                        // insert to the room
                        roomDBDao.insertAllcategorys(it.query.allcategories)
                    }
                }
                return MediatorResult.Success(endOfPaginationReached = false)
            } else {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

        }catch (e:Exception ){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getClosestRemoteKeys(state: PagingState<Int, Allcategory>): CategoryRemoteKey? {

        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let {
                roomDBDao.getAllCategoryRemoteKey(it.category)
            }
        }

    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Allcategory>): CategoryRemoteKey? {
        return state.lastItemOrNull()?.let {
            roomDBDao.getAllCategoryRemoteKey(it.category)
        }
    }
}