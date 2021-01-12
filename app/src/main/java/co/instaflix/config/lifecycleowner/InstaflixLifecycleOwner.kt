package co.instaflix.config.lifecycleowner

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class InstaflixLifecycleOwner: LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)
    override fun getLifecycle() = lifecycleRegistry
}