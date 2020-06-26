package br.com.ilhasoft.protejaBrasil.rxjava.observables;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ilhasoft on 01/03/16.
 */
public class EditTextObservable implements TextWatcher, Observable.OnSubscribe<String> {
    private EditText editText;
    private Subscriber<? super String> subscriber;

    public EditTextObservable(EditText editText) {
        this.editText = editText;
        this.editText.addTextChangedListener(this);
    }

    @Override
    public void call(Subscriber<? super String> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        subscriber.onNext(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
