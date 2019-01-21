package chtgupta.signinui;

import android.content.Context;

public class Utils {

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
