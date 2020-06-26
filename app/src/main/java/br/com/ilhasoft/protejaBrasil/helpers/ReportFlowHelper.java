package br.com.ilhasoft.protejaBrasil.helpers;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.ilhasoft.protejaBrasil.R;

/**
 * Created by Dielson Sales on 03/03/16.
 */
public class ReportFlowHelper {

    public static final String RESULT_REGEX = "<return xsi:type=\"xsd:string\">(.*?)</return>";
    public static final String DIGIT_REGEX = "(\\d*\\.)?\\d+";

    public static void redifyAsterisk(Context context, TextView textView) {
        String text = textView.getText().toString();
        textView.setText(Html.fromHtml(replaceWithRedAsterisk(context, text)));
    }

    private static String replaceWithRedAsterisk(Context context, String originalText) {
        int redColor = ContextCompat.getColor(context, R.color.red_text);
        String hexWithAlpha = Integer.toHexString(redColor);
        String hexWithoutAlpha = hexWithAlpha.subSequence(2, 8).toString();
        return originalText.replace("*", "<font color=#" + hexWithoutAlpha + ">*</font>");
    }

    public static String groupCharactersByPattern(String patternValue, String text) {
        Pattern pattern = Pattern.compile(patternValue);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}
