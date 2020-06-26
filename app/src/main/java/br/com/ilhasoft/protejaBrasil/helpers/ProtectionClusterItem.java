package br.com.ilhasoft.protejaBrasil.helpers;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;

/**
 * Created by john-mac on 8/3/16.
 */
public class ProtectionClusterItem implements ClusterItem {

    private ProtectionNetwork protectionNetwork;
    private String title;
    private String snippet;

    public ProtectionClusterItem(ProtectionNetwork protectionNetwork) {
        this.protectionNetwork = protectionNetwork;
    }

    public ProtectionClusterItem(ProtectionNetwork protectionNetwork, String title, String snippet) {
        this(protectionNetwork);
        this.title = title;
        this.snippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(protectionNetwork.getPosition().getLatitude()
                        , protectionNetwork.getPosition().getLongitude());
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }

    public ProtectionNetwork getProtectionNetwork() {
        return protectionNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProtectionClusterItem that = (ProtectionClusterItem) o;
        return protectionNetwork.equals(that.protectionNetwork);
    }

    @Override
    public int hashCode() {
        return protectionNetwork.hashCode();
    }

    @Override
    public String toString() {
        return protectionNetwork.toString();
    }
}
