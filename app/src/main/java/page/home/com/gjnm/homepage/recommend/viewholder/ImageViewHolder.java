package page.home.com.gjnm.homepage.recommend.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import page.home.com.gjnm.homepage.R;


/**
 * Created by gaojian12 on 17/10/16.
 */

public class ImageViewHolder extends BaseCardViewHolder {

    public static final int HOLDER_KEY = OpViewHolderFactory.IMAGE_VIEWHOLDER;

    public ImageView imageOne;
    public ImageView imageTwo;
    public ImageView imageThree;
    public ImageView imageFour;
    public TextView title;

    public ImageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public View initView() {
        imageOne = (ImageView) itemView.findViewById(R.id.image_one);
        imageTwo = (ImageView) itemView.findViewById(R.id.image_two);
        imageThree = (ImageView) itemView.findViewById(R.id.image_three);
        imageFour = (ImageView) itemView.findViewById(R.id.image_four);
        title = (TextView) itemView.findViewById(R.id.title);
        return itemView;
    }
}
