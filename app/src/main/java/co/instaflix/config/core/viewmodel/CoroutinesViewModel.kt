package co.instaflix.config.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.instaflix.config.core.api.exception.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class CoroutinesViewModel: ViewModel() {
    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    val uiScope = CoroutineScope(Dispatchers.Main)

    /**
     * Response failure from api
     */
    protected val _error = MutableLiveData<ApiException>()
    val errorResponse: LiveData<ApiException> get() = _error

}