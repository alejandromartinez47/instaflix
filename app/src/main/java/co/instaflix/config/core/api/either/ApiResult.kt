package co.instaflix.config.core.api.either

import co.instaflix.config.core.api.exception.ApiException

sealed class ApiResult<out T: Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val exception: ApiException) : ApiResult<Nothing>()
}