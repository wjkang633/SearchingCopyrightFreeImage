package woojin.android.kotlin.project.searchingcopyrightfreeimage.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import woojin.android.kotlin.project.searchingcopyrightfreeimage.BuildConfig
import woojin.android.kotlin.project.searchingcopyrightfreeimage.data.models.PhotoResponse

interface UnsplashApiService {
    @GET(
        "photos/random?" +
                "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}&" +
                "count=30"
    )
    suspend fun getRandomPhotos(
        @Query("query") query: String?
    ): Response<List<PhotoResponse>>
}