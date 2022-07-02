package com.lowe.wanandroid.services

import com.lowe.wanandroid.services.model.ApiResponse
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.PageResponse
import com.lowe.wanandroid.services.model.ProjectTitle
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectService : BaseService {

    /**
     * 项目分类数据
     */
    @GET("project/tree/json")
    suspend fun getProjectTitleList(): ApiResponse<List<ProjectTitle>>

    /**
     * 项目文章列表
     */
    @GET("project/list/{pageNo}/json")
    suspend fun getProjectPageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int,
        @Query("cid") categoryId: Int
    ): ApiResponse<PageResponse<Article>>

    /**
     * 最新项目列表
     */
    @GET("article/listproject/{pageNo}/json")
    suspend fun getNewProjectPageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<PageResponse<Article>>
}