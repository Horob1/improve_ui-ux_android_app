package com.acntem.improveuiapp.presentation.screen.ux.groupbutton

import com.acntem.improveuiapp.domain.model.BackgroundTheme

data class GBState(
    val mode: Boolean = false,
    val backgroundTheme: BackgroundTheme = BackgroundTheme.Accent,
)