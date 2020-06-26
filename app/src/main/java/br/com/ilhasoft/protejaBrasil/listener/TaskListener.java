package br.com.ilhasoft.protejaBrasil.listener;

import java.util.List;

/**
 * Created by johncordeiro on 21/10/15.
 */
public interface TaskListener<T> {

    void onTaskFinished(List<T> items);

}
