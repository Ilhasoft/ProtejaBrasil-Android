package br.com.ilhasoft.protejaBrasil.model.view;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;

import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinder;
import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinderBase;
import net.droidlabs.mvvm.recyclerview.events.ClickHandler;

import br.com.ilhasoft.protejaBrasil.BR;
import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.model.Violation;

/**
 * Created by johncordeiro on 09/10/15.
 */
public class ViolationTypeViewModel extends BaseObservable {

    @Bindable
    public ObservableArrayList<Violation> violations;

    @Bindable
    public ClickHandler<Violation> handler;

    public ViolationTypeViewModel(ClickHandler<Violation> handler) {
        this.violations = new ObservableArrayList<>();
        this.handler = handler;
    }

    public static ItemBinder<Violation> itemViewBinder() {
        return new ItemBinderBase<>(BR.violation, R.layout.item_violation);
    }

}
