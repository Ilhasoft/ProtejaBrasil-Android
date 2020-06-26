package br.com.ilhasoft.protejaBrasil.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentFeedbackResultBinding;
import br.com.ilhasoft.protejaBrasil.listener.OnCloseListener;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class FeedbackResultFragment extends Fragment {

    private FragmentFeedbackResultBinding binding;

    private OnCloseListener onCloseListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback_result, container, false);
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
        binding.evaluate.setOnClickListener(onEvaluateClickListener);
    }

    private View.OnClickListener onStartClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(onCloseListener != null)
                onCloseListener.onClose();
        }
    };

    private View.OnClickListener onEvaluateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openOnGooglePlay();
        }
    };

    private void openOnGooglePlay() {
        final String appPackageName = getActivity().getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
