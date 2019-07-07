package com.project.nikhil.paytmclone;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chabbal.slidingdotsplash.SlidingSplashView;
import com.project.nikhil.paytmclone.Fragments.BankFragment;
import com.project.nikhil.paytmclone.Fragments.HomeFragment;
import com.project.nikhil.paytmclone.Fragments.MallFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    Fragment selectedfragment=null;
    Toolbar toolbar;
    TextView search_bar;
    ImageView mall_icon,mall_search,mall_bag,mall_emoji,cashback;
    ActionBarDrawerToggle toggle;
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;



    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



          mall_icon=findViewById(R.id.mall_icon);
          toolbar = (Toolbar) findViewById(R.id.toolbar1);
          search_bar=findViewById(R.id.search_bar);
          mall_bag=findViewById(R.id.mall_bag);
          mall_emoji=findViewById(R.id.mall_emoji);
          mall_search=findViewById(R.id.mall_search);
          cashback=findViewById(R.id.cashback);


        ((AppCompatActivity)MainActivity.this).setSupportActionBar(toolbar);
         drawer = (DrawerLayout) findViewById(R.id.drwlyot);
         toggle= new ActionBarDrawerToggle(
                MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
         drawer.addDrawerListener(toggle);
         toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.nav_white);
         toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        toggle.syncState();

        mNavItems.add(new NavItem("Home", "Home", R.drawable.ic_brightness_7_black_24dp));
        mNavItems.add(new NavItem("My Orders", "Shopping, Movie Tickets,Bills", R.drawable.ic_local_mall_black_24dp));
        mNavItems.add(new NavItem("My Passbook", "Wallet,Paytm Payments", R.drawable.ic_account_balance_wallet_black_24dp));
        mNavItems.add(new NavItem("Paytm Reminders", "Set Reminder", R.drawable.ic_date_range_black_24dp));
        mNavItems.add(new NavItem("My Favourite Stores", "Pay Using Paytm", R.drawable.ic_local_grocery_store_black_24dp));
        mNavItems.add(new NavItem("My Loyality Cards", "Loyality Points on Loyality card", R.drawable.ic_award_black_24dp));


        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

    }

    private  BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch(menuItem.getItemId())
            {

                case R.id.nav_home:
                    toggle.setHomeAsUpIndicator(R.drawable.nav_white);
                    toolbar.setVisibility(View.VISIBLE);
                    mall_icon.setVisibility(View.GONE);
                    search_bar.setVisibility(View.VISIBLE);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    selectedfragment=new HomeFragment();
                    toolbar.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.dark_blue));
                    mall_bag.setVisibility(View.GONE);
                    mall_emoji.setVisibility(View.GONE);
                    mall_search.setVisibility(View.GONE);
                    cashback.setVisibility(View.VISIBLE);
                    break;

                case R.id.nav_mall:
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    search_bar.setVisibility(View.GONE);
                    toggle.setHomeAsUpIndicator(R.drawable.nav_black);
                   mall_icon.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    toolbar.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.white));
                    selectedfragment=new MallFragment();
                    mall_bag.setVisibility(View.VISIBLE);
                    mall_emoji.setVisibility(View.VISIBLE);
                    mall_search.setVisibility(View.VISIBLE);
                    cashback.setVisibility(View.GONE);
                    break;
                case R.id.nav_bank:
                    toolbar.setVisibility(View.GONE);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    selectedfragment=new BankFragment();
                    break;
                case R.id.nav_message:
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    toolbar.setVisibility(View.GONE);
                    selectedfragment=new BankFragment();
                    break;
            }

            if(selectedfragment!=null)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
            }

            return true;
        }

    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }



    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }
    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }
             View v=view.findViewById(R.id.divider);
            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.desc);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);
            RelativeLayout list_item=view.findViewById(R.id.list_item);
            if(position==0)
            {
                titleView.setText("Paytm First");
                v.setVisibility(View.GONE);
                subtitleView.setText( "say hello to the good life" );
                iconView.setImageResource(R.drawable.ic_verified_user_black_24dp);
                list_item.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.view));
            }

            else {
                titleView.setText( mNavItems.get(position).mTitle );
                subtitleView.setText( mNavItems.get(position).mSubtitle );
                iconView.setImageResource(mNavItems.get(position).mIcon);
            }

            return view;
        }
    }
}
