package com.murlipatle.wikiapitesty.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.murlipatle.wikiapitesty.retrofit.RestApiInterface
import com.murlipatle.wikiapitesty.room.*
import org.json.JSONObject


@ExperimentalPagingApi
class ImagesRemoteMediator(
    private val roomDBDao: RoomDBDao,
    private val restApiInterface: RestApiInterface,
) : RemoteMediator<Int, ImageDetail>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageDetail>
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
            val response = restApiInterface.getImagesList(
                "https://commons.wikimedia.org/w/api.php",
                "query",
                "imageinfo",
                "categorymembers",
                "file",
                "Category:Featured_pictures_on_Wikimedia_Commons",
                "json",
                page,
                "timestamp|user|url"
            )

            if (response.isSuccessful) {

                response.body()?.let {
                    // flush our data
                    if (loadType == LoadType.REFRESH) {
                        roomDBDao.deleteAllImages()
                        roomDBDao.deleteAllImagesRemoteKeys()
                    }
                    val allcategories = mutableListOf<ImageDetail>()

                    val responseStr = it.string()
                    val obj = JSONObject(responseStr)
                    val objPages= obj.getJSONObject("query").getJSONObject("pages")
                    val iter: Iterator<String> = objPages.keys()
                    while (iter.hasNext()) {
                        val key = iter.next()
                            val value: JSONObject = objPages.getJSONObject(key)
                       var images=""
                        var revisions=""
                        try {
                            val imageinfoArray =  value.getJSONArray("imageinfo").getJSONObject(0)

                             images=   imageinfoArray.getString("url").toString()
                             revisions=   imageinfoArray.getJSONArray("descriptionurl").toString()
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                        allcategories.add(ImageDetail(0,value.getInt("pageid"),value.getString("title"),revisions,images))
                    }

                    val list =allcategories.map {article->
                        ImagesRemoteKey(article.pageId, obj.getJSONObject("continue").getString("gcmcontinue"))
                    }
                    // make list of remote keys

                    roomDBDao.insertAllImageRemoteKeys(list)
                    // insert to the room
                    roomDBDao.insertImages(allcategories)
                }
                return MediatorResult.Success(endOfPaginationReached = false)
            } else {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

        }catch (e:Exception ){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getClosestRemoteKeys(state: PagingState<Int, ImageDetail>): ImagesRemoteKey? {

        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let {
                roomDBDao.getAllImagesRemoteKey(it.pageId)
            }
        }

    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, ImageDetail>): ImagesRemoteKey? {
        return state.lastItemOrNull()?.let {
            roomDBDao.getAllImagesRemoteKey(it.pageId)
        }
    }
}