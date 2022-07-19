package com.example.feature_main_screen.domain.models.mappers

import com.example.core.data.models.DateTimeProvider
import com.example.core.data.models.DateTimeProvider.TimeOfDay.DAY
import com.example.core.data.models.Temperature
import com.example.core.data.models.TemperatureRange
import com.example.core.domain.models.TranslatedWeather.CLEAR_SKY
import com.example.core.domain.models.TranslatedWeather
import com.example.core.utils.Mapper
import com.example.feature_main_screen.data.models.DailyWeather
import com.example.feature_main_screen.domain.models.DailyWeatherDisplayableItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
internal class DailyWeatherToDailyWeatherDisplayableItemMapperTest {
    private val weatherCodeToTranslatedWeatherMapper: Mapper<Int, TranslatedWeather> = mockk()
    private val translatedWeatherToResourceMapper: Mapper<Pair<TranslatedWeather, DateTimeProvider.TimeOfDay>, Int> = mockk()
    private val responseDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.CANADA)
    private val dailyDateFormat = SimpleDateFormat("EE, d MMM", Locale.CANADA)
    private val date = responseDateFormat.parse("2022-07-01T10:00")!!
    private val mapper = DailyWeatherToDailyWeatherDisplayableItemMapper(
        dailyWeatherDateFormat = dailyDateFormat,
        weatherCodeToTranslatedWeatherMapper = weatherCodeToTranslatedWeatherMapper,
        translatedWeatherToResourceMapper = translatedWeatherToResourceMapper
    )

    @Test fun `try to map a default value`() = runTest {
        val dailyWeather = DailyWeather(
            date = date, temperatureMin = 0.1,  temperatureMax = 0.2, weatherCode = 1
        )

        coEvery { weatherCodeToTranslatedWeatherMapper.map(1) } returns CLEAR_SKY
        coEvery { translatedWeatherToResourceMapper.map(CLEAR_SKY to DAY) } returns 10

        val expected = DailyWeatherDisplayableItem(
            weatherCode = 1,
            temperature = TemperatureRange(
                firstValue = Temperature(value = 0.1),
                secondValue = Temperature(value = 0.2)
            ),
            date = dailyDateFormat.format(date),
            imageResId = 10
        )
        val actual = mapper.map(from = dailyWeather)

        assertEquals(expected, actual)
    }
}