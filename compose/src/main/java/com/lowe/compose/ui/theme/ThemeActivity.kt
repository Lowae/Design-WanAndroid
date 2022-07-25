package com.lowe.compose.ui.theme

import android.graphics.Typeface
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.color.DynamicColors
import com.lowe.compose.ComposeAppTheme
import com.lowe.compose.MaterialThemeColor
import com.lowe.compose.R
import com.lowe.compose.base.BaseComposeActivity
import com.lowe.compose.utils.collectAsStateWithLifecycle
import com.lowe.resource.theme.ThemeModel
import com.lowe.resource.theme.ThemePrimaryKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThemeActivity : BaseComposeActivity<ThemeViewModel>() {
    override val viewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeState by viewModel.themeState.collectAsStateWithLifecycle()
            val scope = rememberCoroutineScope()
            val snackBarHostState = remember { SnackbarHostState() }
            ComposeAppTheme(
                MaterialThemeColor(
                    themeState,
                    isSystemInDarkTheme()
                ).getColorScheme()
            ) {
                ThemeScreen(snackBarHostState, scope)
            }
        }
    }

    private fun applyTheme(themeModel: ThemeModel) {
        viewModel.applySelectedTheme(themeModel)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ThemeScreen(snackBarHostState: SnackbarHostState, coroutineScope: CoroutineScope) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.theme_title),
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = FontFamily(Typeface.DEFAULT_BOLD)
                        )
                    },
                    navigationIcon = {
                        BackIcon(onClick = this::finish)
                    }
                )
            }, snackbarHost = {
                SnackbarHost(snackBarHostState)
            }) { paddingValues ->
            ThemeGrid(paddingValues, snackBarHostState, coroutineScope)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ThemeGrid(
        paddingValues: PaddingValues,
        snackBarHostState: SnackbarHostState,
        coroutineScope: CoroutineScope
    ) {
        val context = LocalContext.current
        val themeList by viewModel.themeStateFlow.collectAsStateWithLifecycle()
        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            columns = GridCells.Fixed(2)
        ) {
            items(themeList, key = { it.key }) { theme ->
                Surface(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight()
                        .aspectRatio(ratio = 2 / 1.6f)
                        .padding(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(theme.primaryColor),
                    shadowElevation = 4.dp,
                    onClick = {
                        if (theme.key == ThemePrimaryKey.DYNAMIC.storageKey && DynamicColors.isDynamicColorAvailable().not()) {
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(context.getString(R.string.dynamic_color_tips))
                            }
                        } else applyTheme(theme)
                    }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = theme.key, fontSize = 16.sp, color = Color.White)
                        if (theme.isSelected) {
                            Icon(
                                painter = painterResource(com.lowe.resource.R.drawable.ic_palette_32dp),
                                contentDescription = "",
                                modifier = Modifier.padding(8.dp).size(24.dp)
                                    .align(Alignment.BottomEnd),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun BackIcon(onClick: () -> Unit = {}) {
    TopBarIcon(
        painter = painterResource(id = com.lowe.resource.R.drawable.ic_arrow_back_24dp),
        contentDescription = "",
        onClick = onClick
    )
}

@Composable
fun TopBarIcon(
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tint: Color = MaterialTheme.colorScheme.primary,
    contentDescription: String?,
) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = modifier.size(24.dp),
            painter = painter,
            tint = tint,
            contentDescription = contentDescription
        )
    }
}