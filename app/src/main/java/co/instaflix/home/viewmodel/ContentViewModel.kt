package co.instaflix.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.instaflix.config.core.api.either.ApiResult
import co.instaflix.config.core.viewmodel.CoroutinesViewModel
import co.instaflix.home.model.ContentMoviesResponse
import co.instaflix.home.model.ContentSeriesResponse
import co.instaflix.home.model.Movies
import co.instaflix.home.model.Series
import co.instaflix.home.repository.ContentRepository
import co.instaflix.utils.Constants.categories.Companion.ON_AIR
import co.instaflix.utils.Constants.categories.Companion.POPULAR
import co.instaflix.utils.Constants.categories.Companion.TOP
import kotlinx.coroutines.launch
import javax.inject.Inject


class ContentViewModel @Inject
constructor(
    private val contentRepository: ContentRepository
): CoroutinesViewModel() {

    private val _popularSeries = MutableLiveData<ContentSeriesResponse>()
    val listPopularSeriesObserver: LiveData<ContentSeriesResponse> get() = _popularSeries
    private val _latestSeries = MutableLiveData<ContentSeriesResponse>()
    val listOnAirSeriesObserver: LiveData<ContentSeriesResponse> get() = _latestSeries

    private val _popularMovies = MutableLiveData<ContentMoviesResponse>()
    val listPopularMoviesObserver: LiveData<ContentMoviesResponse> get() = _popularMovies
    private val _topMovies = MutableLiveData<ContentMoviesResponse>()
    val listTopMoviesObserver: LiveData<ContentMoviesResponse> get() = _topMovies



    private val _seriesDetail = MutableLiveData<Series>()
    val seriesDetailObserver: LiveData<Series> get() = _seriesDetail
    private val _movieDetail = MutableLiveData<Movies>()
    val movieDetailObserver: LiveData<Movies> get() = _movieDetail


    fun getDetailMovie(idMovie: String) {
        uiScope.launch {
            when(val response = contentRepository.getMoviesDetail(idMovie)) {
                is ApiResult.Success<Movies> ->
                    _movieDetail.value = response.data
                is ApiResult.Error ->
                    _error.value = response.exception
            }
        }
    }

    fun getDetailSeries(idSerie: String) {
        uiScope.launch {
            when(val response = contentRepository.getSeriesDetail(idSerie)) {
                is ApiResult.Success<Series> ->
                    _seriesDetail.value = response.data
                is ApiResult.Error ->
                    _error.value = response.exception
            }
        }
    }

    fun getPopularSeries(page: String) {
        uiScope.launch {
            when(val response = contentRepository.getSeries(page, ON_AIR)) {
                is ApiResult.Success<ContentSeriesResponse> ->
                    _popularSeries.value = response.data
                is ApiResult.Error ->
                    _error.value = response.exception
            }
        }
    }

    fun getOnAirSeries(page: String) {
        uiScope.launch {
            when(val response = contentRepository.getSeries(page, POPULAR)) {
                is ApiResult.Success<ContentSeriesResponse> ->
                    _latestSeries.value = response.data
                is ApiResult.Error ->
                    _error.value = response.exception
            }
        }
    }

    fun getPopularMovies(page: String) {
        uiScope.launch {
            when(val response = contentRepository.getMovies(page, POPULAR)) {
                is ApiResult.Success<ContentMoviesResponse> ->
                    _popularMovies.value = response.data
                is ApiResult.Error ->
                    _error.value = response.exception
            }
        }
    }

    fun getTopMovies(page: String) {
        uiScope.launch {
            when(val response = contentRepository.getMovies(page,TOP)) {
                is ApiResult.Success<ContentMoviesResponse> ->
                    _topMovies.value = response.data
                is ApiResult.Error ->
                    _error.value = response.exception
            }
        }
    }


}