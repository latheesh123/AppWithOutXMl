package latheesh.com.appwithoutxml.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import latheesh.com.appwithoutxml.R;
import latheesh.com.appwithoutxml.ui.FeedFragment;

public class Utils {

    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }


    public static String formatDateTime(String dateString) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        Date date;
        try {
            date = simpleDateFormat.parse(dateString);

            simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
            dateString = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static Drawable getSelectedItemBackground(Context context) {
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        Drawable selectedItemDrawable = ta.getDrawable(0);
        ta.recycle();
        return selectedItemDrawable;
    }

}
