package co.instaflix.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import co.instaflix.BuildConfig
import co.instaflix.R
import co.instaflix.config.view.BaseFragment
import co.instaflix.navigation.view.NavigationActivity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun ImageView.loadImage(url: String) =
    Picasso.with(this.context)
        .load(BuildConfig.URL_IMAGES+url)
        .into(this)

fun BaseFragment.goFragment(fragment: BaseFragment, activityR: FragmentActivity, container: Int, backStack: String?) {

    val fragmentTransaction = activityR!!.supportFragmentManager.beginTransaction()
    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right)
    fragmentTransaction.replace(container,fragment,backStack)
    if(backStack != null) fragmentTransaction.addToBackStack(backStack)
    NavigationActivity().changeCurrent(fragment)
    fragmentTransaction.commit()

}