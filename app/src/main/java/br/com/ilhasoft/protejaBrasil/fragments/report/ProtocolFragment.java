package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentProtocolBinding;
import br.com.ilhasoft.protejaBrasil.listener.OnCloseListener;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class ProtocolFragment extends Fragment {

    private static final String EXTRA_PROTOCOL = "protocol";
    private static final String EXTRA_DESTINATION = "destination";
    private static final String EXTRA_MESSAGE = "message";

    private FragmentProtocolBinding binding;

    private OnCloseListener onCloseListener;

    public static ProtocolFragment newInstance(String protocol, String destination, String message) {
        Bundle args = new Bundle();
        args.putString(EXTRA_PROTOCOL, protocol);
        args.putString(EXTRA_DESTINATION, destination);
        args.putString(EXTRA_MESSAGE, message);

        ProtocolFragment fragment = new ProtocolFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_protocol, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnCloseListener) {
            onCloseListener = (OnCloseListener) context;
        }
    }

    private void setupView() {
        binding.start.setOnClickListener(onStartClickListener);

        String protocol = getArguments().getString(EXTRA_PROTOCOL);
        binding.protocol.setText(protocol);

        String destination = getArguments().getString(EXTRA_DESTINATION);
        binding.destination.setText(destination);

        String message = getArguments().getString(EXTRA_MESSAGE);
        binding.message.setText(message);
    }

    private View.OnClickListener onStartClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(onCloseListener != null)
                onCloseListener.onClose();
        }
    };
}
