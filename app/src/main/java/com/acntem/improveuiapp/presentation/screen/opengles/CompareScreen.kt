package com.acntem.improveuiapp.presentation.screen.opengles

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.acntem.improveuiapp.presentation.common.SimpleSwitchOptimizationLayout
import com.acntem.improveuiapp.presentation.ui.theme.dimens

@Composable
fun CompareScreen(
    onBack: () -> Unit = {},
) {
    val context = LocalContext.current
    val isOptimizeMode = rememberSaveable {
        mutableStateOf(false)
    }

    SimpleSwitchOptimizationLayout(
        title = "OpenGLES vs Canvas",
        useVerticalScroll = true,
        isOptimizeMode = isOptimizeMode.value,
        onPopBackStack = {
            onBack()
        },
        nonOptimizeContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        MaterialTheme.dimens.medium2
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(320.dp)
                        .padding(8.dp)
                ) {
                    SixStarRings(modifier = Modifier.fillMaxSize())
                }
            }
        },
        optimizeContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.medium2),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(320.dp)
                        .padding(8.dp)
                ) {
                    SixStarRingsGL(modifier = Modifier.fillMaxSize(), context = context as Activity)
                }
            }
        },
        sharedContent = {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        MaterialTheme.dimens.medium2
                    ),
                elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.dimens.medium1)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            MaterialTheme.dimens.medium2
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        if (isOptimizeMode.value) "OpenGLES" else "Canvas",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Switch(
                        checked = isOptimizeMode.value,
                        onCheckedChange = {
                            isOptimizeMode.value = it
                        })
                }
            }
        }
    )
}