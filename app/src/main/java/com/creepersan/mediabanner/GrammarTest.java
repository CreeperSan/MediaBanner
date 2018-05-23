package com.creepersan.mediabanner;

import com.creepersan.mediabanner.view.bean.BaseBannerItem;

/**
 * Author      : gemvary
 * Time        : 2018-05-22 11:49
 * Description :
 */

public class GrammarTest {

    public void a(){
    }

    interface Collection<E extends BaseBannerItem>{
        void addAll(Collection<? extends E> items);
    }

}
