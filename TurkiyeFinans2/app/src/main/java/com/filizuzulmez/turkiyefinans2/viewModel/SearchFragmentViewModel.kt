package com.filizuzulmez.turkiyefinans2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.filizuzulmez.turkiyefinans2.model.Result
import com.filizuzulmez.turkiyefinans2.model.Section
import com.filizuzulmez.turkiyefinans2.network.SearchAPI
import com.filizuzulmez.turkiyefinans2.network.SearchAPIService
import com.filizuzulmez.turkiyefinans2.paging.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import java.util.*

class SearchFragmentViewModel: ViewModel()  {
    lateinit var retroService: SearchAPI

    init {
        retroService = SearchAPIService.getRetroInstance().create(SearchAPI::class.java)
    }

    fun getListData(searchItem: String): Flow<PagingData<Result>> {
        return Pager (config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = {SearchPagingSource(retroService, searchItem)}).flow.cachedIn(viewModelScope)
    }



}