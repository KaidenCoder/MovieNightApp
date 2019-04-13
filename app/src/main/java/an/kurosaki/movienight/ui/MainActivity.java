package an.kurosaki.movienight.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import an.kurosaki.movienight.R;
import an.kurosaki.movienight.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_TAB_POSITION = "CURRENT_TAB_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hook up the custom adapter to the ViewPager
        final ViewPager viewPager = findViewById(R.id.view_pager);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(
                MainActivity.this, getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        // Set current page
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this);
        int currentTabPosition = sharedPrefs.getInt(CURRENT_TAB_POSITION, 0);
        if (currentTabPosition != 0 && currentTabPosition!=1) currentTabPosition = 0;
        viewPager.setCurrentItem(currentTabPosition);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
                if (adapter != null) {
                    adapter.refresh(position);
                }

                // Save the current tab position in SharedPreferences
                SharedPreferences sharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putInt(CURRENT_TAB_POSITION, position);
                editor.commit();
            }
        });

        // Hook up the TabLayout to the ViewPager
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

