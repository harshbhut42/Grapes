package com.example.harshbhut42.grapes_3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class ViewPagerAddapter extends FragmentPagerAdapter {


    public ViewPagerAddapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0: ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;

            case 1: UsersFragment usersFragment = new UsersFragment();
                return usersFragment;

            case 2: AccountFragment accountFragment = new AccountFragment();
                return accountFragment;

            default: return null;


        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "CHATS";

            case 1:
                return "USERS";

            case 2:
                return "ACCOUNT";

            default:
                return null;
        }
    }
}
