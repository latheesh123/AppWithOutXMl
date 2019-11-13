package latheesh.com.appwithoutxml.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import latheesh.com.appwithoutxml.R;

import static latheesh.com.appwithoutxml.utils.Utils.getSelectedItemBackground;

public class RowItems {


    public static CardView getRemainingView(Context context) {
        CardView cardView = new CardView(context);
        cardView.setForeground(getSelectedItemBackground(context));
        cardView.setClickable(true);
        cardView.setElevation(context.getResources().getDimension(R.dimen.card_view_elevation));

        CardView.LayoutParams cardLayoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT);
        cardLayoutParams.setMargins(
                (int) context.getResources().getDimension(R.dimen.card_view_margin),
                (int) context.getResources().getDimension(R.dimen.card_view_margin),
                (int) context.getResources().getDimension(R.dimen.card_view_margin),
                (int) context.getResources().getDimension(R.dimen.card_view_margin));
        cardView.setLayoutParams(cardLayoutParams);

        // create the linear layout view to hold the text view
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // add the media content image view to the linear layout view
        ImageView imageView = new ImageView(context);
        imageView.setId(R.id.row_media_content);
        imageView.setContentDescription(context.getResources().getString(R.string.iv_content_description));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(imageView);

        // add the title text view to the linear layout view
        TextView mTitle = new TextView(context);
        mTitle.setId(R.id.row_title);
        mTitle.setTypeface(null, Typeface.BOLD);
        mTitle.setLines(2);
        mTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTitle.setMaxLines(2);
        mTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mTitle.setPadding(
                (int) context.getResources().getDimension(R.dimen.view_padding),
                (int) context.getResources().getDimension(R.dimen.view_padding),
                (int) context.getResources().getDimension(R.dimen.view_padding),
                (int) context.getResources().getDimension(R.dimen.view_padding));
        linearLayout.addView(mTitle);

        cardView.addView(linearLayout);

        return cardView;
    }



    public static CardView getFirstView(Context context) {
        // create the root view a a card view to host the entire view holder
        CardView cardView = new CardView(context);
        cardView.setForeground(getSelectedItemBackground(context));
        cardView.setClickable(true);
        cardView.setElevation(context.getResources().getDimension(R.dimen.card_view_elevation));
        CardView.LayoutParams cardLayoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT);
        cardLayoutParams.setMargins(
                (int) context.getResources().getDimension(R.dimen.card_view_margin),
                (int) context.getResources().getDimension(R.dimen.card_view_margin),
                (int) context.getResources().getDimension(R.dimen.card_view_margin),
                (int) context.getResources().getDimension(R.dimen.card_view_margin));
        cardView.setLayoutParams(cardLayoutParams);

        // linear layout view to hold the text view
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // image view to the linear layout view
        ImageView imageView = new ImageView(context);
        imageView.setId(R.id.row_media_content);
        imageView.setContentDescription(context.getResources().getString(R.string.iv_content_description));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(imageView);

        // add the title text view to the linear layout view
        TextView mTitle = new TextView(context);
        mTitle.setId(R.id.row_title);
        mTitle.setTypeface(null, Typeface.BOLD);
        mTitle.setLines(1);
        mTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTitle.setMaxLines(1);

        mTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mTitle.setPadding(
                (int) context.getResources().getDimension(R.dimen.view_padding),
                (int) context.getResources().getDimension(R.dimen.view_padding),
                (int) context.getResources().getDimension(R.dimen.view_padding),
                (int) context.getResources().getDimension(R.dimen.view_padding));
        linearLayout.addView(mTitle);

        TextView tv_description = new TextView(context);
        tv_description.setId(R.id.row_description);
        tv_description.setEllipsize(TextUtils.TruncateAt.END);
        tv_description.setLines(2);
        tv_description.setMaxLines(2);
        tv_description.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        tv_description.setPadding(
                (int) context.getResources().getDimension(R.dimen.view_padding),
                (int) context.getResources().getDimension(R.dimen.view_padding),
                (int) context.getResources().getDimension(R.dimen.view_padding),
                (int) context.getResources().getDimension(R.dimen.view_padding));
        linearLayout.addView(tv_description);

        cardView.addView(linearLayout);

        return cardView;
    }




}
