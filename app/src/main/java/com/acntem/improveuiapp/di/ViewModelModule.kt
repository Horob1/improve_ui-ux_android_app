package com.acntem.improveuiapp.di

import com.acntem.improveuiapp.presentation.screen.ux.form.UXFormViewModel
import com.acntem.improveuiapp.presentation.screen.ux.groupbutton.UXGBViewModel
import com.acntem.improveuiapp.presentation.screen.ux.safenav.SafeNavViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SafeNavViewModel(
            get(),
            get()
        )
    }

    viewModel {
        UXGBViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }

    viewModel {
        UXFormViewModel(
            get(),
            get()
        )
    }
}