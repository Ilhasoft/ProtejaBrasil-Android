package br.com.ilhasoft.protejaBrasil.model.view;

import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinder;
import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinderBase;
import net.droidlabs.mvvm.recyclerview.events.ClickHandler;

import br.com.ilhasoft.protejaBrasil.BR;
import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.model.Violation;

/**
 * Created by johncordeiro on 09/10/15.
 */
public class ViolationTypeSelectTypeViewModel extends ViolationTypeViewModel {

    public ViolationTypeSelectTypeViewModel(ClickHandler<Violation> handler) {
        super(handler);
    }

    public static ItemBinder<Violation> itemViewBinder() {
        return new ItemBinderBase<>(BR.violation, R.layout.item_select_violation);
    }

}
