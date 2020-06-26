package br.com.ilhasoft.protejaBrasil.helpers;

import android.content.Context;
import android.widget.EditText;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.support.tool.EditTextValidator;

/**
 * Created by john-mac on 1/9/16.
 */
public class CustomValidator {

    private Context context;
    private EditTextValidator validator;

    public CustomValidator(Context context) {
        this.context = context;
        this.validator = new EditTextValidator();
    }

    public boolean validateEmail(EditText email) {
        if(validator.validateEmail(email, context.getString(R.string.error_message_valid_email))) {
            email.setBackgroundResource(R.drawable.selector_edittext_focus_background);
            return true;
        }
        email.setBackgroundResource(R.drawable.shape_error_background);
        return false;
    }

    public boolean validateText(EditText editText) {
        if(validator.validateEmpty(editText, null)) {
            editText.setBackgroundResource(R.drawable.selector_edittext_focus_background);
            return true;
        }
        editText.setBackgroundResource(R.drawable.shape_error_background);
        return false;
    }

}
