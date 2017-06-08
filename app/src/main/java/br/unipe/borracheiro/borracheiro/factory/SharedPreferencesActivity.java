package br.unipe.borracheiro.borracheiro.factory;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import br.unipe.borracheiro.borracheiro.core.Constants;

/**
 * Created by Andrey on 07/06/2017.
 */

public class SharedPreferencesActivity {

    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getInstance(Context context){

        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, context.MODE_PRIVATE);
        }

        return sharedPreferences;
    }

    public static void set(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);

        editor.commit();
    }

    public static String get(Context context, String preferenceName, String defaultValue) {
        return getInstance(context).getString(preferenceName, defaultValue);
    }

    public static void remove(Context context, String preferenceName){
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(preferenceName);

        editor.commit();
    }

}
