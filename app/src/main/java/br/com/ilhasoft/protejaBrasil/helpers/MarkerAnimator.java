package br.com.ilhasoft.protejaBrasil.helpers;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by johncordeiro on 23/10/15.
 */
public class MarkerAnimator {

    public void animateMarker(final Bitmap bitmap, final Marker marker) {
        final Bitmap target = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(target);

        ValueAnimator animator = ValueAnimator.ofFloat(1, 2);
        animator.setDuration(500);

        final Rect originalRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF scaledRect = new RectF();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                scaledRect.set(0, 0, originalRect.right * scale, originalRect.bottom * scale);
                canvas.drawBitmap(bitmap, originalRect, scaledRect, null);
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(target));
            }
        });
        animator.start();
    }

}
