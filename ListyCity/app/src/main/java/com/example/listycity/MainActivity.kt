package com.example.listycity

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = { if(!cityRepository.cities.contains(it)) {cityRepository.addCity(it)}},
                        onDeleteCity = {cityRepository.deleteCity(it)},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CityListScreen(cities: List<String>,
                   onAddCity: (String) -> Unit,
                   onDeleteCity: (String) -> Unit,
                   modifier: Modifier = Modifier
) {
    var newCityName by remember {mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = newCityName,
                onValueChange = { newCityName = it },
                label = { Text("City Name") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newCityName.isNotBlank()) {
                        onAddCity(newCityName)
                        newCityName = ""
                    }
                }
            ){
                Text("Add City")
            }
        }
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(cities) { city ->
                CityRow(city = city, onDeleteCity = onDeleteCity)
            }
        }
    }


}

@Composable
fun CityRow(city: String,
            onDeleteCity: (String) -> Unit){

    Text(text = city, fontSize = 28.sp,
        modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
    )
    Button(
        onClick = { onDeleteCity(city) },
        modifier = Modifier.padding(horizontal = 10.dp)
    ){
        Text("Delete $city")
    }

}