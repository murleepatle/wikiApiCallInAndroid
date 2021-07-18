package com.murlipatle.wikiapitesty.ui.main.category

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.murlipatle.wikiapitesty.paging.CategoryRemoteMediator
import com.murlipatle.wikiapitesty.retrofit.RestApiInterface
import com.murlipatle.wikiapitesty.room.RoomDBDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel  @Inject constructor(private val roomDBDao: RoomDBDao,
                                             private val restApiInterface: RestApiInterface
) : ViewModel() {



    @ExperimentalPagingApi
    val pager = Pager(PagingConfig(pageSize = 10),
        remoteMediator = CategoryRemoteMediator(roomDBDao, restApiInterface)
    ) {

            roomDBDao.getAllAllcategorys()
    }.flow
}