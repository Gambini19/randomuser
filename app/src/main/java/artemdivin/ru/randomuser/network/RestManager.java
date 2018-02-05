package artemdivin.ru.randomuser.network;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import artemdivin.ru.randomuser.util.Constant;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 08.12.2017.
 */

public class RestManager {

    private static DataService dataService;

    public static DataService getService() {
        if (dataService == null){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(2, TimeUnit.SECONDS);
            httpClient.addInterceptor(logging);


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            dataService = retrofit.create(DataService.class);
        }

        return dataService;
    }
}
