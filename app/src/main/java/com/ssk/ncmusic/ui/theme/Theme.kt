package com.ssk.ncmusic.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.*
import com.google.accompanist.insets.ProvideWindowInsets
import com.ssk.ncmusic.ui.theme.color.AppColors
import com.ssk.ncmusic.ui.theme.color.palette.dark.DarkColorPalette
import com.ssk.ncmusic.ui.theme.color.palette.light.*

// 夜间模式
const val THEME_NIGHT = -1
// 默认主题
const val THEME_DEFAULT = 0
// 蓝色主题
const val THEME_BLUE = 1
// 绿色主题
const val THEME_GREEN = 2
// 橙色主题
const val THEME_ORIGIN = 3
// 紫色主题
const val THEME_PURPLE = 4
// 黄色主题
const val THEME_YELLOW = 5

/**
 * 主题状态
 */
val themeTypeState: MutableState<Int> by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    mutableStateOf(getDefaultThemeId())
}

var AppColorsProvider = compositionLocalOf {
    DefaultColorPalette
}

/**
 * 获取当前默认主题
 */
fun getDefaultThemeId(): Int = THEME_DEFAULT

const val TWEEN_DURATION = 200


@Composable
@ReadOnlyComposable
fun isInDarkTheme(): Boolean {
    return isSystemInDarkTheme() || themeTypeState.value == THEME_NIGHT
}

@Composable
fun AppTheme(
    themeType: Int,
    isDark: Boolean = isInDarkTheme(),
    content: @Composable () -> Unit
) {

    val targetColors = if (isDark) DarkColorPalette else {
        when (themeType) {
            THEME_BLUE -> BlueColorPalette
            THEME_GREEN -> GreenColorPalette
            THEME_ORIGIN -> OriginColorPalette
            THEME_PURPLE -> PurpleColorPalette
            THEME_YELLOW -> YellowColorPalette
            else -> DefaultColorPalette
        }
    }

    val statusBarColor = animateColorAsState(targetColors.statusBarColor, TweenSpec(TWEEN_DURATION))
    val pure = animateColorAsState(targetColors.pure, TweenSpec(TWEEN_DURATION))
    val primary = animateColorAsState(targetColors.primary, TweenSpec(TWEEN_DURATION))
    val primaryVariant = animateColorAsState(targetColors.primaryVariant, TweenSpec(TWEEN_DURATION))
    val secondary = animateColorAsState(targetColors.secondary, TweenSpec(TWEEN_DURATION))
    val background = animateColorAsState(targetColors.background, TweenSpec(TWEEN_DURATION))
    val firstText = animateColorAsState(targetColors.firstText, TweenSpec(TWEEN_DURATION))
    val secondText = animateColorAsState(targetColors.secondText, TweenSpec(TWEEN_DURATION))
    val thirdText = animateColorAsState(targetColors.thirdText, TweenSpec(TWEEN_DURATION))
    val firstIcon = animateColorAsState(targetColors.firstIcon, TweenSpec(TWEEN_DURATION))
    val secondIcon = animateColorAsState(targetColors.secondIcon, TweenSpec(TWEEN_DURATION))
    val thirdIcon = animateColorAsState(targetColors.thirdIcon, TweenSpec(TWEEN_DURATION))
    val appBarBackground = animateColorAsState(targetColors.appBarBackground, TweenSpec(TWEEN_DURATION))
    val appBarContent = animateColorAsState(targetColors.appBarContent, TweenSpec(TWEEN_DURATION))
    val card = animateColorAsState(targetColors.card, TweenSpec(TWEEN_DURATION))
    val bottomMusicPlayBarBackground = animateColorAsState(targetColors.bottomMusicPlayBarBackground, TweenSpec(TWEEN_DURATION))
    val divider = animateColorAsState(targetColors.divider, TweenSpec(TWEEN_DURATION))

    val appColors = AppColors(
        statusBar = statusBarColor.value,
        pure = pure.value,
        primary = primary.value,
        primaryVariant = primaryVariant.value,
        secondary = secondary.value,
        background = background.value,
        firstText = firstText.value,
        secondText = secondText.value,
        thirdText = thirdText.value,
        firstIcon = firstIcon.value,
        secondIcon = secondIcon.value,
        thirdIcon = thirdIcon.value,
        appBarBackground = appBarBackground.value,
        appBarContent = appBarContent.value,
        card = card.value,
        bottomMusicPlayBarBackground = bottomMusicPlayBarBackground.value,
        divider = divider.value
    )

    ProvideWindowInsets {
        CompositionLocalProvider(AppColorsProvider provides appColors) {
            MaterialTheme(
                shapes = shapes
            ) {
                ProvideWindowInsets(content = content)
            }
        }
    }
}

