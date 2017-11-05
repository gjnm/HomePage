package page.home.com.gjnm.homepage.recommend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import page.home.com.gjnm.homepage.recommend.carditem.OpBaseCardItem;
import page.home.com.gjnm.homepage.recommend.viewholder.BaseCardViewHolder;
import page.home.com.gjnm.homepage.recommend.viewholder.OpViewHolderFactory;

/**
 * Created by gaojian12 on 17/10/8.
 */

public class CardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;

    private List<OpBaseCardItem> cardList = new ArrayList<OpBaseCardItem>();
    private Context mContext;
    private View mHeaderView;

    public CardListAdapter(Context context, List<OpBaseCardItem> data) {
        cardList = data;
        mContext = context;
    }

    public View getmHeaderView() {
        return mHeaderView;
    }

    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        // notifyItemInserted(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new PubuHolder(mHeaderView);
        }
        BaseCardViewHolder viewHolder = OpViewHolderFactory.getViewHolderByKey(viewGroup, viewType);
        viewHolder.initView();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        OpBaseCardItem cardItem;
        if (mHeaderView != null) {
            if (getItemViewType(position) == TYPE_HEADER) {
                return;
            } else {
                cardItem = cardList.get(position - 1);
            }
        } else {
            cardItem = cardList.get(position);
        }
        cardItem.reportShowAndbindView(mContext, viewHolder, this, position);
    }

    @Override
    public int getItemCount() {
        if (mHeaderView != null) {
            return cardList.size() + 1;
        } else {
            return cardList.size();
        }
    }

    // 返回值赋值给onCreateViewHolder的参数 viewType
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null) {
            if (position == 0) {
                return TYPE_HEADER;
            } else {
                return cardList.get(position - 1).getCardViewType();
            }
        } else {
            return cardList.get(position).getCardViewType();

        }
    }

    class PubuHolder extends RecyclerView.ViewHolder {

        public PubuHolder(View itemView) {
            super(itemView);
        }
    }
}
