package com.clone.movie.network.di

//@Module
//@InstallIn(SingletonComponent::class)
object NetworkModule {
//    @Singleton
//    @Provides
//    fun provideMoshi(): Moshi =
//        Moshi
//            .Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//
//    @Singleton
//    @Provides
//    fun provideOkHttpClient(
//        loggingInterceptor: HttpLoggingInterceptor,
//        headerInterceptor: Interceptor,
//    ): OkHttpClient =
//        OkHttpClient
//            .Builder()
//            .addInterceptor(loggingInterceptor)
//            .addInterceptor(headerInterceptor)
//            .connectTimeout(100, TimeUnit.SECONDS)
//            .readTimeout(100, TimeUnit.SECONDS)
//            .writeTimeout(100, TimeUnit.SECONDS)
//            .build()
//
//    @Provides
//    @Singleton
//    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
//        HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(
//        client: OkHttpClient,
//        moshi: Moshi,
//    ): Retrofit =
//        Retrofit
//            .Builder()
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .client(client)
//            .baseUrl("https://api.unsplash.com/")
//            .build()
//
//    @Provides
//    @Singleton
//    fun providesHeaderInterceptor(): Interceptor =
//        Interceptor { chain ->
//            val request =
//                chain
//                    .request()
//                    .newBuilder()
//                    .addHeader("Accept-Version", "v1")
//                    .addHeader("Authorization", "Client-ID eOfYAPKwA61_W94eO7nOm13LTq4bRes2HcPMY9UFpwk")
//                    .build()
//            chain.proceed(request)
//        }
}