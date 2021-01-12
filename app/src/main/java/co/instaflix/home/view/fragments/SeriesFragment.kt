package co.instaflix.home.view.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.instaflix.R
import co.instaflix.config.view.BaseFragment
import co.instaflix.home.adapter.ContentSeriesAdapter
import co.instaflix.home.adapter.ContentSeriesInterface
import co.instaflix.home.model.ContentSeriesResponse
import co.instaflix.home.model.Series
import co.instaflix.home.viewmodel.ContentViewModel
import co.instaflix.utils.Constants.keys.Companion.CONTENT_ID
import co.instaflix.utils.Constants.keys.Companion.SERIES_DETAIL
import co.instaflix.utils.PaginationScrollListener
import co.instaflix.utils.goFragment
import co.instaflix.utils.hide
import co.instaflix.utils.show
import kotlinx.android.synthetic.main.fragment_series.*

/*
Se usa doble instancia de los adaptadores y lista por solo ser 2, si fueran mas se haría de forma dinamica
*/

class SeriesFragment : BaseFragment(), ContentSeriesInterface {

    private lateinit var seriesAdapterPopularSeries: ContentSeriesAdapter
    private lateinit var seriesAdapterOnAirSeries: ContentSeriesAdapter
    private var listOnAirSeries: ArrayList<Series> = ArrayList()
    private var listPopularSeries: ArrayList<Series> = ArrayList()
    private lateinit var llManagerPopular: LinearLayoutManager
    private lateinit var llManagerOnAir: LinearLayoutManager
    private var pagePopular: Int = 1
    private var pageOnAir: Int = 1
    private var TOTALPAGESPOPULAR: Int = 1
    private var TOTALPAGESONAIR: Int = 1
    private var isLoadingDataPopular: Boolean = false
    private var isLastPageDataOnAir: Boolean = false
    private var isLoadingDataOnAIr: Boolean = false
    private var isLastPageDataPopular: Boolean = false

    override fun findLayoutById() = R.layout.fragment_series


    private val contentViewModel: ContentViewModel by viewModels {
        instaflixViewModelFactory
    }

    override fun initUi() {

        (activity as AppCompatActivity).supportActionBar?.hide()
        showLoader(true)
        initList()
        setObserver()
        getOnAirSeries()
        getPopularSeries()
    }


    fun getOnAirSeries() {
        contentViewModel.getOnAirSeries(pageOnAir.toString())
    }

    fun getPopularSeries() {
        contentViewModel.getPopularSeries(pagePopular.toString())
    }

    fun initList() {
        llManagerPopular = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        llManagerOnAir = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        seriesAdapterOnAirSeries =
            ContentSeriesAdapter(requireContext(), listOnAirSeries, this, false)
        seriesAdapterPopularSeries =
            ContentSeriesAdapter(requireContext(), listPopularSeries, this, true)
        rvSeriesOnAir.layoutManager = llManagerOnAir
        rvSeriesPopular.layoutManager = llManagerPopular
        rvSeriesOnAir.adapter = seriesAdapterOnAirSeries
        rvSeriesPopular.adapter = seriesAdapterPopularSeries
        addListenerPopularPaginator()
        addListenerOnAirPaginator()
    }

    private fun setObserver() {
        contentViewModel.listOnAirSeriesObserver.observe(instaflixLifecycleOwner, Observer {
            updateContetOnAirServed(it)
        })
        contentViewModel.listPopularSeriesObserver.observe(instaflixLifecycleOwner, Observer {
            updateContetPopularServed(it)
        })

        contentViewModel.errorResponse.observe(instaflixLifecycleOwner, Observer {
            if (it.message != null) showErrorDialog(it.message)
        })
    }

    fun addListenerOnAirPaginator() {
        rvSeriesOnAir.addOnScrollListener(object : PaginationScrollListener(llManagerOnAir) {
            override fun loadMoreItems() {
                isLoadingDataOnAIr = true
                pageOnAir += 1
                contentViewModel.getOnAirSeries(pageOnAir.toString())
            }

            override val totalPageCount: Int
                get() = TOTALPAGESONAIR

            override val isLastPage: Boolean
                get() = isLastPageDataOnAir

            override val isLoading: Boolean
                get() = isLoadingDataOnAIr
        })
    }

    fun addListenerPopularPaginator() {
        rvSeriesPopular.addOnScrollListener(object : PaginationScrollListener(llManagerPopular) {
            override fun loadMoreItems() {
                isLoadingDataPopular = true
                pagePopular += 1
                contentViewModel.getPopularSeries(pagePopular.toString())
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
            loaderSeries.show()
        else
            loaderSeries.hide()
    }

    fun showEmptySplash(show: Boolean) {

        if (show)
            panelEmpty.show()
        else
            panelEmpty.hide()

    }

    private fun updateContetPopularServed(it: ContentSeriesResponse) {
        listPopularSeries.addAll(it.listContent as ArrayList<Series>)
        showLoader(false)
        if (validateEmpty()) {
            showEmptySplash(true)
        } else {
            showEmptySplash(false)
        }
        seriesAdapterPopularSeries.notifyDataSetChanged()
    }

    private fun updateContetOnAirServed(it: ContentSeriesResponse) {
        listOnAirSeries.addAll(it.listContent as ArrayList<Series>)
        showLoader(false)
        if (validateEmpty()) {
            showEmptySplash(true)
        } else {
            showEmptySplash(false)
        }
        seriesAdapterOnAirSeries.notifyDataSetChanged()
    }


    fun validateEmpty(): Boolean {
        return (listOnAirSeries.isEmpty() && pageOnAir == 1) || (listPopularSeries.isEmpty() && pagePopular == 1)
    }

    override fun onItemSelect(serie: Series) {
        val fragment = DetailContentSeries()
        val data = Bundle()
        data.putString(CONTENT_ID, serie.id)
        fragment.arguments = data
        goFragment(fragment, requireActivity(), R.id.fragment_container, SERIES_DETAIL)
    }
}
