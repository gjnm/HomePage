package page.home.com.gjnm.homepage.recommend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import page.home.com.gjnm.homepage.recommend.carditem.OpBaseCardItem;
import page.home.com.gjnm.homepage.recommend.carditem.OpCardViewFactory;

/**
 * Created by gaojian12 on 17/10/16.
 */

public class CardFactory {

    /**
     * @return 返回功能卡片列表
     */
    public static List<OpBaseCardItem> getFunctionCardList() {
        return getLocalCardList();
    }

    private static List<OpBaseCardItem> getLocalCardList() {
        List<OpBaseCardItem> cardItems = new LinkedList<OpBaseCardItem>();
        List<Integer> nativeOrderList = OptimizerCardDataSet.getNativeOrderList();
        Set<Integer> visitSet = new HashSet<Integer>();
        for (Integer groupName : nativeOrderList) {
            if (visitSet.contains(groupName)) { // 已经判断过的分组直接跳过，避免重复计算
                continue;
            }
            visitSet.add(groupName);
            String[] keys = OptimizerCardDataSet.getGroupKeys(groupName);
            if (keys == null) {
                continue;
            }
            ArrayList<OpBaseCardItem> cardList = new ArrayList<OpBaseCardItem>();
            for (String key : keys) {
                OpBaseCardItem item = OpCardViewFactory.getCardItem(key);
                if (item != null && item.needShow()) {
                    cardList.add(item);
                }
            }
            if (!cardList.isEmpty() && cardList.size() > 0) {
                shuffle(cardList);   // 随机化
                OpBaseCardItem item = cardList.get(0);
                if (item != null) {
                    cardItems.add(item);
                }
            }
        }
        return cardItems;
    }

    private static void shuffle(ArrayList<OpBaseCardItem> item) {
        if (item == null || item.size() < 2) {
            return;
        }
        Collections.shuffle(item);
    }
}
