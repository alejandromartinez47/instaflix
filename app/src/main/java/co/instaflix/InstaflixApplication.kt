package co.instaflix

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDexApplication
import co.instaflix.config.di.AppInjector
import co.instaflix.config.di.component.AppComponent
import co.instaflix.config.di.component.DaggerAppComponent
import co.instaflix.splash.view.SplashActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class InstaflixApplication: MultiDexApplication(), HasActivityInjector, HasSupportFragmentInjector, Application.ActivityLifecycleCallbacks {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    lateinit var mainContainerG: SplashActivity

    override fun onCreate() {
        super.onCreate()
        this.configureInjectComponent()
        this.configureEmojiCompat()
        this.initDrawables()

    }

    override fun activityInjector() = activityInjector

    override fun supportFragmentInjector() = fragmentInjector

    override fun onActivityPaused(activity: Activity) {
        Log.e(InstaflixApplication::class.java.name, "onActivityPaused")
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityDestroyed(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityResumed(activity: Activity) {
        Log.e(InstaflixApplication::class.java.name, "onActivityResumed")
    }

    private fun configureInjectComponent() {


        daggerAppComponent = DaggerAppComponent.builder()
            .application(this)
            .context(this)
            .build()

        daggerAppComponent.inject(this)
        AppInjector.init(this)
    }

    private fun initDrawables() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private fun configureEmojiCompat() {
        val config = BundledEmojiCompatConfig(this)
        config.setReplaceAll(true)
        EmojiCompat.init(config)
    }


    companion object {
        lateinit var daggerAppComponent: AppComponent
        @kotlin.jvm.JvmField var isActivityVisible: Boolean = false
    }
}

