package page.home.com.gjnm.homepage.recommend;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import page.home.com.gjnm.homepage.recommend.carditem.OpCardViewFactory;

/**
 * Created by gaojian12 on 17/10/16.
 */

public class OptimizerCardDataSet {

    /**
     * 按照产品逻辑对功能卡片进行分组
     */
    private static final int ONE_CARD = 1;
    private static final int TWO_CARD = 2;
    private static final int THREE_CARD = 3;
    private static final int GRID_CARD = 4;
    private static final int IMAGE_CARD = 5;

    private static final int UNINSTALL_APKMANAGER_CARD = 101;

    /**
     * 卡片组 － 卡片key 映射（1对多）
     */
    private static final SparseArray<String[]> groupKeysMap = new SparseArray<String[]>();

    static {
        groupKeysMap.append(ONE_CARD, new String[]{OpCardViewFactory.BIG_ONE_CARD_KEY});
        groupKeysMap.append(TWO_CARD, new String[]{OpCardViewFactory.BIG_TWO_CARD_KEY});
        groupKeysMap.append(THREE_CARD, new String[]{OpCardViewFactory.BIG_THREE_CARD_KEY});
        groupKeysMap.append(GRID_CARD, new String[]{OpCardViewFactory.GRID_CARD_KEY});
        groupKeysMap.append(IMAGE_CARD, new String[]{OpCardViewFactory.IMAGE_CARD_KEY});

//        groupKeysMap.append(UNINSTALL_APKMANAGER_CARD, new String[]{
//                OpCardViewFactory.MANAGER_UNINSTALL_CARD_KEY,
//                OpCardViewFactory.APK_MANAGER_CARD_KEY
//        });

    }

    /**
     * 本地默认推荐卡片顺序
     */
    private static final List<Integer> sNativeOrderList;

    static {
        sNativeOrderList = new ArrayList<Integer>();
        sNativeOrderList.add(GRID_CARD);
        sNativeOrderList.add(ONE_CARD);
        sNativeOrderList.add(IMAGE_CARD);
        sNativeOrderList.add(TWO_CARD);
        sNativeOrderList.add(THREE_CARD);
    }

    /**
     * 返回首页推荐卡片列表
     */
    public static List<Integer> getNativeOrderList() {
        return sNativeOrderList;
    }

    /**
     * 根据组名称返回卡片集合
     */
    public static String[] getGroupKeys(int groupName) {
        return groupKeysMap.get(groupName);
    }

}
