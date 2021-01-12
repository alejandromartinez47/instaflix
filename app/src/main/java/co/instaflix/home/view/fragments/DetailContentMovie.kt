package co.instaflix.home.view.fragments

import android.annotation.SuppressLint
import co.instaflix.R
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.instaflix.config.view.BaseFragment
import co.instaflix.home.model.Movies
import co.instaflix.home.viewmodel.ContentViewModel
import co.instaflix.utils.Constants.keys.Companion.CONTENT_ID
import co.instaflix.utils.hide
import co.instaflix.utils.loadImage
import co.instaflix.utils.show
import kotlinx.android.synthetic.main.fragment_detail_content_movie.*

class DetailContentMovie : BaseFragment() {

    override fun findLayoutById() = R.layout.fragment_detail_content_movie

    private val contentViewModel: ContentViewModel by viewModels {
        instaflixViewModelFactory
    }

    override fun initUi() {
        setObserver()
        getArgumentsInit()
        uiListener()

    }

    fun getArgumentsInit() {
        arguments?.getString(CONTENT_ID)?.let {
            getDataMovieDetail(it)
        }

    }

    fun uiListener() {
        btClose.setOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
    }

    fun getDataMovieDetail(idMovie: String) {
        showLoader(true)
        contentViewModel.getDetailMovie(idMovie)
    }


    private fun setObserver() {
        contentViewModel.movieDetailObserver.observe(instaflixLifecycleOwner, Observer {
            setContent(it)
        })

        contentViewModel.errorResponse.observe(instaflixLifecycleOwner, Observer {
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setContent(it: Movies) {
        showLoader(false)
        tvTitleMovieDetail.text = it.title
        tvToolbarDetailContentMovies.text = it.title
        tvOverviewMovieDetail.text = it.overview
        tvCalificationDetailMovies.text = it.calification.toString()
        tvDuration.text = it.duration.toString()
        ivFlyerDetailMovies.loadImage(it.backdropPath)

    }

    fun showLoader(show: Boolean = false) {
        if (show)
            loaderMovieDetail.show()
        else
            loaderMovieDetail.hide()

    }
}
