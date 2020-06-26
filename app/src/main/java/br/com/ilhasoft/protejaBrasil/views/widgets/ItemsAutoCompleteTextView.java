package br.com.ilhasoft.protejaBrasil.views.widgets;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListAdapter;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.listener.TaskListener;

/**
 * Created by johncordeiro on 21/10/15.
 */
public class ItemsAutoCompleteTextView<T> extends AppCompatAutoCompleteTextView
        implements TaskListener<T> {

    private T selectedItem;
    private final ArrayAdapter<T> adapter;

    private Listener<T> listener;

    public ItemsAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public ItemsAutoCompleteTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.autoCompleteTextViewStyle);
    }

    public ItemsAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
        super.setAdapter(adapter);
    }

    @Override
    public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {
        throw new RuntimeException("This widget don't support adapters");
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if(listener != null) {
            listener.getTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, text.toString());
        }
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        super.performFiltering("", keyCode);
    }

    @Override
    protected void replaceText(CharSequence text) {
        super.replaceText(text);
        for (int i = 0; i < adapter.getCount(); i++) {
            T item = adapter.getItem(i);
            if (text.toString().equals(item.toString()))
                selectedItem = item;
        }
    }

    public T getSelectedItem() {
        return selectedItem;
    }

    public void setListener(Listener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onTaskFinished(List<T> items) {
        adapter.clear();
        if(items != null) {
            adapter.addAll(items);
        }
    }

    public interface Listener<T> {
        Task<T> getTask();
    }

    public static abstract class Task<T> extends AsyncTask<String, Void, List<T>> {

        private TaskListener<T> listener;

        public Task(TaskListener<T> listener) {
            this.listener = listener;
        }

        @Override
        protected void onPostExecute(List<T> items) {
            super.onPostExecute(items);
            listener.onTaskFinished(items);
        }
    }
}
