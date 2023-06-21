package com.fintech.superadmin.flight.data.di

import android.content.Context
import android.content.SharedPreferences
import com.fintech.superadmin.R
import com.fintech.superadmin.flight.data.remote.FlightApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FlightAppModule {


    @Singleton
    @Provides
    fun getFlightApi(httpClient: OkHttpClient, @ApplicationContext context: Context): FlightApi {
        val retrofit: Retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("https://${context.resources.getString(R.string.base_url_data)}/").client(httpClient)
                .build()
        return retrofit.create(FlightApi::class.java)
    }


    @Singleton
    @Provides
    fun getSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MySharedPrefFlight", Context.MODE_PRIVATE)
    }
}