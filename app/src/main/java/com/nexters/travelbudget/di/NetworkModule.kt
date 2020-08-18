package com.nexters.travelbudget.di

import com.nexters.travelbudget.BuildConfig
import com.nexters.travelbudget.data.remote.api.AuthService
import com.nexters.travelbudget.data.remote.api.TripieService
import com.nexters.travelbudget.data.remote.interceptor.AuthInterceptor
import com.nexters.travelbudget.data.remote.model.enums.RetrofitQualifiers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit, OkHttp, DI(네트워크) 설정, 뷰에서 API 접근을 위한 파일
 *
 * @author Wayne
 * @since v1.0.0 / 2020.06.15
 */

/** 네트워크 모듈(DI) 설정 */
val networkModule = module {
    single { AuthInterceptor(androidContext()) }
    single { provideAuthApi(get(qualifier = RetrofitQualifiers.DEFAULT)) }
    single { provideTripieApi(get(qualifier = RetrofitQualifiers.AUTH)) }
    single(RetrofitQualifiers.AUTH) { provideAuthOkHttpClient(get()) }
    single(RetrofitQualifiers.DEFAULT) { provideOkHttpClient() }
}

/** Auth Api 접근을 위한 메소드 */
fun provideAuthApi(okHttpClient: OkHttpClient): AuthService {
    return Retrofit.Builder().baseUrl("http://175.123.172.42:9050/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AuthService::class.java)
}

/** Tripie Api 접근을 위한 메소드 */
fun provideTripieApi(okHttpClient: OkHttpClient): TripieService {
    return Retrofit.Builder().baseUrl("http://175.123.172.42:9050/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(TripieService::class.java)
}

/** 기본 OkHttp 설정 */
fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .build()
}

/** Auth 관련 OkHttp 설정 */
fun provideAuthOkHttpClient(
    authInterceptor: AuthInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .build()
}