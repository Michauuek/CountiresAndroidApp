package com.michau.countries.ui.country_border

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.data.remote.CountryRepository
import com.michau.countries.domain.mapper.toCountryModel
import com.michau.countries.domain.model.CountryModel
import com.michau.countries.util.Constants.AVERAGE_RADIUS_OF_EARTH_KM
import com.michau.countries.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.*

@HiltViewModel
class CountryBorderViewModel @Inject constructor(
    private val apiRepository: CountryRepository
): ViewModel(){

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var currentCountry: CountryModel? by mutableStateOf(null)
        private set

    var countries: MutableList<CountryModel> by mutableStateOf(mutableListOf())
        private set

    var triesNumber by mutableStateOf(0)
        private set

    var emptyTileNumber by mutableStateOf(3)
        private set

    var isRoundFinished by mutableStateOf(false)
        private set

    var buttonState by mutableStateOf(ButtonState(text = "Guess"))
        private set

    var guessState: MutableList<GuessState> by mutableStateOf(mutableListOf())
        private set

    init {
        viewModelScope.launch {
            countries = (apiRepository
                .getAllCountries()
                .data
                ?.map {
                it.toCountryModel() } ?: emptyList()).toMutableList()

            countries.sortByDescending { countryModel ->
                countryModel.population
            }

            countries.toList().take(100).toMutableList().shuffle()
            currentCountry = countries.random()

            Log.d("TAG", currentCountry.toString())
        }
    }
    fun onEvent(event: BorderScreenEvent){
        when(event) {
            is BorderScreenEvent.OnAnswerClick -> {

                viewModelScope.launch {

                    if(event.country.isEmpty()){
                        sendUiEvent(
                            UiEvent.ShowToast("Country cannot be empty")
                        )
                        return@launch
                    }

                    val matchingCountries = apiRepository
                        .getDetailsByCountryName(event.country)
                        .data

                    if(matchingCountries == null || matchingCountries.isEmpty()){
                        sendUiEvent(
                            UiEvent.ShowToast("Select country from the list")
                        )
                        return@launch
                    }

                    val guessedCountry = matchingCountries
                        .first()
                        .toCountryModel()

                    //guessedCountryName = guessedCountry.name
                    var distance: Int? = null
                    var angle: Double? = null

                    try {
                        distance = calculateDistanceInKilometer(
                            currentCountry?.latlng?.component1()!!,
                            currentCountry?.latlng?.component2()!!,
                            guessedCountry.latlng.component1(),
                            guessedCountry.latlng.component2()
                        )
                        angle = angleFromCoordinate(
                            currentCountry?.latlng?.component1()!!,
                            currentCountry?.latlng?.component2()!!,
                            guessedCountry.latlng.component1(),
                            guessedCountry.latlng.component2()
                        )
                    } catch (e: IndexOutOfBoundsException) {
                        e.printStackTrace()
                    }

                    val direction = calculateDirection(angle ?: -1.0)

                    val guess = GuessState(
                        countryName = guessedCountry.name,
                        distance = distance ?: -1,
                        direction = direction
                    )

                    guessState.add(guess)

                    if(triesNumber < 3) {
                        triesNumber++
                        emptyTileNumber--
                    }

                    if (distance == 0) {
                        sendUiEvent(UiEvent.ShowToast("You've guessed correctly"))
                        guessState[triesNumber -1] = guessState[triesNumber -1].copy(
                            color = Color.Green
                        )
                        buttonState = buttonState.copy(
                            text = "Next"
                        )
                        isRoundFinished = true
                    } else {
                        sendUiEvent(
                            UiEvent.ShowToast("Wrong answer distance: " +
                                    "$distance angle: $direction"
                            )
                        )
                    }
                }
            }
            BorderScreenEvent.OnNextRoundClick -> {
                sendUiEvent(
                    UiEvent.ShowToast("Next")
                )
            }
        }
    }

    /**
     *  Function to calculate distance between 2 locations
     *  using latitude and longitude
     *
     *  Math formula
     *  =acos(sin(lat1)*sin(lat2)+cos(lat1)*cos(lat2)*cos(lon2-lon1))*6371
     *  (6371 is Earth radius in km.)
     */
    private fun calculateDistanceInKilometer(
        userLat: Double, userLng: Double,
        venueLat: Double, venueLng: Double
    ): Int {
        val latDistance = Math.toRadians(userLat - venueLat)
        val lngDistance = Math.toRadians(userLng - venueLng)

        val a = (sin(latDistance / 2) * sin(latDistance / 2)
                + (cos(Math.toRadians(userLat)) * cos(Math.toRadians(venueLat))
                * sin(lngDistance / 2) * sin(lngDistance / 2)))

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return (AVERAGE_RADIUS_OF_EARTH_KM * c).roundToInt()
    }

    /**
     *  Function to calculate angle between 2 locations
     *  using latitude and longitude
     */
    private fun angleFromCoordinate(
        userLat: Double, userLng: Double,
        venueLat: Double, venueLng: Double
    ): Double {

        val lat1 = Math.toRadians(userLat)
        val lat2 = Math.toRadians(venueLat)
        val long1 = Math.toRadians(userLng)
        val long2 = Math.toRadians(venueLng)

        val dLon = long1 - long2

        val y = sin(dLon) * cos(lat2)
        val x = cos(lat1) * sin(lat2) - (sin(lat1) * cos(lat2) * cos(dLon))

        var brng = atan2(y, x)

        brng = Math.toDegrees(brng)
        brng = (brng + 360) % 360
        brng = 360 - brng // count degrees counter-clockwise - remove to make clockwise

        return brng
    }

    private fun calculateDirection(angle: Double): String {
        return when(angle) {
            in 0.0..11.25 -> "north"
            in 11.26..33.75 -> "north of north-east"
            in 33.76..56.25 -> "north-east"
            in 56.26..78.75 -> "east-northeast"
            in 78.76..101.25 -> "east"
            in 101.26..123.75 -> "east-southeast"
            in 123.76..146.25 -> "south-east"
            in 146.26..168.75 -> "south-southeast"
            in 168.76..191.25 -> "south"
            in 191.26..213.75 -> "south-southwest"
            in 213.76..236.25 -> "south-west"
            in 236.26..258.75 -> "west-south-west"
            in 258.76..281.25 -> "west"
            in 281.26..303.75 -> "west-northwest"
            in 303.76..326.25 -> "north-west"
            in 326.26..348.67 -> "north-northwest"
            in 348.75..360.0 -> "north"
            else -> "unknown"
        }
    }

    data class GuessState(
        val countryName: String,
        val distance: Int,
        val direction: String,
        val color: Color = Color.White
    )

    data class ButtonState(
        val text: String
    )
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}