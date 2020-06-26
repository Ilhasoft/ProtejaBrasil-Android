package br.com.ilhasoft.protejaBrasil.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.ActivityInternetCrimeBinding;
import br.com.ilhasoft.protejaBrasil.fragments.report.BaseReportFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.InternetCrimeFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.ProtocolFragment;
import br.com.ilhasoft.protejaBrasil.helpers.InternetCrimeConverter;
import br.com.ilhasoft.protejaBrasil.model.InternetCrime;
import br.com.ilhasoft.protejaBrasil.model.InternetReportResult;
import br.com.ilhasoft.protejaBrasil.model.view.InternetCrimeViewModel;
import br.com.ilhasoft.protejaBrasil.network.InternetViolationApi;
import br.com.ilhasoft.protejaBrasil.network.InternetViolationServices;
import br.com.ilhasoft.support.utils.KeyboardHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by johncordeiro on 21/10/15.
 */
public class InternetCrimeActivity extends ReportBaseActivity {

    private static final String ANALYTICS_SCREEN_NAME = "Internet crime report";

    private ActivityInternetCrimeBinding binding;

    private InternetCrimeViewModel viewModel;
    private BaseReportFragment internetCrimeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_internet_crime);
        viewModel = new InternetCrimeViewModel();
        setupView();

        if(savedInstanceState == null) {
            internetCrimeFragment = BaseReportFragment.newInstance(InternetCrimeFragment.class, viewModel, true);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, internetCrimeFragment)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        sendActionReport(AnalyticsAction.StartInternetCrimeReport);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendScreenViewReport(ANALYTICS_SCREEN_NAME);
    }

    private void setupView() {
        setActionBarWithNavigationUp(binding.toolbar);
        binding.reportInternetViolation.setOnClickListener(onReportClickListener);
    }

    private View.OnClickListener onReportClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(internetCrimeFragment.validateForm()) {
                sendInternetCrime();
            }
        }
    };

    private void sendInternetCrime() {
        KeyboardHandler keyboardHandler = new KeyboardHandler();
        keyboardHandler.changeKeyboardVisibility(this, false);

        InternetCrimeConverter converter = new InternetCrimeConverter();
        InternetCrime internetCrime = converter.internetCrimeFromViewModel(viewModel);

        final ProgressDialog progressDialog = ProgressDialog.show(this, null, getString(R.string.load_message_report)
                , true, false);
        InternetViolationServices services = new InternetViolationServices(this, false);
        services.sendInternetCrime(internetCrime).enqueue(new Callback<InternetViolationApi.ObjectResponse<InternetReportResult>>() {
            @Override
            public void onResponse(Call<InternetViolationApi.ObjectResponse<InternetReportResult>> call, Response<InternetViolationApi.ObjectResponse<InternetReportResult>> response) {
                progressDialog.dismiss();
                sendActionReport(AnalyticsAction.FinishInternetCrimeReport);
                if(response.body() != null && response.body().getData() != null) {
                    addProtocolFragment(response.body().getData().getKey());
                } else {
                    Toast.makeText(InternetCrimeActivity.this, R.string.message_error_address, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InternetViolationApi.ObjectResponse<InternetReportResult>> call, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(InternetCrimeActivity.this, R.string.message_report_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addProtocolFragment(String protocol) {
        ProtocolFragment protocolFragment = ProtocolFragment.newInstance(protocol
                , getString(R.string.info_internet_crime_destination)
                , getString(R.string.protocol_info_internet));
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .add(android.R.id.content, protocolFragment)
                .commit();
    }

    @Override
    void onGiveUpReport() {
        sendActionReport(AnalyticsAction.GiveUpInternetCrimeReport);
    }
}
