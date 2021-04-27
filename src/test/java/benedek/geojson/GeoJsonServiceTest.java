package benedek.geojson;

import io.reactivex.rxjava3.core.Single;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

public class GeoJsonServiceTest
{

    @Test
    public void getSignificantEarthquakes()
    {
        //given
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://earthquake.usgs.gov")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        GeoJsonService service = retrofit.create(GeoJsonService.class);

        //when
        Single<GeoJsonFeed> single = service.getSignificantEarthquakes();
        // DO NOT USE BLOCKING GET!
        GeoJsonFeed feed = single.blockingGet();

        //then
        assertNotNull(feed);
        assertNotNull(feed.features);
        assertFalse(feed.features.isEmpty());
        assertNotNull(feed.features.get(0).properties);

        assertNotNull(feed.features.get(0).properties.place);
        assertTrue(feed.features.get(0).properties.mag > 0);
        assertTrue(feed.features.get(0).properties.time > 0);
        assertNotNull(feed.features.get(0).geometry);
        assertNotNull(feed.features.get(0).geometry.coordinates);
        assertFalse(feed.features.get(0).geometry.coordinates.isEmpty());


    }
}
