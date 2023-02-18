package com.michau.countries.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.michau.countries.ui.theme.BackgroundColor
import com.michau.countries.util.UiConstants
import com.michau.countries.util.UiEvent

@Composable
fun CategoryScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
){

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(UiConstants.categories){ category ->
            CategoryItem(
                category = category,
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 40.dp)
                    .clickable {
                        viewModel.onEvent(category.onClick)
                    }
            )
        }
    }

}