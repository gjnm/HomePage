package page.home.com.gjnm.homepage.recommend.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import page.home.com.gjnm.homepage.R;


/**
 * Created by gaojian12 on 17/10/16.
 */

public class OpDefaultViewHolder extends BaseCardViewHolder {

    public static final int HOLDER_KEY = OpViewHolderFactory.DEFAULT_VIEWHOLDER;

    public LinearLayout topView;
    public ImageView icon;
    public TextView title;
    public TextView content;

    public OpDefaultViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public View initView() {
        topView = (LinearLayout) itemView.findViewById(R.id.top);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        title = (TextView) itemView.findViewById(R.id.title);
        content = (TextView) itemView.findViewById(R.id.content);
        return itemView;
    }
}
