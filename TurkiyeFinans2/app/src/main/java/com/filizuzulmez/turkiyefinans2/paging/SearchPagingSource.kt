package com.filizuzulmez.turkiyefinans2.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.filizuzulmez.turkiyefinans2.model.Result
import com.filizuzulmez.turkiyefinans2.model.Section
import com.filizuzulmez.turkiyefinans2.network.SearchAPI
import java.util.ArrayList

class SearchPagingSource(val apiService: SearchAPI, val searchItem: String): PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getDataFromAPI(searchItem,currentLoadingPageKey)
            val responseData = mutableListOf<Result>()
            val data = response.results ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


    fun setDataList(searchResultList: List<Result>?):List<Result> {
        val sectionList: MutableList<Result> = ArrayList<Result>()

        val trackResultList: MutableList<Result> = ArrayList()
        val artistResultList: MutableList<Result> = ArrayList()
        val collectionResultList: MutableList<Result> = ArrayList()

        if (searchResultList != null) {
            for (i in searchResultList.indices) {
                if (searchResultList[i].wrapperType.equals("track")) {
                    trackResultList.add(searchResultList[i])
                } else if (searchResultList[i].wrapperType.equals("artist")) {
                    artistResultList.add(searchResultList[i])
                } else if (searchResultList[i].wrapperType.equals("collection")) {
                    collectionResultList.add(searchResultList[i])
                }
            }
        }

        if(trackResultList.size !=0){
            sectionList.addAll(trackResultList)
        }
        if(artistResultList.size != 0){
            sectionList.addAll(artistResultList)
        }
        if(collectionResultList.size != 0){
            sectionList.addAll(collectionResultList)
        }

        return sectionList
    }

    //wrapper types: track, artist, collection
    fun setSectionList(searchResultList: List<Result>?): List<Section>? {
        val sectionList: MutableList<Section> = ArrayList<Section>()
        val trackSection = Section()
        val artistSection = Section()
        val collectionSection = Section()
        val trackResultList: MutableList<Result> = ArrayList()
        val artistResultList: MutableList<Result> = ArrayList()
        val collectionResultList: MutableList<Result> = ArrayList()
        if (searchResultList != null) {
            for (i in searchResultList.indices) {
                if (searchResultList[i].wrapperType.equals("track")) {
                    trackSection.setSectionName("track")
                    trackResultList.add(searchResultList[i])
                } else if (searchResultList[i].wrapperType.equals("artist")) {
                    artistSection.setSectionName("artist")
                    artistResultList.add(searchResultList[i])
                } else if (searchResultList[i].wrapperType.equals("collection")) {
                    collectionSection.setSectionName("collection")
                    collectionResultList.add(searchResultList[i])
                }
            }
            if (trackResultList.size != 0) {
                trackSection.setSectionItems(trackResultList)
                sectionList.add(trackSection)
            }
            if (artistResultList.size != 0) {
                artistSection.setSectionItems(artistResultList)
                sectionList.add(artistSection)
            }
            if (collectionResultList.size != 0) {
                collectionSection.setSectionItems(collectionResultList)
                sectionList.add(collectionSection)
            }
        }
        return sectionList
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}