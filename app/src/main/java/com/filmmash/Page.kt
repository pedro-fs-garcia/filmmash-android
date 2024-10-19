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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.filmmash.ui.theme.FilmmashTheme

class Page {

    @Composable
    public fun Home(navController: NavController, modifier: Modifier = Modifier){
        Column (
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Button(
                onClick = {navController.navigate("battle")},
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ){
                Text(
                    text = "Choose the best movie",
//                    fontFamily = FontFamily(Font(R.font.courier_prime))
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
//                    fontFamily = FontFamily(Font(R.font.courier_prime))
                )
            }
        }
    }

    @Composable
    public fun Battle(arena: Arena, navController:NavController, modifier:Modifier = Modifier){
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Column() {
                    Text(
                        text = "Yea, it's on..."
                    )
                    Text(
                        text = "...I like the idea of comparing two films together. It gives the whole thing a very \"Turing\" feel since people's ratings of the films will be more implicit than choosing a number to represent each film's quality like they do on IMDB.",
                        textAlign = TextAlign.Justify
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
                        modifier = modifier.clickable { navController.navigate("about") }
                    )

                    Text(
                        text = "See all Ratings",
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
                        text = "FILMMASH",
                        color = Color.White
                    )
                }
                Text(
                    text = "Which is better? Click to choose...",
                    textAlign = TextAlign.Center
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
                            ApiService().postNewWinner(jsonWinner);
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
                        text = arena.movie1.name
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
                            ApiService().postNewWinner(jsonWinner);
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
                        text = arena.movie2.name
                    )
                }
            }
            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = "Reload",
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("battle") }
            )
        }
    }

    @Composable
    public fun Ratings(ratingList: RatingList, navController: NavController, modifier: Modifier = Modifier){
        val listOfMovies = ratingList.movieList
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Movies listed by rating",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = modifier.height(10.dp))
            Footer(navController)
            LazyColumn(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                items(listOfMovies) { movie ->
                    MovieCard(movie)
                }
            }
        }
    }

    @Composable
    public fun About(navController: NavController, modifier: Modifier = Modifier){
        val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(color = MaterialTheme.colorScheme.background)
        ){
            Text(
                text = stringResource(R.string.filmmash),
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
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.github_repo)))
                    context.startActivity(intent)
                }
            ){
                Text(text = "Make a bitcoin donation")
            }
        }
    }

    @Composable
    fun EloScore() {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier
            .padding(16.dp)
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
    private fun Footer(navController: NavController, modifier : Modifier = Modifier){
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Go to filmmash",
                modifier = modifier.clickable { navController.navigate("battle") }
            )

            Spacer(modifier = modifier.height(10.dp))

            Text(
                text = "How the ratings are calculated",
                modifier = modifier.clickable { navController.navigate("ratings") }
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

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview(){
        FilmmashTheme{
            val navController = rememberNavController()
            EloScore()
        //Ratings(navController)
        }
    }
}