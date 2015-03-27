package ar.com.wolox.woloxtrainingmolina.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ar.com.wolox.woloxtrainingmolina.fragments.NewsFragment;
import ar.com.wolox.woloxtrainingmolina.fragments.ProfileFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence mTitles[];
    int mNumbOfTabs;
    int mImgResources[];


    // Constructor del adapter con los atributos necesarios
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb, int mImgResources[]) {
        super(fm);

        this.mTitles = mTitles;
        this.mNumbOfTabs = mNumbOfTabsumb;
        this.mImgResources = mImgResources;

    }

    //Retornamos el fragment correspondiente a la tab que se esta viendo
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // position == 0: la tab de la izquierda
        {
            NewsFragment newsFragment = new NewsFragment();
            return newsFragment;
        }
        // position != 0: la tab de la derecha (se podría cambiar esto para permitir más
        // tabs si llega a ser necesario)
        else
        {
            ProfileFragment profileFragment = new ProfileFragment();
            return profileFragment;
        }


    }

    //Devolvemos el titulo o imagen de la tab correspondiente
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    //Este método no lleva @Override porque es una customizacion, no se hereda
    public int getPageImage(int position) {
        return mImgResources[position];
    }

    @Override
    public int getCount() {
        return mNumbOfTabs;
    }
}
