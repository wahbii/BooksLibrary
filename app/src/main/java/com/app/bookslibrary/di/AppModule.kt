package com.app.bookslibrary.di

import com.app.bookslibrary.BuildConfig
import com.app.bookslibrary.dataManager.Apis
import com.app.bookslibrary.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }

        return builder.build()
    }

    @Singleton
    @Provides
    fun getGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun getRerofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl(Constants.path).addConverterFactory(ScalarsConverterFactory.create())
        .client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Apis = retrofit.create(Apis::class.java)


}