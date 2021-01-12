package co.instaflix.home.repository

import co.instaflix.config.core.api.either.ApiResult
import co.instaflix.config.core.api.exception.ApiException
import co.instaflix.config.core.api.response.ApiErrorResponse
import co.instaflix.config.core.api.response.ApiErrorUnProcessableEntity
import co.instaflix.config.core.api.response.ApiSuccessResponse
import co.instaflix.home.api.ContentDataSource
import co.instaflix.home.model.ContentMoviesResponse
import co.instaflix.home.model.ContentSeriesResponse
import co.instaflix.home.model.Movies
import co.instaflix.home.model.Series
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject

interface ContentRepository {

    suspend fun getSeries(page: String, category: String): ApiResult<ContentSeriesResponse>
    suspend fun getMovies(page: String, category: String): ApiResult<ContentMoviesResponse>
    suspend fun getSeriesDetail(idSerie: String): ApiResult<Series>
    suspend fun getMoviesDetail(idMovie: String): ApiResult<Movies>

    class ContentNetwork @Inject constructor(private val contentDataSource: ContentDataSource) :
        ContentRepository {

        override suspend fun getSeries(
            page: String,
            category: String
        ): ApiResult<ContentSeriesResponse> = GlobalScope.async {
            val response =
                when (val apiResponse = contentDataSource.getSeriesAsync(category, page).await()) {
                    is ApiSuccessResponse ->
                        ApiResult.Success(apiResponse.data)

                    is ApiErrorResponse ->
                        ApiResult.Error(ApiException(apiResponse.errorMessage))

                    is ApiErrorUnProcessableEntity ->
                        ApiResult.Error(ApiException(apiResponse.errorMessage))

                    else ->
                        ApiResult.Error(ApiException("custom error response"))
                }

            response
        }.await()

        override suspend fun getMovies(
            page: String,
            category: String
        ): ApiResult<ContentMoviesResponse> = GlobalScope.async {
            val response =
                when (val apiResponse = contentDataSource.getMoviesAsync(category, page).await()) {
                    is ApiSuccessResponse ->
                        ApiResult.Success(apiResponse.data)

                    is ApiErrorResponse ->
                        ApiResult.Error(ApiException(apiResponse.errorMessage))

                    is ApiErrorUnProcessableEntity ->
                        ApiResult.Error(ApiException(apiResponse.errorMessage))

                    else ->
                        ApiResult.Error(ApiException("custom error response"))
                }

            response
        }.await()

        override suspend fun getSeriesDetail(idSerie: String): ApiResult<Series> =
            GlobalScope.async {
                val response = when (val apiResponse =
                    contentDataSource.getSeriesDetailAsync(idSerie).await()) {
                    is ApiSuccessResponse ->
                        ApiResult.Success(apiResponse.data)

                    is ApiErrorResponse ->
                        ApiResult.Error(ApiException(apiResponse.errorMessage))

                    is ApiErrorUnProcessableEntity ->
                        ApiResult.Error(ApiException(apiResponse.errorMessage))

                    else ->
                        ApiResult.Error(ApiException("custom error response"))
                }

                response
            }.await()

        override suspend fun getMoviesDetail(idMovie: String): ApiResult<Movies> =
            GlobalScope.async {
                val response = when (val apiResponse =
                    contentDataSource.getMovieDetailAsync(idMovie).await()) {
                    is ApiSuccessResponse ->
                        ApiResult.Success(apiResponse.data)

                    is ApiErrorResponse ->
                        ApiResult.Error(ApiException(apiResponse.errorMessage))

                    is ApiErrorUnProcessableEntity ->
                        ApiResult.Error(ApiException(apiResponse.errorMessage))

                    else ->
                        ApiResult.Error(ApiException("custom error response"))
                }

                response
            }.await()

    }
}