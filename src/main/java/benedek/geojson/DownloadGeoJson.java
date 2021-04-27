package benedek.geojson;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class DownloadGeoJson
{
    public static void main(String[] args) throws IOException
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://earthquake.usgs.gov")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        GeoJsonService service = retrofit.create(GeoJsonService.class);
        Single<GeoJsonFeed> single = service.getSignificantEarthquakes();
        // DO NOT USE BLOCKING GET!
        GeoJsonFeed feed = single.blockingGet();

        GeoJsonFeed.Feature largest = feed.features.get(0);
        for (GeoJsonFeed.Feature feature: feed.features)
        {
            if(feature.properties.mag > largest.properties.mag)
            {
                largest = feature;
            }
        }

        System.out.printf("%s %f %d",
                largest.properties.place,
                largest.properties.mag,
                largest.properties.time);
        System.exit(0);
    }

}
