package co.instaflix.home.view.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.instaflix.R
import co.instaflix.config.view.BaseFragment
import co.instaflix.home.adapter.ContentMoviesAdapter
import co.instaflix.home.adapter.ContentMoviesInterface
import co.instaflix.home.model.ContentMoviesResponse
import co.instaflix.home.model.Movies
import co.instaflix.home.viewmodel.ContentViewModel
import co.instaflix.utils.Constants.keys.Companion.CONTENT_ID
import co.instaflix.utils.Constants.keys.Companion.MOVIES_DETAIL
import co.instaflix.utils.PaginationScrollListener
import co.instaflix.utils.goFragment
import co.instaflix.utils.hide
import co.instaflix.utils.show
import kotlinx.android.synthetic.main.fragment_movies.*

/*
Se usa doble instancia de los adaptadores y lista por solo ser 2, si fueran mas se haría de forma dinamica
*/

class MoviesFragment : BaseFragment(), ContentMoviesInterface {

    private lateinit var seriesAdapterPopularMovies: ContentMoviesAdapter
    private lateinit var seriesAdapterTopMovies: ContentMoviesAdapter
    private var listTopMovies: ArrayList<Movies> = ArrayList()
    private var listPopularMovies: ArrayList<Movies> = ArrayList()
    private lateinit var llManagerPopular: LinearLayoutManager
    private lateinit var llManagerTop: LinearLayoutManager
    private var pagePopular: Int = 1
    private var pageTop: Int = 1
    private var TOTALPAGESPOPULAR: Int = 1
    private var TOTALPAGESTop: Int = 1
    private var isLoadingDataPopular: Boolean = false
    private var isLastPageDataTop: Boolean = false
    private var isLoadingDataTop: Boolean = false
    private var isLastPageDataPopular: Boolean = false

    override fun findLayoutById() = R.layout.fragment_movies


    private val contentViewModel: ContentViewModel by viewModels {
        instaflixViewModelFactory
    }

    override fun initUi() {

        (activity as AppCompatActivity).supportActionBar?.hide()
        showLoader(true)
        initList()
        setObserver()
        getTopMovies()
        getPopularMovies()
    }


    fun getTopMovies() {
        contentViewModel.getTopMovies(pageTop.toString())
    }

    fun getPopularMovies() {
        contentViewModel.getPopularMovies(pagePopular.toString())
    }

    fun initList() {
        llManagerPopular = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        llManagerTop = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        seriesAdapterTopMovies = ContentMoviesAdapter(requireContext(), listTopMovies, this)
        seriesAdapterPopularMovies = ContentMoviesAdapter(requireContext(), listPopularMovies, this)
        rvMoviesTop.layoutManager = llManagerTop
        rvMoviesPopular.layoutManager = llManagerPopular
        rvMoviesTop.adapter = seriesAdapterTopMovies
        rvMoviesPopular.adapter = seriesAdapterPopularMovies
        addListenerPopularPaginator()
        addListenerTopPaginator()
    }

    private fun setObserver() {
        contentViewModel.listTopMoviesObserver.observe(instaflixLifecycleOwner, Observer {
            updateContetTopServed(it)
        })
        contentViewModel.listPopularMoviesObserver.observe(instaflixLifecycleOwner, Observer {
            updateContetPopularServed(it)
        })

        contentViewModel.errorResponse.observe(instaflixLifecycleOwner, Observer {
            if (it.message != null) showErrorDialog(it.message)
        })
    }

    fun addListenerTopPaginator() {
        rvMoviesTop.addOnScrollListener(object : PaginationScrollListener(llManagerTop) {
            override fun loadMoreItems() {
                isLoadingDataTop = true
                pageTop += 1
                contentViewModel.getTopMovies(pageTop.toString())
            }
            override val totalPageCount: Int
                get() = TOTALPAGESTop

            override val isLastPage: Boolean
                get() = isLastPageDataTop

            override val isLoading: Boolean
                get() = isLoadingDataTop
        })
    }

    fun addListenerPopularPaginator() {
        rvMoviesPopular.addOnScrollListener(object : PaginationScrollListener(llManagerPopular) {
            override fun loadMoreItems() {
                isLoadingDataPopular = true
                pagePopular += 1
                contentViewModel.getPopularMovies(pagePopular.toString())
            }
            override val totalPageCount: Int
                get() = TOTALPAGESPOPULAR

            override val isLastPage: Boolean
                get() = isLastPageDataPopular

            override val isLoading: Boolean
                get() = isLoadingDataPopular
        })
    }

    fun showLoader(show: Boolean = false) {

        if (show)
            loaderMovies.show()
        else
            loaderMovies.hide()
    }

    fun showEmptySplash(show: Boolean) {

        if (show)
            panelEmpty.show()
        else
            panelEmpty.hide()

    }

    private fun updateContetPopularServed(it: ContentMoviesResponse) {
        listPopularMovies.addAll(it.listContent as ArrayList<Movies>)
        showLoader(false)
        if (validateEmpty()) {
            showEmptySplash(true)
        } else {
            showEmptySplash(false)
        }
        seriesAdapterPopularMovies.notifyDataSetChanged()
    }

    private fun updateContetTopServed(it: ContentMoviesResponse) {
        listTopMovies.addAll(it.listContent as ArrayList<Movies>)
        showLoader(false)
        if (validateEmpty()) {
            showEmptySplash(true)
        } else {
            showEmptySplash(false)
        }
        seriesAdapterTopMovies.notifyDataSetChanged()
    }


    fun validateEmpty(): Boolean {
        return (listTopMovies.isEmpty() && pageTop == 1) || (listPopularMovies.isEmpty() && pagePopular == 1)
    }

    override fun onItemSelect(Movie: Movies) {
        val fragment = DetailContentMovie()
        val data = Bundle()
        data.putString(CONTENT_ID, Movie.id)
        fragment.arguments = data
        goFragment(fragment, requireActivity(), R.id.fragment_container, MOVIES_DETAIL)
    }
}
