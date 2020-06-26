package br.com.ilhasoft.protejaBrasil.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.ActivityOpinionBinding;
import br.com.ilhasoft.protejaBrasil.fragments.FeedbackResultFragment;
import br.com.ilhasoft.protejaBrasil.helpers.BindableFeedbackType;
import br.com.ilhasoft.protejaBrasil.helpers.CustomValidator;
import br.com.ilhasoft.protejaBrasil.listener.OnCloseListener;
import br.com.ilhasoft.protejaBrasil.model.Feedback;
import br.com.ilhasoft.protejaBrasil.model.view.FeedbackViewModel;
import br.com.ilhasoft.protejaBrasil.network.FeedbackServices;
import br.com.ilhasoft.support.utils.KeyboardHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by johncordeiro on 22/10/15.
 */
public class OpinionActivity extends TrackedActivity implements OnCloseListener {

    private static final String ANALYTICS_SCREEN_NAME = "Feedback";

    private ActivityOpinionBinding binding;
    private FeedbackViewModel viewModel;

    private ProgressDialog progressDialog;

    private CustomValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_opinion);
        setupObjects();
        setupData();
        setupView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendScreenViewReport(ANALYTICS_SCREEN_NAME);
    }

    private void setupObjects() {
        validator = new CustomValidator(this);
    }

    private void setupView() {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        binding.send.setOnClickListener(onSendClickListener);
        binding.message.setOnTouchListener(onTouchMessageListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void setupData() {
        binding.setDoubt(new BindableFeedbackType(Feedback.FeedbackType.doubt));
        binding.setSuggestion(new BindableFeedbackType(Feedback.FeedbackType.suggestion));
        binding.setCriticism(new BindableFeedbackType(Feedback.FeedbackType.criticism));
        binding.setCompliment(new BindableFeedbackType(Feedback.FeedbackType.compliment));

        createViewModel();
    }

    private void createViewModel() {
        viewModel = new FeedbackViewModel();
        binding.setViewModel(viewModel);
    }

    private View.OnClickListener onSendClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(validateFields()) {
                progressDialog = ProgressDialog.show(OpinionActivity.this, null, getString(R.string.message_wait_a_moment), true);

                FeedbackServices services = new FeedbackServices(OpinionActivity.this);
                services.sendFeedback(viewModel.get()).enqueue(onSendFeedbackCallback);
                sendActionReport(AnalyticsAction.SendFeedback);
            }
        }
    };

    private boolean validateFields() {
        return validator.validateEmail(binding.email)
                & validator.validateText(binding.message)
                & validator.validateText(binding.name)
                & validateType();
    }

    private boolean validateType() {
        boolean typeValid = binding.getViewModel().type.get() != null;
        if(!typeValid) {
            Toast.makeText(OpinionActivity.this, R.string.error_message_type_invalid, Toast.LENGTH_SHORT).show();
        }
        return typeValid;
    }

    private Callback<Feedback> onSendFeedbackCallback = new Callback<Feedback>() {
        @Override
        public void onResponse(Call<Feedback> call, Response<Feedback> response) {
            progressDialog.dismiss();
            addFeedbackResultFragment();
            createViewModel();
        }

        @Override
        public void onFailure(Call<Feedback> call, Throwable throwable) {
            progressDialog.dismiss();
            Toast.makeText(OpinionActivity.this, R.string.error_message_feedback, Toast.LENGTH_SHORT).show();
        }
    };

    private void addFeedbackResultFragment() {
        hideKeyboard();

        FeedbackResultFragment feedbackResultFragment = new FeedbackResultFragment();
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(android.R.id.content, feedbackResultFragment)
                .commit();
    }

    private void hideKeyboard() {
        KeyboardHandler keyboardHandler = new KeyboardHandler();
        keyboardHandler.changeKeyboardVisibility(this, false);
    }

    @Override
    public void onClose() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    private View.OnTouchListener onTouchMessageListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (view.getId() == R.id.message) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
            }
            return false;
        }
    };
}
