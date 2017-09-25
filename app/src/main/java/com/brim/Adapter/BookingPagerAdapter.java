package com.brim.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.brim.PagerFragment.Pager1;
import com.brim.PagerFragment.Pager2;

/**
 * Created by bodhidipta on 26/06/17.
 * <!-- * Copyright (c) 2017, The FavouriteTable-->
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class BookingPagerAdapter extends FragmentStatePagerAdapter {

    public BookingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 1;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Pager1();
            case 1:
                return new Pager2();
            default:
                return null;
        }

    }

//    /**
//     * This method may be called by the ViewPager to obtain a title string
//     * to describe the specified page. This method may return null
//     * indicating no title for this page. The default implementation returns
//     * null.
//     *
//     * @param position The position of the title requested
//     * @return A title for the requested page
//     */
//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "Upcoming";
//            case 1:
//                return "Seated";
//            case 2:
//                return "All";
//            default:
//                return "";
//        }
//    }
}
