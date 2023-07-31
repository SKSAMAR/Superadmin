package com.fintech.superadmin.clean.data.di

import android.content.Context
import com.fintech.superadmin.BuildConfig
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.data.network.APIServices
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
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
object FintechSystemModule {


    @Singleton
    @Provides
    fun getFintechAPI(
        @ApplicationContext context: Context,
        httpClient: OkHttpClient
    ): FintechAPI {
        val gson = GsonBuilder()
            .setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("https://" + context.getString(R.string.base_url_data) + "/")
            .client(httpClient)
            .build()
        return retrofit.create(FintechAPI::class.java)
    }

}