package com.example.flightsearch.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.FlightSearchTopAppBar
import com.example.flightsearch.R
import com.example.flightsearch.data.airport.AirPort
import com.example.flightsearch.ui.AppViewModelProvider
import com.example.flightsearch.ui.home.HomeViewModel
import com.example.flightsearch.ui.navigation.NavigationDestination
import kotlinx.coroutines.android.awaitFrame

object ItemSearchDestination: NavigationDestination {
    override val route: String = "item_search"
    override val titleRes: Int = R.string.airport_list

}

lateinit var airPortList: List<AirPort>
lateinit var itemList: List<Pair<String, String>>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemSearchScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var userInput by rememberSaveable { mutableStateOf(homeUiState.userInput) }
    airPortList = viewModel.getAllAirPort().collectAsState(initial = emptyList()).value
    itemList = aTobListMaker(airPortList)

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FlightSearchTopAppBar(
                title = stringResource(ItemSearchDestination.titleRes),
                navigationUp = onNavigateUp,
                canNavigateBack = canNavigateBack,
                scrollBehavior = scrollBehavior
            )
        },
    ) {
        SearchBody(
            itemList = itemList.filter { it.first == userInput },
            contentPadding = it,
            value = userInput,
            result = stringResource(R.string.searching),
            keyboardActions = {
                viewModel.updateUserInput(userInput)
                this.defaultKeyboardAction(ImeAction.Done)
            },
            onValueChange = { userInput = it },
            favoriteButton = {
                viewModel.updateUserInput("")
                navigateBack()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding() + dimensionResource(R.dimen.padding_large))
        )
    }
}

@Composable
fun SearchBody(
    modifier: Modifier = Modifier,
    result: String,
    value: String,
    itemList: List<Pair<String, String>>,
    onValueChange: (String) -> Unit,
    favoriteButton: () -> Unit,
    keyboardActions: (KeyboardActionScope.() -> Unit)?,
    contentPadding: PaddingValues
) {
    var popupControl by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        EditTextField(
            value = value,
            keyboardActions = keyboardActions,
            onValueChange = onValueChange
        )
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = result,
                modifier = Modifier
                    .weight(3f)
                    .padding(start = dimensionResource(R.dimen.padding_large))
            )
            IconButton(
                onClick = { popupControl = if (popupControl) false else true },
                modifier = Modifier.weight(0.5f)
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null)
            }
        }

        if (itemList.isEmpty()) {
            Spacer(modifier = Modifier.height(25.dp))
        } else {
            AirPortList(
                itemList = itemList,
                favoriteButton = favoriteButton,
                contentPadding = contentPadding
            )
        }

        if (popupControl){
            Popup(
                alignment = Alignment.Center,
                offset = IntOffset(0, -100)
            ) {
                PopupContent(
                    onClicked = { popupControl = false }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupContent(
    onClicked: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_large))
            .imePadding(),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
        onClick = onClicked
    ) {
        Text(
            text = stringResource(R.string.help),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        )
    }
}

@Composable
fun EditTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardActions: (KeyboardActionScope.() -> Unit)?
) {
    val focusRequester = remember { FocusRequester() }

    TextField(
        value = value,
        onValueChange = onValueChange,
        keyboardActions = KeyboardActions(onDone = keyboardActions),
        label = { Text(stringResource(R.string.search_hint)) },
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
        trailingIcon = { Icon(painter = painterResource(R.drawable.baseline_mic_24), contentDescription = null) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_extra_large))
            .focusRequester(focusRequester),
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = colorResource(R.color.skyblue),
            focusedContainerColor = colorResource(R.color.skyblue),
            errorContainerColor = colorResource(R.color.skyblue),
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )

    LaunchedEffect(focusRequester) {
        awaitFrame()
        focusRequester.requestFocus()
    }
}

@Composable
fun AirPortList(
    itemList: List<Pair<String, String>>,
    favoriteButton: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    if (itemList.isEmpty()) {
        Text(
            text = stringResource(R.string.empty_airport),
            style = MaterialTheme.typography.bodyLarge
        )
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding
        ) {
            items(items = itemList, key = { it }) { item ->
                AirPortItem(
                    item = item,
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.padding_small)),
                    favoriteButton = favoriteButton
                )
            }
        }
    }
}

@Composable
fun AirPortItem(
    item: Pair<String, String>,
    modifier: Modifier = Modifier,
    favoriteButton: () -> Unit = {}
) {
    Card(
        modifier = modifier.height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .weight(4f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = stringResource(R.string.depart),
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier
                ) {
                    Text(text = item.first, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = airPortDescription(item.first))
                }
                Text(
                    text = stringResource(R.string.arrive),
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier
                ) {
                    Text(text = item.second, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = airPortDescription(item.second))
                }
            }
            IconButton(
                modifier = Modifier
                    .requiredHeight(36.dp)
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

fun aTobListMaker(itemList: List<AirPort>): MutableList<Pair<String, String>> {
    val list = mutableListOf<Pair<String, String>>()
    for (i in itemList.indices) {
        for (j in itemList.indices) {
            val a = itemList[i].iataCode
            val b = itemList[j].iataCode
            if (a != b) {
                list += Pair(itemList[i].iataCode, itemList[j].iataCode)
            }
        }
    }

    return list
}

fun airPortDescription(airPort: String): String {
    return airPortList.find { it.iataCode == airPort }!!.name
}