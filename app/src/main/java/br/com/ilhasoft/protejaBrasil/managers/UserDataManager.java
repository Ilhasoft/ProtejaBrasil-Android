package br.com.ilhasoft.protejaBrasil.managers;

import android.content.Context;

import br.com.ilhasoft.support.preferences.Preferences;

/**
 * Created by john-mac on 5/8/16.
 */
public class UserDataManager extends Preferences {

    private enum Field {
        State
    }

    public UserDataManager(Context context) {
        super(context, "user_data");
    }

    public void setState(String initials) {
        setValue(Field.State, initials);
    }

    public String getState() {
        return getValue(Field.State, "");
    }

}
