package br.com.ilhasoft.protejaBrasil.model.view;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;

import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinder;
import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinderBase;
import net.droidlabs.mvvm.recyclerview.events.ClickHandler;

import br.com.ilhasoft.protejaBrasil.BR;
import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.model.Theme;

/**
 * Created by johncordeiro on 19/10/15.
 */
public class ThemeViewModel extends BaseObservable {

    @Bindable
    public ObservableArrayList<Theme> themes;

    @Bindable
    public ClickHandler<Theme> handler;

    public ThemeViewModel(ClickHandler<Theme> handler) {
        this.themes = new ObservableArrayList<>();
        this.handler = handler;
    }

    public static ItemBinder<Theme> itemViewBinder() {
        return new ItemBinderBase<>(BR.theme, R.layout.item_theme);
    }

}
