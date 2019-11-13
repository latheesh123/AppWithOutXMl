package latheesh.com.appwithoutxml.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import latheesh.com.appwithoutxml.R;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Main layout
        CoordinatorLayout coordinatorLayout = new CoordinatorLayout(this);
        coordinatorLayout.setId(R.id.parentclass);
        CoordinatorLayout.LayoutParams coordinatorLayoutParams = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,
                CoordinatorLayout.LayoutParams.MATCH_PARENT);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        Toolbar toolbar = new Toolbar(this);
        toolbar.setId(R.id.toolbar);
        toolbar.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        toolbar.setDrawingCacheBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        toolbar.setElevation(getResources().getDimension(R.dimen.elevation));

        //to solve rendering issues of text in toolbar
        toolbar.setTitle("PersonalCapital");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        toolbar.setPopupTheme(R.style.PopupOverlay);
        linearLayout.addView(toolbar);

        //Fragment view holder
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.fragment_main);
        frameLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        linearLayout.addView(frameLayout);

        coordinatorLayout.addView(linearLayout);

        setContentView(coordinatorLayout, coordinatorLayoutParams);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, new FeedFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Refresh Menu Item
        menu.add(Menu.NONE, R.id.menu_refresh, Menu.NONE, "Refresh")
                .setIcon(R.mipmap.baseline_cached_black_36)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            refreshContent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshContent() {
        FeedFragment mainFragment = (FeedFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);

        if (mainFragment != null) {
            mainFragment.refreshContent();
        }
    }

}
