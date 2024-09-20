package com.eldroid.news;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Use the default layout and let Android handle the orientation

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment headlineListFragment = fragmentManager.findFragmentByTag("HEADLINE_LIST_FRAGMENT");

            if (headlineListFragment == null) {
                headlineListFragment = new HeadlineListFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.headlineListContainer, headlineListFragment, "HEADLINE_LIST_FRAGMENT");

                // Check if the newsContentContainer exists before adding NewsContentFragment
                if (findViewById(R.id.newsContentContainer) != null) {
                    Fragment newsContentFragment = new NewsContentFragment();
                    fragmentTransaction.add(R.id.newsContentContainer, newsContentFragment, "NEWS_CONTENT_FRAGMENT");
                }

                fragmentTransaction.commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
