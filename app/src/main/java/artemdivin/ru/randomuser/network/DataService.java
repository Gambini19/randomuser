package artemdivin.ru.randomuser.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by admin on 28.01.2018.
 */

public  interface DataService {
    @GET("api/?results=10")
    Call<ResponseBody> getRandomPerson();

}
