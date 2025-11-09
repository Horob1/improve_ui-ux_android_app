package com.acntem.improveuiapp.di

import com.acntem.improveuiapp.domain.usecase.GetGBColorUseCase
import com.acntem.improveuiapp.domain.usecase.GetOptimizeFormModeUseCase
import com.acntem.improveuiapp.domain.usecase.GetOptimizeGBScreenModeUseCase
import com.acntem.improveuiapp.domain.usecase.GetSafeNavStateUseCase
import com.acntem.improveuiapp.domain.usecase.SaveSafeNavStateUseCase
import com.acntem.improveuiapp.domain.usecase.SetGBColorUseCase
import com.acntem.improveuiapp.domain.usecase.SetOptimizeFormModeUseCase
import com.acntem.improveuiapp.domain.usecase.SetOptimizeGBScreenModeUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single {
        GetSafeNavStateUseCase(get())
    }

    single {
        SaveSafeNavStateUseCase(get())
    }

    single {
        GetGBColorUseCase(get())
    }

    single {
        SetGBColorUseCase(get())
    }

    single {
        GetOptimizeGBScreenModeUseCase(
            get()
        )
    }

    single {
        SetOptimizeGBScreenModeUseCase(
            get()
        )
    }

    single {
        SetOptimizeFormModeUseCase(
            get()
        )
    }

    single {
        GetOptimizeFormModeUseCase(
            get()
        )
    }

}