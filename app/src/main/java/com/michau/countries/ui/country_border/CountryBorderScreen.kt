package com.michau.countries.ui.country_border

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.michau.countries.ui.level.LevelButton
import com.michau.countries.ui.result.ResultScreenEvent
import com.michau.countries.ui.theme.BackgroundColor
import com.michau.countries.util.UiEvent

@Composable
fun CountryBorderScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: CountryBorderViewModel = hiltViewModel()
){

    var currentCountry by remember {
        mutableStateOf("")
    }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }


    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect {event ->
            when(event) {
                is UiEvent.ShowToast ->
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is UiEvent.Navigate ->
                    onNavigate(event)
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        
        AsyncImage(
            modifier = Modifier.padding(top = 40.dp),
            model = "file:///android_asset/countries_outline/all/" +
                    "${viewModel.currentCountry?.alpha2Code.toString().lowercase()}/512.png",
            contentDescription = "",
            colorFilter = ColorFilter.tint(color = Color.White)
        )
        
        Spacer(modifier = Modifier.height(60.dp))

        repeat(viewModel.triesNumber){
            DetailsTile(
                countryName = viewModel.guessState[it].countryName,
                distance =  viewModel.guessState[it].distance,
                direction =  viewModel.guessState[it].direction,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(vertical = 2.dp, horizontal = 12.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(viewModel.guessState[it].color),
            )
        }

        repeat(viewModel.emptyTileNumber){
            EmptyGuessTile(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(vertical = 2.dp, horizontal = 12.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color.White),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Category Field
        Column(
            modifier = Modifier
                .padding(vertical = 30.dp, horizontal = 12.dp)
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        expanded = false
                    }
                )
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heightTextFields)
                            .border(
                                width = 1.8.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            },
                        value = currentCountry,
                        onValueChange = {
                            currentCountry = it
                            expanded = true
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.White
                        ),
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = Icons.Rounded.KeyboardArrowDown,
                                    contentDescription = "arrow",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                }

                AnimatedVisibility(visible = expanded) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .width(textFieldSize.width.dp),
                        elevation = 15.dp,
                        shape = RoundedCornerShape(10.dp)
                    ) {

                        LazyColumn(
                            modifier = Modifier.heightIn(max = 150.dp),
                        ) {
                            if (viewModel.countries.isNotEmpty()) {
                                items(
                                    viewModel.countries.filter {
                                        it.name.lowercase()
                                            .contains(currentCountry.lowercase()) || it.name.lowercase()
                                            .contains("others")
                                    }.sortedBy {
                                            it.name
                                        }
                                ) {
                                    SearchListItem(title = it.name) { title ->
                                        currentCountry = title
                                        expanded = false
                                    }
                                }
                            } else {
                                items(
                                    viewModel.countries.toList().sortedBy {
                                        it.name
                                    }
                                ) {
                                    SearchListItem(title = it.name) { title ->
                                        currentCountry = title
                                        expanded = false
                                    }
                                }
                            }

                        }

                    }
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                LevelButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields),
                    color = Color(0xFF2E7D32),
                    text = viewModel.buttonState.text,
                    onClick = {
                        if(viewModel.isRoundFinished)
                            viewModel.onEvent(BorderScreenEvent.OnNextRoundClick)
                        else
                            viewModel.onEvent(BorderScreenEvent.OnAnswerClick(currentCountry))
                    }
                )
            }

        }
    }
}

@Composable
fun DetailsTile(
    countryName: String,
    distance: Int,
    direction: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${countryName.substringBefore("(")} ",
            textAlign = TextAlign.Left
        )
        Text(
            text = "$distance km ",
            textAlign = TextAlign.Center
        )
        Text(
            text = "$direction ",
            textAlign = TextAlign.Right
        )
    }
}

@Composable
fun EmptyGuessTile(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
    }
}