package com.example.andriod.tabiandating;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.andriod.tabiandating.models.FragmentTag;
import com.example.andriod.tabiandating.models.Message;
import com.example.andriod.tabiandating.models.User;
import com.example.andriod.tabiandating.settings.SettingsFragment;
import com.example.andriod.tabiandating.util.PreferenceKeys;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMainActivity,
        BottomNavigationViewEx.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "MainActivity" ;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.home:{
                mFragmentTags.clear();
                mFragmentTags = new ArrayList<>();
                init();
                break;
            }

            case R.id.settings:{
                Log.d(TAG, "onNavigationItemSelected: SettingsFragment");
                if (mSettingsFragment == null){
                    mSettingsFragment = new SettingsFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.main_content_frame, mSettingsFragment, getString(R.string.tag_fragment_settings));
                    transaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_settings));
                    mFragment.add(new FragmentTag(mSettingsFragment, getString(R.string.tag_fragment_settings)));
                }else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_settings));
                    mFragmentTags.add(getString(R.string.tag_fragment_settings));
                }
                settFragmentVisibilities(getString(R.string.tag_fragment_settings));
                break;
            }

            case R.id.agreement:{
                Log.d(TAG, "onNavigationItemSelected: AgreementFragment");
                if (mAgreementFragment == null){
                    mAgreementFragment = new AgreementFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.main_content_frame, mAgreementFragment, getString(R.string.tag_fragment_agreement));
                    transaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_agreement));
                    mFragment.add(new FragmentTag(mAgreementFragment, getString(R.string.tag_fragment_agreement)));
                }else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_agreement));
                    mFragmentTags.add(getString(R.string.tag_fragment_agreement));
                }
                settFragmentVisibilities(getString(R.string.tag_fragment_agreement));
                break;
            }

            case R.id.bottom_nav_home: {
                Log.d(TAG, "onNavigationItemSelected: HomeFragment");
                if ( mHomeFragment == null){
                    mHomeFragment = new HomeFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.main_content_frame, mHomeFragment, getString(R.string.tag_fragment_home));
                    transaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_home));
                    mFragment.add(new FragmentTag( mHomeFragment, getString(R.string.tag_fragment_home)));
                }else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_home));
                    mFragmentTags.add(getString(R.string.tag_fragment_home));
                }
                settFragmentVisibilities(getString(R.string.tag_fragment_home));
                menuItem.setChecked(true);
                break;
            }
            case R.id.bottom_nav_connections: {
                Log.d(TAG, "onNavigationItemSelected: connectingFragment");
                if ( mSavedConnectionsFragment == null){
                    mSavedConnectionsFragment = new SavedConnectionsFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.main_content_frame, mSavedConnectionsFragment, getString(R.string.tag_fragment_saved_connections));
                    transaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_saved_connections));
                    mFragment.add(new FragmentTag(mSavedConnectionsFragment, getString(R.string.tag_fragment_saved_connections)));
                }else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_saved_connections));
                    mFragmentTags.add(getString(R.string.tag_fragment_saved_connections));
                }
                settFragmentVisibilities(getString(R.string.tag_fragment_saved_connections));
                menuItem.setChecked(true);
                break;
            }
            case R.id.bottom_nav_messages: {
                Log.d(TAG, "onNavigationItemSelected: messagesFragment");
                if (  mMessagesFragment == null){
                    mMessagesFragment = new MessagesFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.main_content_frame, mMessagesFragment, getString(R.string.tag_fragment_messages));
                    transaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_messages));
                    mFragment.add(new FragmentTag( mMessagesFragment, getString(R.string.tag_fragment_messages)));
                }else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_messages));
                    mFragmentTags.add(getString(R.string.tag_fragment_messages));
                }
                settFragmentVisibilities(getString(R.string.tag_fragment_messages));
                menuItem.setChecked(true);
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    //constants
    private static int HOME_FRAGMENT = 0;
    private static int CONNECTIONS_FRAGMENT = 1;
    private static int MESSAGES_FRAGMENT = 2;

    //Fragments
    private HomeFragment mHomeFragment;
    private SavedConnectionsFragment mSavedConnectionsFragment;
    private MessagesFragment mMessagesFragment;
    private SettingsFragment mSettingsFragment;
    private ViewProfileFragment mViewProfileFragment;
    private ChatFragment mChatFragment;
    private AgreementFragment mAgreementFragment;
    //widget
    private BottomNavigationViewEx mbottomNavigationViewEx;
    private ImageView mHeaderImage;
    private DrawerLayout mDrawerLayout;

    private ArrayList<String> mFragmentTags = new ArrayList<>();
    private ArrayList<FragmentTag> mFragment = new ArrayList<>();
    private int mExitCount = 0;
    //Var
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbottomNavigationViewEx = findViewById(R.id.bottom_nav_view);
        mbottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        mHeaderImage = headerView.findViewById(R.id.header_image);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setNavigationViewListener();
        isFirstLogin();
        init();
        setmHeaderImage();
        initBottonNavigationView();
    }

    private void initBottonNavigationView(){
        Log.d(TAG, "initBottonNavigationView: initializing the Bottom Navigation View");
        mbottomNavigationViewEx.enableAnimation(false);
    }

    private void init(){
        if (  mHomeFragment == null){
            mHomeFragment = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, mHomeFragment, getString(R.string.tag_fragment_home));
            transaction.commit();
            mFragmentTags.add(getString(R.string.tag_fragment_home));
            mFragment.add(new FragmentTag( mHomeFragment, getString(R.string.tag_fragment_home)));
        }else {
            mFragmentTags.remove(getString(R.string.tag_fragment_home));
            mFragmentTags.add(getString(R.string.tag_fragment_home));
        }
        settFragmentVisibilities(getString(R.string.tag_fragment_home));
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        int backStackCount = mFragmentTags.size();
        if (backStackCount > 1){
            //Navigate Backward
            String topFragmentTag = mFragmentTags.get(backStackCount -1);
            String newTopFragmentTag = mFragmentTags.get(backStackCount -2);
            settFragmentVisibilities(newTopFragmentTag);
            mFragmentTags.remove(topFragmentTag);

            mExitCount = 0;
        }else if (backStackCount == 1){
            String topFragmentTag = mFragmentTags.get(backStackCount -1);
            if (topFragmentTag.equals(getString(R.string.tag_fragment_home))){
                mHomeFragment.scrollToTop();
                mExitCount++;
                Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show();
            }else{
                mExitCount++;
                Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show();
            }
        }
        if (mExitCount >= 2){
            super.onBackPressed();

        }
    }

    private void setNavigationIcon(String tagname){
        Menu menu = mbottomNavigationViewEx.getMenu();
        MenuItem menuItem = null;

        if (tagname.equals(getString(R.string.tag_fragment_home))){
            Log.d(TAG, "setNavigationIcon: home fragment is visible");
            menuItem = menu.getItem(HOME_FRAGMENT);
            menuItem.setChecked(true);
        }
        else if (tagname.equals(getString(R.string.tag_fragment_saved_connections))){
            Log.d(TAG, "setNavigationIcon: connection fragment is visible");
            menuItem = menu.getItem(CONNECTIONS_FRAGMENT);
            menuItem.setChecked(true);
        }
        else if (tagname.equals(getString(R.string.tag_fragment_messages))){
            Log.d(TAG, "setNavigationIcon: message fragment is visible");
            menuItem = menu.getItem(MESSAGES_FRAGMENT);
            menuItem.setChecked(true);
        }
    }

    private void settFragmentVisibilities(String tagname){
        if (tagname.equals(getString(R.string.tag_fragment_home)))
            showBottomNavigation();
        else if (tagname.equals(getString(R.string.tag_fragment_saved_connections)))
            showBottomNavigation();
        else if (tagname.equals(getString(R.string.tag_fragment_messages)))
            showBottomNavigation();
        else if (tagname.equals(getString(R.string.tag_fragment_settings)))
            hideBottomNavigation();
        else if (tagname.equals(getString(R.string.tag_fragment_view_profile)))
            hideBottomNavigation();
        else if (tagname.equals(getString(R.string.tag_fragment_chat)))
            hideBottomNavigation();
        else if (tagname.equals(getString(R.string.tag_fragment_agreement)))
            hideBottomNavigation();


        for (int i=0; i<mFragment.size(); i++){
            if (tagname.equals(mFragment.get(i).getTag())){
                //show
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show((mFragment.get(i).getFragment()));
                transaction.commit();

            }else {
                //dont show
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide((mFragment.get(i).getFragment()));
                transaction.commit();
            }
        }
        setNavigationIcon(tagname);
    }

    private void setNavigationViewListener(){
        Log.d(TAG, "setNavigationViewListener: initializing navigation drawer listener");
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setmHeaderImage(){
        Log.d(TAG, "setmHeaderImage: setting Header Image for navigation drawer");
        Glide.with(this)
                .load(R.drawable.couple)
                .into(mHeaderImage);
    }

    private void hideBottomNavigation(){
        if (mbottomNavigationViewEx != null){
            mbottomNavigationViewEx.setVisibility(View.GONE);
        }
    }

    private void showBottomNavigation(){
        if (mbottomNavigationViewEx != null){
            mbottomNavigationViewEx.setVisibility(View.VISIBLE);
        }
    }
    private void isFirstLogin(){
        Log.d(TAG, "isFirstLogin: checking if this is first login");
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstLogin = preferences.getBoolean(PreferenceKeys.FIRST_TIME_LOGIN, true);
        if (isFirstLogin){
            Log.d(TAG, "isFirstLogin: launching alert dialog");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(getString(R.string.first_time_user_message));
            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d(TAG, "onClick: closing dialog");
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(PreferenceKeys.FIRST_TIME_LOGIN, false);
                    editor.apply();
                    dialogInterface.dismiss();
                }
            });
            alertDialogBuilder.setIcon(R.drawable.tabian_dating);
            alertDialogBuilder.setTitle("");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public void inflateViewProfileFragment(User user) {
        if (mViewProfileFragment != null){
            getSupportFragmentManager().beginTransaction().remove(mViewProfileFragment).commitAllowingStateLoss();
        }

       mViewProfileFragment = new  ViewProfileFragment();
       Bundle args = new Bundle();
       args.putParcelable(getString(R.string.intent_user), user);
       mViewProfileFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content_frame, mViewProfileFragment, getString(R.string.tag_fragment_view_profile));
        transaction.commit();
        mFragmentTags.add(getString(R.string.tag_fragment_view_profile));
        mFragment.add(new FragmentTag(  mViewProfileFragment, getString(R.string.tag_fragment_view_profile)));

        settFragmentVisibilities(getString(R.string.tag_fragment_view_profile));
    }

    @Override
    public void onMessageSelected(Message message) {

        if (mChatFragment != null){
            getSupportFragmentManager().beginTransaction().remove(mChatFragment).commitAllowingStateLoss();
        }
       mChatFragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.intent_message), message);
        mChatFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content_frame, mChatFragment, getString(R.string.tag_fragment_chat));
        transaction.commit();
        mFragmentTags.add(getString(R.string.tag_fragment_chat));
        mFragment.add(new FragmentTag(   mChatFragment, getString(R.string.tag_fragment_chat)));
        settFragmentVisibilities(getString(R.string.tag_fragment_chat));
    }


}
