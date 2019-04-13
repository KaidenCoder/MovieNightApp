package an.kurosaki.movienight.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import an.kurosaki.movienight.R;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private MoviesViewPager movieFragment;
    private TvShowViewPager tvShowFragment;

    public ViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MoviesViewPager();
        } else {
            return new TvShowViewPager();
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        switch (position) {
            case 0:
                movieFragment = (MoviesViewPager) createdFragment;
                break;
            case 1:
                tvShowFragment = (TvShowViewPager) createdFragment;
                break;
        }
        return createdFragment;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void refresh(int position){
        switch (position) {
            case 0:
                movieFragment.resetResultsPage();
                break;
            case 1:
                tvShowFragment.resetResultsPage();
                break;
        }
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.filtered_movies_tab_title);
        } else {
            return context.getString(R.string.filtered_tv_shows_tab_title);
        }
    }

}

