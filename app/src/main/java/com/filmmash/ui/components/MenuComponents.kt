package com.filmmash.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MenuComponents {

    @Composable
    fun DrawerContent(drawerState: DrawerState, scope: CoroutineScope, navController: NavController, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .background(shape = RectangleShape, color= MaterialTheme.colorScheme.background)
                .fillMaxHeight()
                .width(200.dp)
        ) {
            IconButton(
                onClick = {scope.launch { drawerState.close() }},
            ) {
                Icon(Icons.Filled.Menu, contentDescription = "menu", modifier = modifier.fillMaxSize().padding(0.dp))
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("home")
                        scope.launch { drawerState.close() }
                    }
            ){
                Text(
                    text = "Home",
                    modifier = modifier
                        .padding(16.dp)
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("battle")
                        scope.launch { drawerState.close() }
                    }
            ){
                Text(
                    text = "Filmmash",
                    modifier = modifier
                        .padding(16.dp)
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("ratings")
                        scope.launch { drawerState.close() }
                    }
            ){
                Text(
                    text = "Ratings",
                    modifier = modifier
                        .padding(16.dp)
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("about")
                        scope.launch { drawerState.close() }
                    }
            ){
                Text(
                    text = "About Us",
                    modifier = modifier
                        .padding(16.dp)
                )
            }
        }
    }
}