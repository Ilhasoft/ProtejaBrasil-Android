package br.com.ilhasoft.protejaBrasil.helpers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import br.com.ilhasoft.protejaBrasil.R;

/**
 * Created by johncordeiro on 22/10/15.
 */
public class EmailSender {

    public void sendEmail(Context context, String email, String subject, String name, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        try {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.title_send_opinion_email)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.message_error_send_opinion, Toast.LENGTH_SHORT).show();
        }
    }

}
