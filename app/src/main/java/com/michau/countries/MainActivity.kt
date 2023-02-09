package com.michau.countries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.michau.countries.ui.category.CategoryScreen
import com.michau.countries.ui.country.CountrySearch
import com.michau.countries.ui.country_details.CountryDetailScreen
import com.michau.countries.ui.level.ChooseLevelScreen
import com.michau.countries.ui.level.Levels
import com.michau.countries.ui.quiz.QuizScreen
import com.michau.countries.ui.theme.OrdersYTTheme
import com.michau.countries.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrdersYTTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.CATEGORY
                ){
                    composable(Routes.CATEGORY){
                        CategoryScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(Routes.COUNTRIES_LIST){
                        CountrySearch(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(Routes.QUIZ_LEVEL){
                        ChooseLevelScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = Routes.QUIZ_GAME + "?level={level}",
                        arguments = listOf(
                            navArgument(name = "level") {
                                type = NavType.EnumType(Levels::class.java)
                                defaultValue = Levels.Hardcore
                            }
                        )
                    ) {
                        QuizScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = Routes.COUNTRY_DETAIL + "?countryName={countryName}",
                        arguments = listOf(
                            navArgument(name = "countryName") {
                                type = NavType.StringType
                                defaultValue = ""
                            }
                        )
                    ) {
                        CountryDetailScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}
