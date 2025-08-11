import com.inacap.smartflora.Item
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("items")
    fun getItems(): Call<List<Item>>

    @POST("items")
    fun addItem(@Body item: Item): Call<Void>

    @DELETE("items/{name}")
    fun deleteItem(@Path("name") name: String): Call<Void>
}
