package br.com.ilhasoft.protejaBrasil.model.view;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;

import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinder;
import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinderBase;
import net.droidlabs.mvvm.recyclerview.events.ClickHandler;

import br.com.ilhasoft.protejaBrasil.BR;
import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;

/**
 * Created by johncordeiro on 09/10/15.
 */
public class ProtectionNetworkViewModel extends BaseObservable {

    @Bindable
    public ObservableArrayList<ProtectionNetwork> list;

    @Bindable
    public ClickHandler<ProtectionNetwork> handler;

    public ProtectionNetworkViewModel(ClickHandler<ProtectionNetwork> handler) {
        this.list = new ObservableArrayList<>();
        this.handler = handler;
    }

    public static ItemBinder<ProtectionNetwork> itemViewBinder() {
        return new ItemBinderBase<>(BR.protectionNetwork, R.layout.item_search_network);
    }

}
