package com.filmmash

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.filmmash.ui.theme.FilmmashTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Page {

    @Composable
    fun Home(navController: NavController, modifier: Modifier = Modifier){
        Column (
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.leonardo_phoenix_design_a_minimalist_modern_logo_for_a_mobile_2),
                contentDescription = "logo",
                modifier = modifier
            )
            Button(
                onClick = {navController.navigate("battle")},
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ){
                Text(
                    text = "Get started",
                    fontFamily = FontFamily(Font(R.font.courier_prime)),
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = {navController.navigate("ratings")},
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ){
                Text(
                    text = "Check ratings",
                    fontFamily = FontFamily(Font(R.font.courier_prime)),
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = {navController.navigate("about")},
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ){
                Text(
                    text = "about us",
                    fontFamily = FontFamily(Font(R.font.courier_prime)),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Composable
    fun Battle(arena: Arena, navController:NavController, modifier:Modifier = Modifier){
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Column {
                    Text(
                        text = "Yea, it's on...",
                        fontFamily = FontFamily(Font(R.font.courier_prime)),
                    )
                    Text(
                        text = "...I like the idea of comparing two films together. It gives the whole thing a very \"Turing\" feel since people's ratings of the films will be more implicit than choosing a number to represent each film's quality like they do on IMDB.",
                        textAlign = TextAlign.Justify,
                        fontFamily = FontFamily(Font(R.font.courier_prime)),
                    )
                }

                MovieArena(arena = arena, navController)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "About us",
                        fontFamily = FontFamily(Font(R.font.courier_prime)),
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.clickable { navController.navigate("about") }
                    )

                    Text(
                        text = "See all Ratings",
                        fontFamily = FontFamily(Font(R.font.courier_prime)),
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.clickable { navController.navigate("ratings") }
                    )
                }
            }
    }

    @Composable
    private fun MovieArena(arena: Arena, navController: NavController, modifier: Modifier = Modifier){
        Column {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .background(color = Color.DarkGray)
                ) {
                    Text(
                        text = "FilmMash",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.courier_prime)),
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Which is better? Click to choose...",
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.courier_prime))
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .clickable(onClick = {
                            arena.winner = arena.movie1.film_id
                            val jsonWinner = arena.buildJsonWinner()
                            ApiService().postNewWinner(jsonWinner)
                            navController.navigate("battle")
                        })
                        .weight(1f)
                ) {
                    AsyncImage(
                        model = arena.movie1.poster,
                        contentDescription = arena.movie1.name,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .height(254.dp)
                            .width(173.dp)
                    )
                    Text(
                        text = arena.movie1.name,
                        fontFamily = FontFamily(Font(R.font.courier_prime))
                    )
                }
                Text(
                    text = "vs",
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .clickable(onClick = {
                            arena.winner = arena.movie2.film_id
                            val jsonWinner = arena.buildJsonWinner()
                            ApiService().postNewWinner(jsonWinner)
                            navController.navigate("battle")
                        })
                        .weight(1f)
                ) {
                    AsyncImage(
                        model = arena.movie2.poster,
                        contentDescription = arena.movie2.name,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .height(254.dp)
                            .width(173.dp)
                    )
                    Text(
                        text = arena.movie2.name,
                        fontFamily = FontFamily(Font(R.font.courier_prime)),
                    )
                }
            }
            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = "Reload",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.courier_prime)),
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("battle") }
            )
        }
    }

    @Composable
    fun Ratings(ratingList: RatingList, modifier: Modifier = Modifier){
        val listOfMovies = ratingList.movieList
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Movies listed by rating",
                fontFamily = FontFamily(Font(R.font.courier_prime)),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = modifier.height(10.dp))
            LazyColumn(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(listOfMovies) { movie ->
                    MovieCard(movie)
                }
            }
        }
    }

    @Composable
    fun About(navController: NavController, modifier: Modifier = Modifier){
        val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ){
            Text(
                text = stringResource(R.string.filmmash),
                fontFamily = FontFamily(Font(R.font.courier_prime)),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = stringResource(R.string.about_1),
                fontSize = 16.sp
            )
            Text(
                text = stringResource(R.string.about_2),
            )
            Text(
                text = stringResource(R.string.see_more_details_on_the_elo_rating_system),
                textDecoration = TextDecoration.Underline,
                modifier = modifier.clickable { navController.navigate("elo") }
            )
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.github_repo)))
                    context.startActivity(intent)
                }
            ){
                Text(text = "Contribute on github")
            }
            Button(
                onClick = {navController.navigate("aboutAuthor")}
            ){
                Text(text = "About the author")
            }
        }
    }

    @Composable
    fun EloScore() {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 50.dp)
            .verticalScroll(scrollState)
        ) {
            Text(
                text = "Elo Rating System",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "The Elo rating system is a method for calculating the relative skill levels of players in two-player games, but it can also be adapted for various ranking scenarios, such as our movie ranking app. Here’s a breakdown of how the Elo system works:"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Basics of the Elo System",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            Text(
                text = "1. Initial Ratings:\n" +
                        "   - Every player (or film, in your case) starts with a predefined rating. In our case, it was set to 1400.\n"
            )

            Text(
                text = "2. Rating Calculation:\n" +
                        "   - The outcome of a match (or vote) between two players (or films) affects their ratings. The formula to calculate the new ratings is as follows:\n" +
                        "\n" +
                        "   R_a' = R_a + K × (S_a - E_a)\n" +
                        "   R_b' = R_b + K × (S_b - E_b)\n" +
                        "\n" +
                        "   Where:\n" +
                        "   - R_a and R_b are the current ratings of player A and player B, respectively.\n" +
                        "   - R_a' and R_b' are the new ratings after the match.\n" +
                        "   - K is a constant that determines how much the ratings can change after a match (often set between 10 and 40).\n" +
                        "   - S_a and S_b are the actual scores of players A and B after the match (1 for a win, 0.5 for a draw, and 0 for a loss).\n" +
                        "   - E_a and E_b are the expected scores, calculated based on the current ratings."
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "3. Calculating Expected Scores:\n" +
                        "   - The expected score for each player can be calculated using the following formula:\n" +
                        "\n" +
                        "   E_a = 1 / (1 + 10^{(R_b - R_a) / 400})\n" +
                        "   E_b = 1 / (1 + 10^{(R_a - R_b) / 400})\n" +
                        "\n" +
                        "   This formula estimates the probability of a player winning based on their current ratings."
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Example Scenario",
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "1. Initial Ratings:\n" +
                        "   - Film A: 1,600\n" +
                        "   - Film B: 1,400\n"
            )

            Text(
                text = "2. Vote Outcome:\n" +
                        "   - Film A wins against Film B."
            )

            Text(
                text = "3. Calculating Expected Scores:\n" +
                        "   - E_A = 1 / (1 + 10^{(1400 - 1600) / 400}) = 0.76\n" +
                        "   - E_B = 1 / (1 + 10^{(1600 - 1400) / 400}) = 0.24"
            )

            Text(
                text = "4. Updating Ratings:\n" +
                        "   - If K = 32:\n" +
                        "   - For Film A (winner):\n" +
                        "     R_a' = 1600 + 32 × (1 - 0.76) ≈ 1608\n" +
                        "   - For Film B (loser):\n" +
                        "     R_b' = 1400 + 32 × (0 - 0.24) ≈ 1392"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Key Points",
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "- Dynamic Ratings: Ratings change based on performance against other films, allowing for a more accurate representation of a film's popularity over time.\n" +
                        "- Lesser-Known Films: The Elo system allows lesser-known films to gain ratings quickly if they perform well against more popular films, promoting diversity in rankings.\n" +
                        "- Simplicity: While the formulas may seem complex, the system is relatively straightforward to implement and can be adapted to various contexts beyond gaming."
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "By implementing the Elo rating system in your Filmmash app, you can create a dynamic ranking mechanism that reflects the preferences and choices of your users in an engaging way!"
            )
        }
    }

    @Composable
    fun AboutAuthor(modifier: Modifier = Modifier){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "author"
            )
            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = "Pedro Garcia",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
            Text(
                text = ""
            )
        }
    }

    @Composable
    private fun MovieCard(movie: Movie, modifier:Modifier = Modifier){
        Row (
            modifier = modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(color = Color.LightGray)
                .padding(15.dp)
        ){
            AsyncImage(
                model = movie.poster,
                contentDescription = movie.name,
                contentScale = ContentScale.FillHeight,
                modifier = modifier.fillMaxHeight()
            )
            Spacer(modifier = modifier.width(15.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxHeight()
            ){
                Text (
                    text = movie.name,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text (
                    text = movie.director,
                    color = Color.Black
                )
                Text (
                    text = movie.score.toString(),
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = modifier.height(16.dp))
    }

    @Composable
    fun DrawerContent(drawerState: DrawerState, scope: CoroutineScope, navController: NavController, modifier:Modifier = Modifier) {
        Column(
            modifier = modifier
                .background(shape = RectangleShape, color=MaterialTheme.colorScheme.onBackground)
                .fillMaxHeight()
                .width(200.dp)
        ) {
            IconButton(
                onClick = {scope.launch { drawerState.close() }},
            ) {
                Icon(Icons.Filled.Menu, tint = Color.White, contentDescription = "menu", modifier = modifier.fillMaxSize().padding(0.dp))
            }
            Text(
                text = "Home",
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        navController.navigate("home")
                        scope.launch { drawerState.close() }
                    }
            )
            Text(
                text = "Filmmash",
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        navController.navigate("battle")
                        scope.launch { drawerState.close() }
                    }
            )
            Text(
                text = "Ratings",
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        navController.navigate("ratings")
                        scope.launch{drawerState.close()}
                    }

            )
            Text(
                text = "About us",
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        navController.navigate("about")
                        scope.launch { drawerState.close() }
                    }
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview(){
        FilmmashTheme{
            EloScore()
        }
    }
}