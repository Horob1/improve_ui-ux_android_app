package com.acntem.improveuiapp.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.acntem.improveuiapp.R

// ----------------------------
// Font Families
// ----------------------------
val Inter = FontFamily(
    Font(resId = R.font.inter_medium, weight = FontWeight.Medium)
)

val Rubik = FontFamily(
    Font(resId = R.font.rubik_bold, weight = FontWeight.Bold)
)

val Roboto = FontFamily(
    Font(resId = R.font.roboto_regular, weight = FontWeight.Normal),
    Font(resId = R.font.roboto_medium, weight = FontWeight.Medium),
    Font(resId = R.font.roboto_bold, weight = FontWeight.Bold),
)

val BitCound = FontFamily(
    Font(resId = R.font.bitcount_black, weight = FontWeight.Black),
)
// ----------------------------
// Helper function for scale
// ----------------------------
fun typographyScale(
    displaySize: Float,
    headlineSize: Float,
    titleSize: Float,
    bodySize: Float,
    labelSize: Float
) = Typography(
    displayLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.ExtraBold,
        fontSize = displaySize.sp,
        lineHeight = (displaySize * 1.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Bold,
        fontSize = (displaySize * 0.85).sp,
        lineHeight = (displaySize * 1.2).sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.ExtraBold,
        fontSize = headlineSize.sp,
        lineHeight = (headlineSize * 1.2).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Bold,
        fontSize = (headlineSize * 0.8).sp,
        lineHeight = (headlineSize * 1.1).sp
    ),
    titleLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = titleSize.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = (titleSize * 0.9).sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = bodySize.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = (bodySize * 0.9).sp
    ),
    labelLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = labelSize.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = (labelSize * 0.9).sp
    )
)

// ----------------------------
// Responsive Typography Sets
// ----------------------------
val CompactTypography = typographyScale(
    displaySize = 28f,
    headlineSize = 22f,
    titleSize = 14f,
    bodySize = 13f,
    labelSize = 12f
)

val CompactMediumTypography = typographyScale(
    displaySize = 32f,
    headlineSize = 24f,
    titleSize = 14f,
    bodySize = 14f,
    labelSize = 12f
)

val CompactSmallTypography = typographyScale(
    displaySize = 22f,
    headlineSize = 16f,
    titleSize = 10f,
    bodySize = 10f,
    labelSize = 10f
)

val MediumTypography = typographyScale(
    displaySize = 38f,
    headlineSize = 30f,
    titleSize = 16f,
    bodySize = 15f,
    labelSize = 14f
)

val ExpandedTypography = typographyScale(
    displaySize = 42f,
    headlineSize = 34f,
    titleSize = 18f,
    bodySize = 16f,
    labelSize = 15f
)
