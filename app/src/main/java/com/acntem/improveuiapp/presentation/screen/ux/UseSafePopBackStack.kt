package com.acntem.improveuiapp.presentation.screen.ux

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.acntem.improveuiapp.presentation.common.SimpleSwitchOptimizationLayout
import com.acntem.improveuiapp.presentation.ui.theme.dimens

@Composable
fun UseSafePopBackStack (
    navController: NavController,
){
    SimpleSwitchOptimizationLayout(
        title = "Safe Back",
        onPopBackStack = {
            navController.navigateUp()
        },
        nonOptimizeContent = {
            NavBox(
                onClick = {
                    navController.popBackStack()
                },
                text = "Non safe back"
            )
        },
        optimizeContent = {
            NavBox(
                onClick = {
                    navController.navigateUp()
                },
                text = "Safe back"
            )
        }
    )
}

@Composable
fun NavBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
) {
    Box(
        modifier = modifier
            .clickable(
                onClick = onClick
            ).clip(
                shape = MaterialTheme.shapes.medium
            )
            .background(
                color = MaterialTheme.colorScheme.primary,
            )
            .padding(horizontal = MaterialTheme.dimens.medium2, vertical = MaterialTheme.dimens.medium2 / 2)

    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}