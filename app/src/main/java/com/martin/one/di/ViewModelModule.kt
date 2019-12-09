package com.martin.one.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.martin.one.ui.detail.DetailViewModel
import com.martin.one.ui.main.MainViewModel
import com.martin.one.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindsDetailViewModel(detailViewModel: DetailViewModel):ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
