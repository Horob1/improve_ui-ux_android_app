package com.acntem.improveuiapp.di

import com.acntem.improveuiapp.domain.usecase.GetSafeNavStateUseCase
import com.acntem.improveuiapp.domain.usecase.SaveSafeNavStateUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single {
        GetSafeNavStateUseCase(get())
    }

    single {
        SaveSafeNavStateUseCase(get())
    }
}