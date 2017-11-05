package page.home.com.gjnm.homepage.recommend.viewholder;

import android.view.View;

/**
 * Created by gaojian12 on 17/11/4.
 */

public class GridViewHolder extends BaseCardViewHolder {
    public static final int HOLDER_KEY = OpViewHolderFactory.GRID_VIEWHOLDER;

    public GridViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public View initView() {

        // 对View 的操作
        // .......
        return itemView;
    }
}
