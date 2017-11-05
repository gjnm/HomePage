package page.home.com.gjnm.homepage.recommend.carditem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import page.home.com.gjnm.homepage.recommend.CardListAdapter;

/**
 * Created by gaojian12 on 17/10/16.
 */

public abstract class OpBaseCardItem {
    private boolean mFirstCreate = true;
    /**
     * 返回列表展示的UI样式类型
     */
    public abstract int getCardViewType();
    /**
     * 返回列表展示的UI样式类型
     */
    public abstract String getCardPageType();
    /**
     * 判断列表是否可以展示，各列表自行控制
     */
    public boolean needShow() {
        return false;
    }
    /**
     * 等同于Adapter的getView,具体列表控制UI展示
     */
    public abstract void bindItemView(Context context, RecyclerView.ViewHolder viewHolder, CardListAdapter
            cardAdapter, int position);

    public void reportShowAndbindView(Context context, RecyclerView.ViewHolder viewHolder, CardListAdapter
            cardAdapter, int position) {
        // 默认实现，上报功能卡片的创建展示
        if (mFirstCreate) {
            mFirstCreate = false;
        }
        bindItemView(context, viewHolder, cardAdapter, position);
    }

}