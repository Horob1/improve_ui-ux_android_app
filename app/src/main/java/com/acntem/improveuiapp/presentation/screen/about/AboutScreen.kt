package com.acntem.improveuiapp.presentation.screen.about

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assistant
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.acntem.improveuiapp.R
import com.acntem.improveuiapp.presentation.ui.theme.BitCound
import com.acntem.improveuiapp.presentation.ui.theme.dimens

@Preview
@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.dimens.small2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1)
        ) {
            Icon(
                imageVector = Icons.Filled.Assistant,
                contentDescription = "App Logo",
                modifier = Modifier.size(MaterialTheme.dimens.logoSize * 2),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Improve UI App",
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = BitCound
            )
            Text(
                text = "Version 1.0.0",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "A demo application for techniques and animations in Jetpack Compose to improve UI/UX skills.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small2)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1)
        ) {
            Text(
                text = "Made By",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = BitCound
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AboutCard(
                    image = R.drawable.red_face,
                    name = "Anh"
                )
                AboutCard(
                    image = R.drawable.blue_face,
                    name = "Công"
                )
                AboutCard(
                    image = R.drawable.violet_face,
                    name = "Nhật"
                )
            }
        }
    }
}

@Preview
@Composable
fun AboutCard(
    @DrawableRes image: Int = R.drawable.red_face,
    name: String = "Anh",
    size: Dp = (MaterialTheme.dimens.logoSize * 2),
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .padding(MaterialTheme.dimens.small1),
    ) {
        Image(
            painter = painterResource(id = image),
            modifier = Modifier
                .size(
                    size
                )
                .padding(MaterialTheme.dimens.small1)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            contentDescription = "Author",
        )

        Text(
            text = name,
            Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = BitCound
            ),
        )
    }
}