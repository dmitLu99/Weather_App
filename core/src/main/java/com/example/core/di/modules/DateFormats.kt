package com.example.core.di.modules

import com.example.core.di.annotation.Base
import com.example.core.di.annotation.Daily
import com.example.core.di.annotation.DailyBase
import com.example.core.di.annotation.Hourly
import dagger.Module
import dagger.Provides
import java.time.format.DateTimeFormatter
import java.util.*

@Module
object DateFormats {

    @[Provides Hourly]
    fun provideHourlyWeatherDateFormat(locale: Locale): DateTimeFormatter =
        DateTimeFormatter.ofPattern("HH:mm", locale)

    @[Provides Daily]
    fun provideDailyWeatherDateFormat(locale: Locale): DateTimeFormatter =
        DateTimeFormatter.ofPattern("EE, d MMM", locale)

    @[Provides Base]
    fun provideBaseDateFormat(locale: Locale): DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", locale)

    @[Provides DailyBase]
    fun provideDailyBaseDateFormat(locale: Locale): DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd", locale)
}
