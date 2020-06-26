package br.com.ilhasoft.protejaBrasil.network;

import android.content.Context;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.model.NetworkType;
import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;
import br.com.ilhasoft.protejaBrasil.model.State;
import br.com.ilhasoft.protejaBrasil.model.Theme;
import retrofit2.Call;

/**
 * Created by johncordeiro on 09/10/15.
 */
public class ProtectionNetworkServices extends BaseServices<ProtectionNetworkApi> {

    public ProtectionNetworkServices(Context context, boolean fromCache) {
        super(ProtectionNetworkApi.class, true, context, fromCache);
    }

    public ProtectionNetworkServices(Context context) {
        this(context, false);
    }

    public Call<List<ProtectionNetwork>> listProtectionNetworks() {
        return service.listProtectionNetworks(lang);
    }

    public Call<List<ProtectionNetwork>> listProtectionNetworksByTheme(Theme theme) {
        return service.listProtectionNetworksByTheme(theme.getId(), lang);
    }

    public Call<List<ProtectionNetwork>> listProtectionNetworksByName(String name) {
        return service.listProtectionNetworksByName(name, lang);
    }

    public Call<List<ProtectionNetwork>> listNearProtectionNetworksByName(String name, Double latitude, Double longitude) {
        return service.listNearProtectionNetworksByName(name, latitude, longitude, lang);
    }

    public Call<List<ProtectionNetwork>> listProtectionNetworksByType(NetworkType type) {
        return service.listProtectionNetworksByType(type.getId(), lang);
    }

    public Call<ProtectionNetwork> listProtectionNetworksByTypeAndProximity(NetworkType type
            , Double latitude, Double longitude) {
        return service.listProtectionNetworksByTypeAndProximity(type.getId(), latitude, longitude, lang);
    }

    public Call<ProtectionNetwork> listProtectionNetworksByIdAndProximity(Integer id
            , Double latitude, Double longitude) {
        return service.listProtectionNetworksByTypeAndProximity(id, latitude, longitude, lang);
    }

    public Call<List<ProtectionNetwork>> listProtectionNetworksByState(State state) {
        return service.listProtectionNetworksByState(state.getInitials(), lang);
    }

    public Call<List<ProtectionNetwork>> listProtectionNetworksByStateAndTheme(State state, Theme theme) {
        return service.listProtectionNetworksByStateAndTheme(state.getInitials(), theme.getId(), lang);
    }

    public Call<List<ProtectionNetwork>> listProtectionNetworksByThemeAndPosition(Integer theme
            , Double latitude, Double longitude, Integer distance) {
        return service.listProtectionNetworksByThemeAndPosition(theme, latitude, longitude, distance, lang);
    }
}
