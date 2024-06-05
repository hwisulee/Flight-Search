package com.example.flightsearch.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.FlightSearchTopAppBar
import com.example.flightsearch.R
import com.example.flightsearch.data.favorite.Favorite
import com.example.flightsearch.ui.AppViewModelProvider
import com.example.flightsearch.ui.navigation.NavigationDestination
import com.example.flightsearch.ui.theme.FlightSearchTheme

object HomeDestination: NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemSearch: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var userInput by rememberSaveable { mutableStateOf("") }
    val itemList by viewModel.getAllFavorite().collectAsState(emptyList())

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FlightSearchTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },

    ) {
        HomeBody(
            itemList = itemList,
            contentPadding = it,
            onClicked = { navigateToItemSearch() },
            onValueChange = { userInput = it },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding() + dimensionResource(R.dimen.padding_large))
        )
    }
}

@Composable
fun HomeBody(
    modifier: Modifier = Modifier,
    itemList: List<Favorite>,
    onValueChange: (String) -> Unit,
    onClicked: () -> Unit,
    contentPadding: PaddingValues
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        EditTextField(
            onClicked = onClicked,
            onValueChange = onValueChange
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.favorite_routes),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(dimensionResource(R.dimen.padding_large))
        )
        Spacer(modifier = Modifier.height(10.dp))
        FavoriteList(
            itemList = itemList,
            contentPadding = contentPadding
        )
    }
}

@Composable
fun EditTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onClicked: () -> Unit
) {
    Button(
        onClick = onClicked,
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        TextField(
            enabled = false,
            value = "",
            onValueChange = onValueChange,
            label = { Text(stringResource(R.string.search_hint)) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
            trailingIcon = { Icon(painter = painterResource(R.drawable.baseline_mic_24), contentDescription = null) },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_extra_large)),
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.colors(
                disabledLeadingIconColor = Color.Black.copy(alpha = 0.6f),
                disabledTrailingIconColor = Color.Black.copy(alpha = 0.6f),
                disabledLabelColor = Color.Black.copy(alpha = 0.6f),
                disabledContainerColor = colorResource(R.color.skyblue),
                unfocusedContainerColor = colorResource(R.color.skyblue),
                focusedContainerColor = colorResource(R.color.skyblue),
                errorContainerColor = colorResource(R.color.skyblue),
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun FavoriteList(
    itemList: List<Favorite>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    if (itemList.isEmpty()) {
        Text(
            text = stringResource(R.string.empty_favorite),
            style = MaterialTheme.typography.bodyLarge
        )
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding
        ) {
            items(items = itemList, key = { it.id }) { item ->
                FavoriteItem(
                    item = item,
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.padding_small)),
                    favoriteButton = {}
                )
            }
        }
    }
}

@Composable
fun FavoriteItem(
    item: Favorite,
    modifier: Modifier = Modifier,
    favoriteButton: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_large))
                    .weight(4f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = stringResource(R.string.depart),
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = item.departureCode, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = item.departureCode)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(R.string.arrive),
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = item.destinationCode, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = item.destinationCode)
                }
            }
            IconButton(
                modifier = Modifier
                    .requiredHeight(48.dp)
                    .weight(1f),
                onClick = favoriteButton
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.Star,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun ItemListPreView() {
    FlightSearchTheme {
        Surface {
            val itemList = listOf(
                Favorite(0, "FCO", "SVO"),
                Favorite(1, "FCO", "MUC"),
                Favorite(2, "FCO", "LIS"),
            )

            FavoriteList(itemList = itemList, contentPadding = PaddingValues(0.dp))
        }
    }
}

@Preview
@Composable
fun ItemPreView() {
    FlightSearchTheme {
        Surface {
            FavoriteItem(item = Favorite(0, "FCO", "SVO"))
        }
    }
}