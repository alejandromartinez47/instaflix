package co.instaflix.config.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import co.instaflix.config.core.Injectable
import co.instaflix.config.lifecycleowner.InstaflixLifecycleOwner
import dagger.android.support.DaggerFragment
import org.jetbrains.anko.support.v4.alert
import javax.inject.Inject


abstract class BaseFragment: DaggerFragment(), Injectable {

    @Inject lateinit var instaflixViewModelFactory: ViewModelProvider.Factory

    protected val instaflixLifecycleOwner = InstaflixLifecycleOwner()


    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View?  =
            inflater.inflate(findLayoutById(), container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instaflixLifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        val fragmentManager: FragmentManager? = activity?.supportFragmentManager
        fragmentManager?.beginTransaction()?.detach(this)?.commitAllowingStateLoss()
        super.onConfigurationChanged(newConfig)
        fragmentManager?.beginTransaction()?.attach(this)?.commitAllowingStateLoss()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

    protected fun showErrorDialog(error: String, cancelable: Boolean = true, positiveButtonText: String? = "",
                                  negativeButtonText: String? = "", onPositiveButtonListener: (() -> Unit)? = null,
                                  onNegativeButtonListener: (() -> Unit)? = null) {
        alert {
            message = error
            isCancelable = cancelable
            if(positiveButtonText != null) {
                positiveButton(positiveButtonText) {
                    it.dismiss()
                    onPositiveButtonListener?.invoke()
                }
            }

            if(negativeButtonText != null) {
                negativeButton(negativeButtonText) {
                    it.dismiss()
                    onNegativeButtonListener?.invoke()
                }
            }
            show()
        }
    }
}