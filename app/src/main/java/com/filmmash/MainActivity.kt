package com.filmmash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.filmmash.ui.theme.FilmmashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilmmashTheme {
                val layoutDirection = LocalLayoutDirection.current
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = WindowInsets.safeDrawing.asPaddingValues().calculateStartPadding(layoutDirection),
                            end = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(layoutDirection),
                        )
                        .statusBarsPadding()
                ){
                    NavigationEngine()
                }
            }
        }
    }
}

@Composable
fun NavigationEngine(apiService:ApiService = ApiService()){
    val navController = rememberNavController()
    val pages = Page()
    val arenaInfo = remember { mutableStateOf<String?>(null) }
    val ratingInfo = remember { mutableStateOf<String?>(null) }
    NavHost(navController = navController, startDestination = "home"){
        composable ("home"){
            pages.Home(navController)
        }
        composable("about"){
            pages.About(navController)
        }

        composable("elo"){
            pages.EloScore()
        }

        composable("battle"){
            LaunchedEffect(Unit) {
                apiService.getNewArena { arena ->
                    arenaInfo.value = arena
                }
            }
            arenaInfo.value?.let { arenaInfo ->
                val arena = Arena()
                arena.jsonToArena(arenaInfo)
                pages.Battle(arena, navController)
            }
        }
        composable("ratings"){
            LaunchedEffect(Unit) {
                apiService.getAllRatings { ratings ->
                    ratingInfo.value = ratings
                }
            }
            ratingInfo.value?.let { ratingInfo ->
                val ratingList = RatingList()
                ratingList.jsonToRatingList(ratingInfo)
                pages.Ratings(ratingList, navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview(){
    FilmmashTheme{
        NavigationEngine()
    }
}