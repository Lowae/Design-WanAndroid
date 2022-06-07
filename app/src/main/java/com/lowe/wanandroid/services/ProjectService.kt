package com.lowe.wanandroid.services

import com.lowe.wanandroid.services.model.ProjectTitle
import retrofit2.http.GET

interface ProjectService : BaseService {

    /** 获取项目分类数据 */
    @GET("project/tree/json")
    suspend fun getProjectTitleList(): ApiResponse<List<ProjectTitle>>
}