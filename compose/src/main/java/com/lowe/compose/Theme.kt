package com.lowe.compose

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.lowe.common.base.app.CommonApplicationProxy

sealed interface ChangeableTheme {

    val LightColors: ColorScheme

    val DarkColors: ColorScheme
}

object DynamicTheme : ChangeableTheme {
    override val LightColors = dynamicLightColorScheme(CommonApplicationProxy.application)

    override val DarkColors = dynamicDarkColorScheme(CommonApplicationProxy.application)
}

object DefaultTheme : ChangeableTheme {
    override val LightColors = lightColorScheme(
        primary = DefaultColor.md_theme_light_primary,
        onPrimary = DefaultColor.md_theme_light_onPrimary,
        primaryContainer = DefaultColor.md_theme_light_primaryContainer,
        onPrimaryContainer = DefaultColor.md_theme_light_onPrimaryContainer,
        secondary = DefaultColor.md_theme_light_secondary,
        onSecondary = DefaultColor.md_theme_light_onSecondary,
        secondaryContainer = DefaultColor.md_theme_light_secondaryContainer,
        onSecondaryContainer = DefaultColor.md_theme_light_onSecondaryContainer,
        tertiary = DefaultColor.md_theme_light_tertiary,
        onTertiary = DefaultColor.md_theme_light_onTertiary,
        tertiaryContainer = DefaultColor.md_theme_light_tertiaryContainer,
        onTertiaryContainer = DefaultColor.md_theme_light_onTertiaryContainer,
        error = DefaultColor.md_theme_light_error,
        errorContainer = DefaultColor.md_theme_light_errorContainer,
        onError = DefaultColor.md_theme_light_onError,
        onErrorContainer = DefaultColor.md_theme_light_onErrorContainer,
        background = DefaultColor.md_theme_light_background,
        onBackground = DefaultColor.md_theme_light_onBackground,
        surface = DefaultColor.md_theme_light_surface,
        onSurface = DefaultColor.md_theme_light_onSurface,
        surfaceVariant = DefaultColor.md_theme_light_surfaceVariant,
        onSurfaceVariant = DefaultColor.md_theme_light_onSurfaceVariant,
        outline = DefaultColor.md_theme_light_outline,
        inverseOnSurface = DefaultColor.md_theme_light_inverseOnSurface,
        inverseSurface = DefaultColor.md_theme_light_inverseSurface,
        inversePrimary = DefaultColor.md_theme_light_inversePrimary,
        surfaceTint = DefaultColor.md_theme_light_surfaceTint,
    )


    override val DarkColors = darkColorScheme(
        primary = DefaultColor.md_theme_dark_primary,
        onPrimary = DefaultColor.md_theme_dark_onPrimary,
        primaryContainer = DefaultColor.md_theme_dark_primaryContainer,
        onPrimaryContainer = DefaultColor.md_theme_dark_onPrimaryContainer,
        secondary = DefaultColor.md_theme_dark_secondary,
        onSecondary = DefaultColor.md_theme_dark_onSecondary,
        secondaryContainer = DefaultColor.md_theme_dark_secondaryContainer,
        onSecondaryContainer = DefaultColor.md_theme_dark_onSecondaryContainer,
        tertiary = DefaultColor.md_theme_dark_tertiary,
        onTertiary = DefaultColor.md_theme_dark_onTertiary,
        tertiaryContainer = DefaultColor.md_theme_dark_tertiaryContainer,
        onTertiaryContainer = DefaultColor.md_theme_dark_onTertiaryContainer,
        error = DefaultColor.md_theme_dark_error,
        errorContainer = DefaultColor.md_theme_dark_errorContainer,
        onError = DefaultColor.md_theme_dark_onError,
        onErrorContainer = DefaultColor.md_theme_dark_onErrorContainer,
        background = DefaultColor.md_theme_dark_background,
        onBackground = DefaultColor.md_theme_dark_onBackground,
        surface = DefaultColor.md_theme_dark_surface,
        onSurface = DefaultColor.md_theme_dark_onSurface,
        surfaceVariant = DefaultColor.md_theme_dark_surfaceVariant,
        onSurfaceVariant = DefaultColor.md_theme_dark_onSurfaceVariant,
        outline = DefaultColor.md_theme_dark_outline,
        inverseOnSurface = DefaultColor.md_theme_dark_inverseOnSurface,
        inverseSurface = DefaultColor.md_theme_dark_inverseSurface,
        inversePrimary = DefaultColor.md_theme_dark_inversePrimary,
        surfaceTint = DefaultColor.md_theme_dark_surfaceTint,
    )
}

object BegoniaTheme : ChangeableTheme {
    override val LightColors = lightColorScheme(
        primary = BegoniaColor.begonia_red_light_primary,
        onPrimary = BegoniaColor.begonia_red_light_onPrimary,
        primaryContainer = BegoniaColor.begonia_red_light_primaryContainer,
        onPrimaryContainer = BegoniaColor.begonia_red_light_onPrimaryContainer,
        secondary = BegoniaColor.begonia_red_light_secondary,
        onSecondary = BegoniaColor.begonia_red_light_onSecondary,
        secondaryContainer = BegoniaColor.begonia_red_light_secondaryContainer,
        onSecondaryContainer = BegoniaColor.begonia_red_light_onSecondaryContainer,
        tertiary = BegoniaColor.begonia_red_light_tertiary,
        onTertiary = BegoniaColor.begonia_red_light_onTertiary,
        tertiaryContainer = BegoniaColor.begonia_red_light_tertiaryContainer,
        onTertiaryContainer = BegoniaColor.begonia_red_light_onTertiaryContainer,
        error = BegoniaColor.begonia_red_light_error,
        errorContainer = BegoniaColor.begonia_red_light_errorContainer,
        onError = BegoniaColor.begonia_red_light_onError,
        onErrorContainer = BegoniaColor.begonia_red_light_onErrorContainer,
        background = BegoniaColor.begonia_red_light_background,
        onBackground = BegoniaColor.begonia_red_light_onBackground,
        surface = BegoniaColor.begonia_red_light_surface,
        onSurface = BegoniaColor.begonia_red_light_onSurface,
        surfaceVariant = BegoniaColor.begonia_red_light_surfaceVariant,
        onSurfaceVariant = BegoniaColor.begonia_red_light_onSurfaceVariant,
        outline = BegoniaColor.begonia_red_light_outline,
        inverseOnSurface = BegoniaColor.begonia_red_light_inverseOnSurface,
        inverseSurface = BegoniaColor.begonia_red_light_inverseSurface,
        inversePrimary = BegoniaColor.begonia_red_light_inversePrimary,
        surfaceTint = BegoniaColor.begonia_red_light_surfaceTint,
    )


    override val DarkColors = darkColorScheme(
        primary = BegoniaColor.begonia_red_dark_primary,
        onPrimary = BegoniaColor.begonia_red_dark_onPrimary,
        primaryContainer = BegoniaColor.begonia_red_dark_primaryContainer,
        onPrimaryContainer = BegoniaColor.begonia_red_dark_onPrimaryContainer,
        secondary = BegoniaColor.begonia_red_dark_secondary,
        onSecondary = BegoniaColor.begonia_red_dark_onSecondary,
        secondaryContainer = BegoniaColor.begonia_red_dark_secondaryContainer,
        onSecondaryContainer = BegoniaColor.begonia_red_dark_onSecondaryContainer,
        tertiary = BegoniaColor.begonia_red_dark_tertiary,
        onTertiary = BegoniaColor.begonia_red_dark_onTertiary,
        tertiaryContainer = BegoniaColor.begonia_red_dark_tertiaryContainer,
        onTertiaryContainer = BegoniaColor.begonia_red_dark_onTertiaryContainer,
        error = BegoniaColor.begonia_red_dark_error,
        errorContainer = BegoniaColor.begonia_red_dark_errorContainer,
        onError = BegoniaColor.begonia_red_dark_onError,
        onErrorContainer = BegoniaColor.begonia_red_dark_onErrorContainer,
        background = BegoniaColor.begonia_red_dark_background,
        onBackground = BegoniaColor.begonia_red_dark_onBackground,
        surface = BegoniaColor.begonia_red_dark_surface,
        onSurface = BegoniaColor.begonia_red_dark_onSurface,
        surfaceVariant = BegoniaColor.begonia_red_dark_surfaceVariant,
        onSurfaceVariant = BegoniaColor.begonia_red_dark_onSurfaceVariant,
        outline = BegoniaColor.begonia_red_dark_outline,
        inverseOnSurface = BegoniaColor.begonia_red_dark_inverseOnSurface,
        inverseSurface = BegoniaColor.begonia_red_dark_inverseSurface,
        inversePrimary = BegoniaColor.begonia_red_dark_inversePrimary,
        surfaceTint = BegoniaColor.begonia_red_dark_surfaceTint,
    )
}

object IrisTheme : ChangeableTheme {

    override val LightColors = lightColorScheme(
        primary = IrisColor.iris_blue_light_primary,
        onPrimary = IrisColor.iris_blue_light_onPrimary,
        primaryContainer = IrisColor.iris_blue_light_primaryContainer,
        onPrimaryContainer = IrisColor.iris_blue_light_onPrimaryContainer,
        secondary = IrisColor.iris_blue_light_secondary,
        onSecondary = IrisColor.iris_blue_light_onSecondary,
        secondaryContainer = IrisColor.iris_blue_light_secondaryContainer,
        onSecondaryContainer = IrisColor.iris_blue_light_onSecondaryContainer,
        tertiary = IrisColor.iris_blue_light_tertiary,
        onTertiary = IrisColor.iris_blue_light_onTertiary,
        tertiaryContainer = IrisColor.iris_blue_light_tertiaryContainer,
        onTertiaryContainer = IrisColor.iris_blue_light_onTertiaryContainer,
        error = IrisColor.iris_blue_light_error,
        errorContainer = IrisColor.iris_blue_light_errorContainer,
        onError = IrisColor.iris_blue_light_onError,
        onErrorContainer = IrisColor.iris_blue_light_onErrorContainer,
        background = IrisColor.iris_blue_light_background,
        onBackground = IrisColor.iris_blue_light_onBackground,
        surface = IrisColor.iris_blue_light_surface,
        onSurface = IrisColor.iris_blue_light_onSurface,
        surfaceVariant = IrisColor.iris_blue_light_surfaceVariant,
        onSurfaceVariant = IrisColor.iris_blue_light_onSurfaceVariant,
        outline = IrisColor.iris_blue_light_outline,
        inverseOnSurface = IrisColor.iris_blue_light_inverseOnSurface,
        inverseSurface = IrisColor.iris_blue_light_inverseSurface,
        inversePrimary = IrisColor.iris_blue_light_inversePrimary,
        surfaceTint = IrisColor.iris_blue_light_surfaceTint,
    )


    override val DarkColors = darkColorScheme(
        primary = IrisColor.iris_blue_dark_primary,
        onPrimary = IrisColor.iris_blue_dark_onPrimary,
        primaryContainer = IrisColor.iris_blue_dark_primaryContainer,
        onPrimaryContainer = IrisColor.iris_blue_dark_onPrimaryContainer,
        secondary = IrisColor.iris_blue_dark_secondary,
        onSecondary = IrisColor.iris_blue_dark_onSecondary,
        secondaryContainer = IrisColor.iris_blue_dark_secondaryContainer,
        onSecondaryContainer = IrisColor.iris_blue_dark_onSecondaryContainer,
        tertiary = IrisColor.iris_blue_dark_tertiary,
        onTertiary = IrisColor.iris_blue_dark_onTertiary,
        tertiaryContainer = IrisColor.iris_blue_dark_tertiaryContainer,
        onTertiaryContainer = IrisColor.iris_blue_dark_onTertiaryContainer,
        error = IrisColor.iris_blue_dark_error,
        errorContainer = IrisColor.iris_blue_dark_errorContainer,
        onError = IrisColor.iris_blue_dark_onError,
        onErrorContainer = IrisColor.iris_blue_dark_onErrorContainer,
        background = IrisColor.iris_blue_dark_background,
        onBackground = IrisColor.iris_blue_dark_onBackground,
        surface = IrisColor.iris_blue_dark_surface,
        onSurface = IrisColor.iris_blue_dark_onSurface,
        surfaceVariant = IrisColor.iris_blue_dark_surfaceVariant,
        onSurfaceVariant = IrisColor.iris_blue_dark_onSurfaceVariant,
        outline = IrisColor.iris_blue_dark_outline,
        inverseOnSurface = IrisColor.iris_blue_dark_inverseOnSurface,
        inverseSurface = IrisColor.iris_blue_dark_inverseSurface,
        inversePrimary = IrisColor.iris_blue_dark_inversePrimary,
        surfaceTint = IrisColor.iris_blue_dark_surfaceTint,
    )

}

object CardamomTipTheme : ChangeableTheme {

    override val LightColors = lightColorScheme(
        primary = CardamomTipColor.cardamom_tip_green_light_primary,
        onPrimary = CardamomTipColor.cardamom_tip_green_light_onPrimary,
        primaryContainer = CardamomTipColor.cardamom_tip_green_light_primaryContainer,
        onPrimaryContainer = CardamomTipColor.cardamom_tip_green_light_onPrimaryContainer,
        secondary = CardamomTipColor.cardamom_tip_green_light_secondary,
        onSecondary = CardamomTipColor.cardamom_tip_green_light_onSecondary,
        secondaryContainer = CardamomTipColor.cardamom_tip_green_light_secondaryContainer,
        onSecondaryContainer = CardamomTipColor.cardamom_tip_green_light_onSecondaryContainer,
        tertiary = CardamomTipColor.cardamom_tip_green_light_tertiary,
        onTertiary = CardamomTipColor.cardamom_tip_green_light_onTertiary,
        tertiaryContainer = CardamomTipColor.cardamom_tip_green_light_tertiaryContainer,
        onTertiaryContainer = CardamomTipColor.cardamom_tip_green_light_onTertiaryContainer,
        error = CardamomTipColor.cardamom_tip_green_light_error,
        errorContainer = CardamomTipColor.cardamom_tip_green_light_errorContainer,
        onError = CardamomTipColor.cardamom_tip_green_light_onError,
        onErrorContainer = CardamomTipColor.cardamom_tip_green_light_onErrorContainer,
        background = CardamomTipColor.cardamom_tip_green_light_background,
        onBackground = CardamomTipColor.cardamom_tip_green_light_onBackground,
        surface = CardamomTipColor.cardamom_tip_green_light_surface,
        onSurface = CardamomTipColor.cardamom_tip_green_light_onSurface,
        surfaceVariant = CardamomTipColor.cardamom_tip_green_light_surfaceVariant,
        onSurfaceVariant = CardamomTipColor.cardamom_tip_green_light_onSurfaceVariant,
        outline = CardamomTipColor.cardamom_tip_green_light_outline,
        inverseOnSurface = CardamomTipColor.cardamom_tip_green_light_inverseOnSurface,
        inverseSurface = CardamomTipColor.cardamom_tip_green_light_inverseSurface,
        inversePrimary = CardamomTipColor.cardamom_tip_green_light_inversePrimary,
        surfaceTint = CardamomTipColor.cardamom_tip_green_light_surfaceTint,
    )


    override val DarkColors = darkColorScheme(
        primary = CardamomTipColor.cardamom_tip_green_dark_primary,
        onPrimary = CardamomTipColor.cardamom_tip_green_dark_onPrimary,
        primaryContainer = CardamomTipColor.cardamom_tip_green_dark_primaryContainer,
        onPrimaryContainer = CardamomTipColor.cardamom_tip_green_dark_onPrimaryContainer,
        secondary = CardamomTipColor.cardamom_tip_green_dark_secondary,
        onSecondary = CardamomTipColor.cardamom_tip_green_dark_onSecondary,
        secondaryContainer = CardamomTipColor.cardamom_tip_green_dark_secondaryContainer,
        onSecondaryContainer = CardamomTipColor.cardamom_tip_green_dark_onSecondaryContainer,
        tertiary = CardamomTipColor.cardamom_tip_green_dark_tertiary,
        onTertiary = CardamomTipColor.cardamom_tip_green_dark_onTertiary,
        tertiaryContainer = CardamomTipColor.cardamom_tip_green_dark_tertiaryContainer,
        onTertiaryContainer = CardamomTipColor.cardamom_tip_green_dark_onTertiaryContainer,
        error = CardamomTipColor.cardamom_tip_green_dark_error,
        errorContainer = CardamomTipColor.cardamom_tip_green_dark_errorContainer,
        onError = CardamomTipColor.cardamom_tip_green_dark_onError,
        onErrorContainer = CardamomTipColor.cardamom_tip_green_dark_onErrorContainer,
        background = CardamomTipColor.cardamom_tip_green_dark_background,
        onBackground = CardamomTipColor.cardamom_tip_green_dark_onBackground,
        surface = CardamomTipColor.cardamom_tip_green_dark_surface,
        onSurface = CardamomTipColor.cardamom_tip_green_dark_onSurface,
        surfaceVariant = CardamomTipColor.cardamom_tip_green_dark_surfaceVariant,
        onSurfaceVariant = CardamomTipColor.cardamom_tip_green_dark_onSurfaceVariant,
        outline = CardamomTipColor.cardamom_tip_green_dark_outline,
        inverseOnSurface = CardamomTipColor.cardamom_tip_green_dark_inverseOnSurface,
        inverseSurface = CardamomTipColor.cardamom_tip_green_dark_inverseSurface,
        inversePrimary = CardamomTipColor.cardamom_tip_green_dark_inversePrimary,
        surfaceTint = CardamomTipColor.cardamom_tip_green_dark_surfaceTint,
    )

}

@Composable
fun ComposeAppTheme(colorScheme: ColorScheme, content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = colorScheme, content = content)
}