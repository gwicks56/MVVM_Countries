package com.gwicks.countries.di

import com.gwicks.countries.model.CountriesService
import com.gwicks.countries.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: CountriesService)

    fun inject(viewModel: ListViewModel)

}