package page.home.com.gjnm.homepage.recommend.carditem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import page.home.com.gjnm.homepage.R;
import page.home.com.gjnm.homepage.recommend.CardListAdapter;
import page.home.com.gjnm.homepage.recommend.viewholder.OpDefaultViewHolder;
import page.home.com.gjnm.homepage.recommend.viewholder.OpViewHolderFactory;

/**
 * Created by gaojian12 on 17/10/16.
 */

public class ThreeCardView extends OpBaseCardItem {

    public static final String CARDVIEW_KEY = OpCardViewFactory.BIG_THREE_CARD_KEY;

    private Context mContext;
    private int mPosition;

    @Override
    public int getCardViewType() {
        return OpViewHolderFactory.linkCardAndHolderMap.get(CARDVIEW_KEY);
    }

    @Override
    public String getCardPageType() {
        return CARDVIEW_KEY;
    }

    @Override
    public boolean needShow() {
        return true;
    }

    @Override
    public void bindItemView(Context context, RecyclerView.ViewHolder viewHolder, CardListAdapter
            cardAdapter, int position) {
        mContext = context;
        mPosition = position;
        OpDefaultViewHolder cardViewHolder = (OpDefaultViewHolder) viewHolder;
        cardViewHolder.content.setText("这是大图item的标题");
        cardViewHolder.icon.setImageResource(R.mipmap.item_icon_1);
        cardViewHolder.title.setText("这是大图item的内容。。。。");
        cardViewHolder.topView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealClick();
            }
        });
    }

    private void dealClick() {

    }
}
