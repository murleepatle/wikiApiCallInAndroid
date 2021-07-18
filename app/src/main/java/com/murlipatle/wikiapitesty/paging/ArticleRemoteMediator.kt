package com.murlipatle.wikiapitesty.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.murlipatle.wikiapitesty.retrofit.RestApiInterface
import com.murlipatle.wikiapitesty.room.ArticleDetail
import com.murlipatle.wikiapitesty.room.ArticleRemoteKey
import com.murlipatle.wikiapitesty.room.RoomDBDao
import org.json.JSONObject


@ExperimentalPagingApi
class ArticleRemoteMediator(
    private val roomDBDao: RoomDBDao,
    private val restApiInterface: RestApiInterface,
) : RemoteMediator<Int, ArticleDetail>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleDetail>
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
            val response = restApiInterface.getRandomArticle(
                "json",
                "query",
                "random",
                "0",
                "revisions|images",
                "content",
                "10"
            )

            if (response.isSuccessful) {

                response.body()?.let {
                    // flush our data
                    if (loadType == LoadType.REFRESH) {
                        roomDBDao.deleteAllArticles()
                        roomDBDao.deleteAllRemoteKeys()
                    }
                    val allcategories = mutableListOf<ArticleDetail>()

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
                             revisions=   value.getJSONArray("revisions").getJSONObject(0).getString("*")
                                images=  value.getJSONArray("images").toString()

                            }catch (e:Exception){
                            e.printStackTrace()
                        }
                        allcategories.add(ArticleDetail(0,pageId=value.getInt("pageid"),title = value.getString("title"),description = revisions,images = images))
                    }

                    val list =allcategories.map {article->
                        ArticleRemoteKey(article.id, obj.getJSONObject("continue").getString("grncontinue"))
                    }
                    // make list of remote keys

                    roomDBDao.insertAllRemoteKeys(list)
                    // insert to the room
                    roomDBDao.insertArticles(allcategories)
                }
                return MediatorResult.Success(endOfPaginationReached = false)
            } else {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

        }catch (e:Exception ){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getClosestRemoteKeys(state: PagingState<Int, ArticleDetail>): ArticleRemoteKey? {

        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let {
                roomDBDao.getAllREmoteKey(it.id)
            }
        }

    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, ArticleDetail>): ArticleRemoteKey? {
        return state.lastItemOrNull()?.let {
            roomDBDao.getAllREmoteKey(it.id)
        }
    }
}