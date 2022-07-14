package com.gwicks.countries

import android.widget.ListView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gwicks.countries.model.CountriesService
import com.gwicks.countries.model.Country
import com.gwicks.countries.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.Instant
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()


    @Mock
    lateinit var countriesService: CountriesService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle: Single<List<Country>>? = null


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCountriesSucess(){
        val country = Country("countryName", "capital", "url")
        val countriesList: ArrayList<Country> = arrayListOf(country)

        testSingle = Single.just(countriesList)

        Mockito.`when`(countriesService.getCountries()).thenReturn(testSingle)

        listViewModel.refresh()

        assertEquals(1, listViewModel.countries.value?.size)
        assertEquals(false, listViewModel.countryLoadError.value)
        assertEquals(false, listViewModel.loading.value)

    }

    @Test
    fun getCountriesFail(){
        testSingle = Single.error(Throwable())
        Mockito.`when`(countriesService.getCountries()).thenReturn(testSingle)
        listViewModel.refresh()
        assertEquals(true, listViewModel.countryLoadError.value)
        assertEquals(false, listViewModel.loading.value)

    }

    @Before
    fun setUpRxSchedulers(){
        val immediate = object :Scheduler(){
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor{it.run()})
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler{scheduler -> immediate}
        RxJavaPlugins.setInitComputationSchedulerHandler{scheduler ->immediate}
        RxJavaPlugins.setInitNewThreadSchedulerHandler{scheduler ->immediate}
        RxJavaPlugins.setInitSingleSchedulerHandler{scheduler ->immediate}
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{scheduler ->immediate}
    }


}