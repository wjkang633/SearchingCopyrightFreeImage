package woojin.android.kotlin.project.searchingcopyrightfreeimage.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import woojin.android.kotlin.project.searchingcopyrightfreeimage.BuildConfig
import woojin.android.kotlin.project.searchingcopyrightfreeimage.data.models.PhotoResponse

object Repository {
    private val unsplashApiService: UnsplashApiService by lazy {
        Retrofit
            .Builder()
            .baseUrl(Url.UNSPLASH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildOkHttpClient())
            .build()
            .create()
    }

    private fun buildOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                })
            .build()

    suspend fun getRandomPhotos(query: String?): List<PhotoResponse>? = unsplashApiService.getRandomPhotos(query).body()
}