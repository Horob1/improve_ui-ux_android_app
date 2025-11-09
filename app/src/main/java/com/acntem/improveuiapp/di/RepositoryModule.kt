package com.acntem.improveuiapp.di

import com.acntem.improveuiapp.data.repository.CommonRepositoryImpl
import com.acntem.improveuiapp.data.repository.SafeNavRepositoryImpl
import com.acntem.improveuiapp.domain.repository.CommonRepository
import com.acntem.improveuiapp.domain.repository.SafeNavRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SafeNavRepository> {
        SafeNavRepositoryImpl(
            get()
        )
    }

    single<CommonRepository> {
        CommonRepositoryImpl(
            get()
        )
    }
}