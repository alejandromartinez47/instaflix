package co.instaflix.config.view

import android.content.res.Configuration
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import android.os.Bundle
import co.instaflix.config.core.Injectable
import co.instaflix.config.lifecycleowner.InstaflixLifecycleOwner


abstract class BaseActivity: DaggerAppCompatActivity(), Injectable {

    @Inject lateinit var instaflixViewModelFactory: ViewModelProvider.Factory
    protected val instaflixLifecycleOwner = InstaflixLifecycleOwner()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(findLayoutById())
        instaflixLifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        initUi()
    }

    override fun onResume() {
        super.onResume()
        instaflixLifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onDestroy() {
        super.onDestroy()
        instaflixLifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }


    protected abstract fun findLayoutById(): Int

    protected abstract fun initUi()

}