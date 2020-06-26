package br.com.ilhasoft.protejaBrasil.network;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by johncordeiro on 09/10/15.
 */
public interface ProtectionNetworkApi {

    @GET("protection_network")
    Call<List<ProtectionNetwork>> listProtectionNetworks(@Query("lang") String lang);

    @GET("protection_network/theme/{theme}")
    Call<List<ProtectionNetwork>> listProtectionNetworksByTheme(@Path("theme") Integer theme, @Query("lang") String lang);

    @GET("protection_network/type/{type}")
    Call<List<ProtectionNetwork>> listProtectionNetworksByType(@Path("type") Integer type, @Query("lang") String lang);

    @GET("protection_network/state/{state}")
    Call<List<ProtectionNetwork>> listProtectionNetworksByState(@Path("state") String state, @Query("lang") String lang);

    @GET("protection_network/state/{state}/theme/{theme}")
    Call<List<ProtectionNetwork>> listProtectionNetworksByStateAndTheme(@Path("state") String state
            , @Path("theme") Integer theme, @Query("lang") String lang);

    @GET("protection_network/name/{name}")
    Call<List<ProtectionNetwork>> listProtectionNetworksByName(@Path("name") String name, @Query("lang") String lang);

    @GET("protection_network/name/{name}/position/{lat},{long}/")
    Call<List<ProtectionNetwork>> listNearProtectionNetworksByName(@Path("name") String name
            , @Path("lat") Double latitude, @Path("long") Double longitude, @Query("lang") String lang);

    @GET("protection_network/type/{type}/position/{lat},{long}/")
    Call<ProtectionNetwork> listProtectionNetworksByTypeAndProximity(@Path("type") Integer type
            , @Path("lat") Double latitude, @Path("long") Double longitude, @Query("lang") String lang);

    @GET("protection_network/theme/{theme}/position/{lat},{long}/radius/{distance}")
    Call<List<ProtectionNetwork>> listProtectionNetworksByThemeAndPosition(@Path("theme") Integer theme
        , @Path("lat") Double latitude, @Path("long") Double longitude, @Path("distance") Integer distance,  @Query("lang") String lang);

}
