package br.com.ilhasoft.protejaBrasil.model.view;

import br.com.ilhasoft.protejaBrasil.helpers.BindableFeedbackType;
import br.com.ilhasoft.protejaBrasil.model.Feedback;
import br.com.ilhasoft.support.databinding.bindings.BindableString;

/**
 * Created by johncordeiro on 22/10/15.
 */
public class FeedbackViewModel {

    public BindableFeedbackType type = new BindableFeedbackType();
    public BindableString name = new BindableString();
    public BindableString email = new BindableString();
    public BindableString message = new BindableString();

    public Feedback get() {
        return new Feedback(type.get(), name.get(), email.get(), message.get());
    }

}
