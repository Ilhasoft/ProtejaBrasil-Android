package br.com.ilhasoft.protejaBrasil.managers.animators;

import android.animation.Animator;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ilhasoft on 02/03/16.
 */
public class AccessibilityPinAnimator {

    private int displayHeight;
    private List<ImageView> buttons;
    private List<TextView> labels;
    private ImageView accessibilityMarker;

    public AccessibilityPinAnimator(int displayHeight, List<ImageView> buttons, List<TextView> labels, ImageView accessibilityMarker) {
        this.displayHeight = displayHeight;
        this.buttons = buttons;
        this.labels = labels;
        this.accessibilityMarker = accessibilityMarker;
    }

    public void prepareViews() {
        for (ImageView button : buttons) {
            button.setAlpha(0.0f);
        }
        for (TextView label : labels) {
            label.setAlpha(0.0f);
        }
        accessibilityMarker.setY((displayHeight / 2) * -1);
    }

    public void animatePinDrop() {
        Handler handler = new Handler();
        int smallDelay = 1000; // 1 second
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                accessibilityMarker.animate().translationY(0).setDuration(800).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animateShowOptions();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
            }
        }, smallDelay);
    }

    private void animateShowOptions() {
        for (ImageView button : buttons) {
            button.animate().alpha(1.0f).setDuration(800);
        }
        for (TextView label : labels) {
            label.animate().alpha(1.0f).setDuration(800);
        }
    }
}
