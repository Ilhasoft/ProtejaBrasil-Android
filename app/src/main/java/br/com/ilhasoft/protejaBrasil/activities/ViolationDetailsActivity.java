package br.com.ilhasoft.protejaBrasil.activities;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.ActivityViolationDetailsBinding;
import br.com.ilhasoft.protejaBrasil.fragments.InternetViolationFragment;
import br.com.ilhasoft.protejaBrasil.fragments.ViolationDetailsFragment;
import br.com.ilhasoft.protejaBrasil.fragments.ViolationsFragment;
import br.com.ilhasoft.protejaBrasil.listener.OnViolationSelectedListener;
import br.com.ilhasoft.protejaBrasil.model.Theme;
import br.com.ilhasoft.protejaBrasil.model.Violation;
import br.com.ilhasoft.protejaBrasil.model.ViolationCategory;
import br.com.ilhasoft.support.tool.ColorHelper;
import br.com.ilhasoft.support.tool.StatusBarDesigner;

/**
 * Created by johncordeiro on 10/10/15.
 */
public class ViolationDetailsActivity extends AppCompatActivity implements OnViolationSelectedListener {

    public static final String EXTRA_THEME = "theme";

    private ActivityViolationDetailsBinding binding;

    private Theme theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_violation_details);

        if(savedInstanceState == null) {
            theme = getIntent().getParcelableExtra(EXTRA_THEME);
            setupView();

            ViolationsFragment violationsFragment = ViolationsFragment.newInstance(theme);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, violationsFragment)
                    .commit();
        }
    }

    public Theme getSelectedTheme() {
        return theme;
    }

    private void setupView() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.description.setMovementMethod(new ScrollingMovementMethod());
        animateEnter();
    }

    private void animateEnter() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animateEnterLollipop();
        } else {
            setupColor();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateEnterLollipop() {
        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {}

            @Override
            public void onTransitionEnd(Transition transition) {
                getWindow().getSharedElementEnterTransition().removeListener(this);
                setupColor();
                animateReveal(binding.appbarBackground);
            }

            @Override
            public void onTransitionCancel(Transition transition) {}

            @Override
            public void onTransitionPause(Transition transition) {}

            @Override
            public void onTransitionResume(Transition transition) {}
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateReveal(View view) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    private void setupColor() {
        if(theme.getColor() != null) {
            int color = Color.parseColor(theme.getColor());
            binding.appbarBackground.setBackgroundColor(color);

            StatusBarDesigner statusBarDesigner = new StatusBarDesigner();
            statusBarDesigner.setStatusBarColor(this, ColorHelper.darkColor(color));
        }
    }

    public void setupTheme() {
        setupInfo(theme.getTitle(), theme.getDescription(), theme.getIcon());
    }

    public void setupInfo(String name, String description) {
        setupInfo(name, description, null);
    }

    public void setupInfo(String name, String description, String image) {
        setTitle(name);
        binding.setDescription(description);
        binding.setImage(image);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        } else {
            supportFinishAfterTransition();
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onViolationSelected(Violation violation) {
        setupInfo(violation.getName(), violation.getDescription(), null);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, createFragmentForViolation(violation))
                .addToBackStack(null)
                .commit();
    }

    private Fragment createFragmentForViolation(Violation violation) {
        Fragment fragment;
        if(violation.getCategories() != null
        && violation.getCategories().contains(new ViolationCategory(ViolationCategory.Category.InternetCrime))) {
            fragment = InternetViolationFragment.newInstance(violation);
        } else {
            fragment = ViolationDetailsFragment.newInstance(violation);
        }
        return fragment;
    }
}
