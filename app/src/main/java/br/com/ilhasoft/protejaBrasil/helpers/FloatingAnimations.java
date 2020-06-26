package br.com.ilhasoft.protejaBrasil.helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.DrawableRes;

import com.github.clans.fab.FloatingActionMenu;

/**
 * Created by johncordeiro on 10/10/15.
 */
public class FloatingAnimations {

    public void setSwitchImageAnimation(final FloatingActionMenu floatingActionMenu
            , final @DrawableRes int openImage, final @DrawableRes int closeImage) {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(floatingActionMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(floatingActionMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(floatingActionMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(floatingActionMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                floatingActionMenu.getMenuIconView().setImageResource(floatingActionMenu.isOpened()
                        ? openImage : closeImage);
            }
        });

        animatorSet.play(scaleOutX).with(scaleOutY);
        animatorSet.play(scaleInX).with(scaleInY).after(scaleOutX);
        animatorSet.setInterpolator(new OvershootInterpolator(2));

        floatingActionMenu.setIconToggleAnimatorSet(animatorSet);
    }

}
