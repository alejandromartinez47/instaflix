package co.instaflix.home.api

import co.instaflix.config.core.api.response.ApiResponse
import co.instaflix.home.model.ContentMoviesResponse
import co.instaflix.home.model.ContentSeriesResponse
import co.instaflix.home.model.Movies
import co.instaflix.home.model.Series
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ContentDataSource {

    @GET("3/movie/{category}")
    fun getMoviesAsync(
        @Path("category") category: String,
        @Query("page") page: String
    ): Deferred<ApiResponse<ContentMoviesResponse>>

    @GET("3/tv/{category}")
    fun getSeriesAsync(
        @Path("category") category: String,
        @Query("page") page: String
    ): Deferred<ApiResponse<ContentSeriesResponse>>

    @GET("3/tv/{id_serie}")
    fun getSeriesDetailAsync(@Path("id_serie") idSerie: String): Deferred<ApiResponse<Series>>

    @GET("3/movie/{id_movie}")
    fun getMovieDetailAsync(@Path("id_movie") idMovie: String): Deferred<ApiResponse<Movies>>


}