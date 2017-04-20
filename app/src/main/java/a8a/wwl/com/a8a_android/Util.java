package a8a.wwl.com.a8a_android;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 4/21/2017.
 */

public class Util {

    public static void savePhone(Context context, String string) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("PhoneNumber", string);
        editor.commit();
    }

    public static String getPhone(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String authToken = pref.getString("PhoneNumber", "");
        return authToken;
    }

    public static void saveToken(Context context, String string) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Token", string);
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String authToken = pref.getString("Token", "");
        return authToken;
    }

}
