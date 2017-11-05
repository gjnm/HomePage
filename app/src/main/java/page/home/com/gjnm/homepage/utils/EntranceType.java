package page.home.com.gjnm.homepage.utils;

public enum EntranceType {
    TOTAL("to"),
    DIAGNOSTIC("dgs"),
    DEEP_SAVER("ds"),
    NOTIFICATION_SAVER("ns"),
    LANDING_PAGE("lp"),
    TRASH_CLEAN("tc"),
    CPU_TEMP("ct");

    private final String mName;

    EntranceType(String reportKey) {
        mName = reportKey;
    }

    public String getName() {
        return mName;
    }
}
