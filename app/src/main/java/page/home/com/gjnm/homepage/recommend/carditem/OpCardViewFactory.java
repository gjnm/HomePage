package page.home.com.gjnm.homepage.recommend.carditem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaojian12 on 17/10/16.
 */

public class OpCardViewFactory {

    public static final String TAG = "OpCardViewFactory";

    public static final String GRID_CARD_KEY = "grid_item";
    public static final String BIG_ONE_CARD_KEY = "one_big_icon_item";
    public static final String BIG_TWO_CARD_KEY = "two_big_icon_item";
    public static final String BIG_THREE_CARD_KEY = "ohree_big_icon_item";
    public static final String IMAGE_CARD_KEY = "image_card_item";


    /**
     * 卡片key - 实例映射表
     */
    private static final Map<String, Class<? extends OpBaseCardItem>> keyCardInstanceMap =
            new HashMap<String, Class<? extends OpBaseCardItem>>();

    static {
        keyCardInstanceMap.put(GRID_CARD_KEY, GridCardView.class);
        keyCardInstanceMap.put(BIG_ONE_CARD_KEY, OneCardView.class);
        keyCardInstanceMap.put(BIG_TWO_CARD_KEY, TwoCardView.class);
        keyCardInstanceMap.put(BIG_THREE_CARD_KEY, ThreeCardView.class);
        keyCardInstanceMap.put(IMAGE_CARD_KEY, ImageCardView.class);
    }

    public static OpBaseCardItem getCardItem(String cardKey) {
        Class<? extends OpBaseCardItem> itemClass = keyCardInstanceMap.get(cardKey);
        if (itemClass != null) {
            try {
                return itemClass.newInstance();
            } catch (Exception e) {
            }
        }
        return null;
    }

}
