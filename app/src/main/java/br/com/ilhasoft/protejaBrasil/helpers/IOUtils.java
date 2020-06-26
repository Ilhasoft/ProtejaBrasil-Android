package br.com.ilhasoft.protejaBrasil.helpers;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by john-mac on 1/11/16.
 */
public class IOUtils {

    public static String loadFileFromAssets(Context context, String filename) {
        try {
            InputStream inputStream = context.getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, "UTF-8");
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
