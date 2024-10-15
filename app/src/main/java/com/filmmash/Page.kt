package com.filmmash

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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
        Column (
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

            MovieArena(arena = arena,navController)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
            ){
                Text(
                    text = "See top movies",
                    modifier = modifier.clickable { navController.navigate("ratings") }
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
                            arena.winner = 1
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
                            arena.winner = 2
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
    public fun Ratings(navController: NavController, modifier: Modifier = Modifier){
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(color = MaterialTheme.colorScheme.background)
        ){
            MovieCard(Movie())
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
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = modifier.fillMaxHeight()
            )
            Spacer(modifier = modifier.width(15.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxHeight()
            ){
                Text (
                    text = "Título do filme",
                    fontSize = 20.sp
                )
                Text (
                    text = "Diretor"
                )
                Text (
                    text = "Elo Score"
                )
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview(){
        FilmmashTheme{
            val navController = rememberNavController()
            val movie1 = Movie(100,"Inception", "Christopher Nolan", 1500, "https://raw.githubusercontent.com/pedro-fs-garcia/grandes_filmes/refs/heads/main/app/static/images/films/memento.jpg")
            val movie2 = Movie(200,"The Matrix", "Wachowski Sisters", 1600, "https://raw.githubusercontent.com/pedro-fs-garcia/grandes_filmes/refs/heads/main/app/static/images/films/the_matrix.jpg")
            val arena = Arena(movie1, movie2)
            MovieArena(arena = arena,navController)
        //Ratings(navController)
        }
    }
}