package page.home.com.gjnm.homepage.recommend.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import page.home.com.gjnm.homepage.R;
import page.home.com.gjnm.homepage.app.MyApplication;
import page.home.com.gjnm.homepage.recommend.carditem.GridCardView;
import page.home.com.gjnm.homepage.recommend.carditem.ImageCardView;
import page.home.com.gjnm.homepage.recommend.carditem.OneCardView;
import page.home.com.gjnm.homepage.recommend.carditem.ThreeCardView;
import page.home.com.gjnm.homepage.recommend.carditem.TwoCardView;

/**
 * Created by gaojian12 on 17/10/16.
 */

public class OpViewHolderFactory {

    public static final int GRID_VIEWHOLDER = 1;
    public static final int DEFAULT_VIEWHOLDER = 2;
    public static final int IMAGE_VIEWHOLDER = 3;

    public static final Map<String, Integer> linkCardAndHolderMap = new HashMap<String, Integer>();

    static {
        linkCardAndHolderMap.put(OneCardView.CARDVIEW_KEY, OpDefaultViewHolder.HOLDER_KEY);
        linkCardAndHolderMap.put(TwoCardView.CARDVIEW_KEY, OpDefaultViewHolder.HOLDER_KEY);
        linkCardAndHolderMap.put(ThreeCardView.CARDVIEW_KEY, OpDefaultViewHolder.HOLDER_KEY);
        linkCardAndHolderMap.put(GridCardView.CARDVIEW_KEY, GridViewHolder.HOLDER_KEY);
        linkCardAndHolderMap.put(ImageCardView.CARDVIEW_KEY, ImageViewHolder.HOLDER_KEY);
    }

    public static BaseCardViewHolder getViewHolderByKey(ViewGroup viewGroup, int holderKey) {
        LayoutInflater inflater = LayoutInflater.from(MyApplication.getInstance());
        switch (holderKey) {
            case DEFAULT_VIEWHOLDER:
                return new OpDefaultViewHolder(inflater.inflate(R.layout.big_icon_item_view, viewGroup, false));

            case GRID_VIEWHOLDER:
                return new GridViewHolder(inflater.inflate(R.layout.grid_item_view, viewGroup, false));

            case IMAGE_VIEWHOLDER:
                return new ImageViewHolder(inflater.inflate(R.layout.image_item_view, viewGroup, false));
            default:
                break;

        }
        return null;
    }
}
