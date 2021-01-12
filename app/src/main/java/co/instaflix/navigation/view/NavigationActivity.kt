package co.instaflix.navigation.view

import co.instaflix.R
import co.instaflix.config.view.BaseActivity
import co.instaflix.config.view.BaseFragment
import co.instaflix.home.view.fragments.MoviesFragment
import co.instaflix.home.view.fragments.SeriesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_navigation.*


class NavigationActivity : BaseActivity() {

    var selectedFragment: BaseFragment? = null

    override fun findLayoutById() = R.layout.activity_navigation

    override fun initUi() {
        initialFragment()
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

    }

    fun initialFragment() {
        selectedFragment = MoviesFragment()
        commitFrament()
    }

    private val navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.pageSeries -> if (selectedFragment != SeriesFragment()) {
                    selectedFragment = SeriesFragment()
                }
                R.id.pageMovies -> selectedFragment = MoviesFragment()
            }
            if (selectedFragment != null) commitFrament()
            true
        }

    fun commitFrament() {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, selectedFragment!!
        ).commit()
    }

    fun changeCurrent(fragment: BaseFragment) {
        selectedFragment = fragment
    }


}