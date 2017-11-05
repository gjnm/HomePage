package page.home.com.gjnm.homepage.recommend.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gaojian12 on 17/10/13.
 */

public abstract class BaseCardViewHolder extends RecyclerView.ViewHolder {

    public BaseCardViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * 创建list里面item对应的view
     */
    public abstract View initView();

}