package br.com.ilhasoft.protejaBrasil.listener;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.model.Theme;

/**
 * Created by john-mac on 1/5/16.
 */
public interface OnThemesLoadedListener {

    void onThemesLoaded(List<Theme> themes);

}
