package com.translate.speech.to.text.dictionary.instant.voice.translatoRes.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_fire
import downloaderkmpproductionapp.shared.generated.resources.ic_home_nav
import downloaderkmpproductionapp.shared.generated.resources.ic_more_nav
import downloaderkmpproductionapp.shared.generated.resources.ic_player_nav
import downloaderkmpproductionapp.shared.generated.resources.ic_trending_nav
import downloaderkmpproductionapp.shared.generated.resources.nunito_medium
import downloaderkmpproductionapp.shared.generated.resources.player
import downloaderkmpproductionapp.shared.generated.resources.trending
import org.example.project.domain.models.appmodels.BottomNavScreens
import org.example.project.navigation.NavRoutes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun BottomNav(
    navController: NavController
) {

    val tabs = listOf(

        BottomNavScreens(
            NavRoutes.DownloaderHomeScreen.route,
            "Home",
            Res.drawable.ic_home_nav,

        ),

        BottomNavScreens(
            NavRoutes.TrendingHomeScreen.route,
            stringResource(Res.string.trending),
            Res.drawable.ic_trending_nav
        ),

        BottomNavScreens(
            NavRoutes.PlayerHomeScreen.route,
            stringResource(Res.string.player),
            Res.drawable.ic_player_nav
        ),

        BottomNavScreens(
            NavRoutes.MoreScreen.route,
            "More",
            Res.drawable.ic_more_nav
        ),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(137.dp)
            .navigationBarsPadding()
    ) {

        // NAVIGATION BAR BACKGROUND
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(72.dp)
                .background(
                    color = Color.White,

                )
        ) {

            // TOP DIVIDER
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFEAEAEA))
                    .align(Alignment.TopCenter)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 14.dp,
                        start = 10.dp,
                        end = 10.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                tabs.forEachIndexed { index, tab ->

                    val selected = currentRoute == tab.route

                    // SPACE FOR CENTER BUTTON
                    if (index == 2) {

                        Box(
                            modifier = Modifier.width(74.dp)
                        )

                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            ) {

                                if (currentRoute != tab.route) {

                                    navController.navigate(tab.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            imageVector = vectorResource(tab.icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(
                                if (selected)
                                    Color(0xFF003BEE)
                                else
                                    Color(0xFF8B96A6)
                            )
                        )

                        AppText(
                            text = tab.name,
                            color = if (selected)
                                Color(0xFF003BEE)
                            else
                                Color(0xFF8B96A6),
                            fontSize = 14.sp,
                            font = Res.font.nunito_medium,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }
        }

        // FLOATING CENTER BUTTON
        FloatingFireButton(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 4.dp),
            onClick = {

                navController.navigate(
                    NavRoutes.ReelsHomeScreen.route
                ) {
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun FloatingFireButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .size(75.dp)
            .shadow(
                elevation = 5.dp,
                shape = CircleShape,
            )
            .clip(CircleShape)

            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2761FB),
                        Color(0xFF2246F7),
                        Color(0xFF0C3AED),
                    )
                )
            )
            .border(
                width = 2.dp,
                color = Color.White.copy(
                    alpha = 0.47f
                ),
                shape = CircleShape
            )
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(Res.drawable.ic_fire),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Preview
@Composable
fun BottomNavPrev() {

        BottomNav(rememberNavController())


}