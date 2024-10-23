package com.filmmash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.filmmash.ui.theme.FilmmashTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilmmashTheme {
                val layoutDirection = LocalLayoutDirection.current
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ){
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
                        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                        val scope = rememberCoroutineScope()
                        val modifier = Modifier
                        val navController = rememberNavController()
                        val pages = Page()
                        ModalNavigationDrawer(
                            modifier = modifier
                                .fillMaxHeight(),
                            drawerContent = {
                                pages.DrawerContent(drawerState, scope, navController)
                            },
                            scrimColor = Color.LightGray.copy(alpha = 0f),
                            drawerState = drawerState,
                            content = {
                                Surface(
                                    modifier = modifier
                                        .fillMaxSize(),
                                    color = MaterialTheme.colorScheme.background
                                ){
                                    Column(){
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = modifier
                                                .fillMaxWidth()
                                                .height(50.dp)
                                                .padding(end = 16.dp)
                                        ){
                                            IconButton(
                                                onClick = { scope.launch{drawerState.open()} },
                                            ) {
                                                Icon(Icons.Filled.Menu, contentDescription = "description", modifier = modifier.fillMaxSize().padding(0.dp).border(width = 0.dp, shape = RectangleShape, color = Color.Transparent))
                                            }
                                            Text(
                                                text = "FilmMash",
                                                fontFamily = FontFamily(Font(R.font.courier_prime)),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 32.sp,
                                                textAlign = TextAlign.Center,
                                                modifier = modifier.clickable { navController.navigate("battle") }
                                            )
                                        }
                                        NavigationEngine(navController = navController)

                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationEngine(navController: NavHostController, apiService:ApiService = ApiService()){

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

        composable("aboutAuthor"){
            pages.AboutAuthor()
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
                pages.Ratings(ratingList)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview(){
    FilmmashTheme{
        val navController = rememberNavController()
        NavigationEngine(navController)
    }
}