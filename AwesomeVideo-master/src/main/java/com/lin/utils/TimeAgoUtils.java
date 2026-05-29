    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;
    private static final String ONE_SECOND_AGO = "\u79d2\u524d";
    private static final String ONE_MINUTE_AGO = "\u5206\u949f\u524d";
    private static final String ONE_HOUR_AGO = "\u5c0f\u65f6\u524d";
    private static final String ONE_DAY_AGO = "\u5929\u524d";
    private static final String ONE_MONTH_AGO = "\u6708\u524d";
    private static final String ONE_YEAR_AGO = "\u5e74\u524d";

    public static String format(Date date) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < 60000L) {
            long seconds = TimeAgoUtils.toSeconds(delta);
            return (seconds <= 0L ? 1L : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 2700000L) {
            long minutes = TimeAgoUtils.toMinutes(delta);
            return (minutes <= 0L ? 1L : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 86400000L) {
            long hours = TimeAgoUtils.toHours(delta);
            return (hours <= 0L ? 1L : hours) + ONE_HOUR_AGO;
        }
        if (delta < 172800000L) {
            return "\u6628\u5929";
        }
        if (delta < 2592000000L) {
            long days = TimeAgoUtils.toDays(delta);
            return (days <= 0L ? 1L : days) + ONE_DAY_AGO;
        }
        if (delta < 29030400000L) {
            long months = TimeAgoUtils.toMonths(delta);
            return (months <= 0L ? 1L : months) + ONE_MONTH_AGO;
        }
        long years = TimeAgoUtils.toYears(delta);
        return (years <= 0L ? 1L : years) + ONE_YEAR_AGO;
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return TimeAgoUtils.toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return TimeAgoUtils.toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return TimeAgoUtils.toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return TimeAgoUtils.toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return TimeAgoUtils.toMonths(date) / 365L;
    }

    public static void main(String[] args) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        Date date = format.parse("2018-05-01 18:35:35");
        System.out.println(TimeAgoUtils.format(date));
    }
}
