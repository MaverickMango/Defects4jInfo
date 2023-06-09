package org.apache.commons.lang3.time;


public class FastDateFormat extends java.text.Format {
    private static final long serialVersionUID = 1L;

    public static final int FULL = java.text.DateFormat.FULL;

    public static final int LONG = java.text.DateFormat.LONG;

    public static final int MEDIUM = java.text.DateFormat.MEDIUM;

    public static final int SHORT = java.text.DateFormat.SHORT;

    private static final org.apache.commons.lang3.time.FormatCache<org.apache.commons.lang3.time.FastDateFormat> cache = new org.apache.commons.lang3.time.FormatCache<org.apache.commons.lang3.time.FastDateFormat>() {
        @java.lang.Override
        protected org.apache.commons.lang3.time.FastDateFormat createInstance(java.lang.String pattern, java.util.TimeZone timeZone, java.util.Locale locale) {
            return new org.apache.commons.lang3.time.FastDateFormat(pattern, timeZone, locale);
        }
    };

    private static java.util.concurrent.ConcurrentMap<org.apache.commons.lang3.time.FastDateFormat.TimeZoneDisplayKey, java.lang.String> cTimeZoneDisplayCache = new java.util.concurrent.ConcurrentHashMap<org.apache.commons.lang3.time.FastDateFormat.TimeZoneDisplayKey, java.lang.String>(7);

    private final java.lang.String mPattern;

    private final java.util.TimeZone mTimeZone;

    private final java.util.Locale mLocale;

    private transient org.apache.commons.lang3.time.FastDateFormat.Rule[] mRules;

    private transient int mMaxLengthEstimate;

    public static org.apache.commons.lang3.time.FastDateFormat getInstance() {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getDateTimeInstance(org.apache.commons.lang3.time.FastDateFormat.SHORT, org.apache.commons.lang3.time.FastDateFormat.SHORT, null, null);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getInstance(java.lang.String pattern) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getInstance(pattern, null, null);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getInstance(java.lang.String pattern, java.util.TimeZone timeZone) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getInstance(pattern, timeZone, null);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getInstance(java.lang.String pattern, java.util.Locale locale) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getInstance(pattern, null, locale);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getInstance(java.lang.String pattern, java.util.TimeZone timeZone, java.util.Locale locale) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getInstance(pattern, timeZone, locale);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getDateInstance(int style) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getDateTimeInstance(style, null, null, null);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getDateInstance(int style, java.util.Locale locale) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getDateTimeInstance(style, null, null, locale);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getDateInstance(int style, java.util.TimeZone timeZone) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getDateTimeInstance(style, null, timeZone, null);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getDateInstance(int style, java.util.TimeZone timeZone, java.util.Locale locale) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getDateTimeInstance(style, null, timeZone, locale);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getTimeInstance(int style) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getDateTimeInstance(null, style, null, null);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getTimeInstance(int style, java.util.Locale locale) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getDateTimeInstance(null, style, null, locale);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getTimeInstance(int style, java.util.TimeZone timeZone) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getDateTimeInstance(null, style, timeZone, null);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getTimeInstance(int style, java.util.TimeZone timeZone, java.util.Locale locale) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getDateTimeInstance(null, style, timeZone, locale);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getDateTimeInstance(dateStyle, timeStyle, null, null);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, java.util.Locale locale) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getDateTimeInstance(dateStyle, timeStyle, null, locale);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, java.util.TimeZone timeZone) {
        return org.apache.commons.lang3.time.FastDateFormat.getDateTimeInstance(dateStyle, timeStyle, timeZone, null);
    }

    public static org.apache.commons.lang3.time.FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, java.util.TimeZone timeZone, java.util.Locale locale) {
        return org.apache.commons.lang3.time.FastDateFormat.cache.getDateTimeInstance(dateStyle, timeStyle, timeZone, locale);
    }

    static java.lang.String getTimeZoneDisplay(java.util.TimeZone tz, boolean daylight, int style, java.util.Locale locale) {
        org.apache.commons.lang3.time.FastDateFormat.TimeZoneDisplayKey key = new org.apache.commons.lang3.time.FastDateFormat.TimeZoneDisplayKey(tz, daylight, style, locale);
        java.lang.String value = org.apache.commons.lang3.time.FastDateFormat.cTimeZoneDisplayCache.get(key);
        if (value == null) {
            value = tz.getDisplayName(daylight, style, locale);
            java.lang.String prior = org.apache.commons.lang3.time.FastDateFormat.cTimeZoneDisplayCache.putIfAbsent(key, value);
            if (prior != null) {
                value = prior;
            }
        }
        return value;
    }

    protected FastDateFormat(java.lang.String pattern, java.util.TimeZone timeZone, java.util.Locale locale) {
        mPattern = pattern;
        mTimeZone = timeZone;
        mLocale = locale;
        init();
    }

    private void init() {
        java.util.List<org.apache.commons.lang3.time.FastDateFormat.Rule> rulesList = parsePattern();
        mRules = rulesList.toArray(new org.apache.commons.lang3.time.FastDateFormat.Rule[rulesList.size()]);
        int len = 0;
        for (int i = mRules.length; (--i) >= 0;) {
            len += mRules[i].estimateLength();
        }
        mMaxLengthEstimate = len;
    }

    protected java.util.List<org.apache.commons.lang3.time.FastDateFormat.Rule> parsePattern() {
        java.text.DateFormatSymbols symbols = new java.text.DateFormatSymbols(mLocale);
        java.util.List<org.apache.commons.lang3.time.FastDateFormat.Rule> rules = new java.util.ArrayList<org.apache.commons.lang3.time.FastDateFormat.Rule>();
        java.lang.String[] ERAs = symbols.getEras();
        java.lang.String[] months = symbols.getMonths();
        java.lang.String[] shortMonths = symbols.getShortMonths();
        java.lang.String[] weekdays = symbols.getWeekdays();
        java.lang.String[] shortWeekdays = symbols.getShortWeekdays();
        java.lang.String[] AmPmStrings = symbols.getAmPmStrings();
        int length = mPattern.length();
        int[] indexRef = new int[1];
        for (int i = 0; i < length; i++) {
            indexRef[0] = i;
            java.lang.String token = parseToken(mPattern, indexRef);
            i = indexRef[0];
            int tokenLen = token.length();
            if (tokenLen == 0) {
                break;
            }
            org.apache.commons.lang3.time.FastDateFormat.Rule rule;
            char c = token.charAt(0);
            switch (c) {
                case 'G' :
                    rule = new org.apache.commons.lang3.time.FastDateFormat.TextField(java.util.Calendar.ERA, ERAs);
                    break;
                case 'y' :
                    if (tokenLen == 2) {
                        rule = org.apache.commons.lang3.time.FastDateFormat.TwoDigitYearField.INSTANCE;
                    }else {
                        rule = selectNumberRule(java.util.Calendar.YEAR, (tokenLen < 4 ? 4 : tokenLen));
                    }
                    break;
                case 'M' :
                    if (tokenLen >= 4) {
                        rule = new org.apache.commons.lang3.time.FastDateFormat.TextField(java.util.Calendar.MONTH, months);
                    }else
                        if (tokenLen == 3) {
                            rule = new org.apache.commons.lang3.time.FastDateFormat.TextField(java.util.Calendar.MONTH, shortMonths);
                        }else
                            if (tokenLen == 2) {
                                rule = org.apache.commons.lang3.time.FastDateFormat.TwoDigitMonthField.INSTANCE;
                            }else {
                                rule = org.apache.commons.lang3.time.FastDateFormat.UnpaddedMonthField.INSTANCE;
                            }


                    break;
                case 'd' :
                    rule = selectNumberRule(java.util.Calendar.DAY_OF_MONTH, tokenLen);
                    break;
                case 'h' :
                    rule = new org.apache.commons.lang3.time.FastDateFormat.TwelveHourField(selectNumberRule(java.util.Calendar.HOUR, tokenLen));
                    break;
                case 'H' :
                    rule = selectNumberRule(java.util.Calendar.HOUR_OF_DAY, tokenLen);
                    break;
                case 'm' :
                    rule = selectNumberRule(java.util.Calendar.MINUTE, tokenLen);
                    break;
                case 's' :
                    rule = selectNumberRule(java.util.Calendar.SECOND, tokenLen);
                    break;
                case 'S' :
                    rule = selectNumberRule(java.util.Calendar.MILLISECOND, tokenLen);
                    break;
                case 'E' :
                    rule = new org.apache.commons.lang3.time.FastDateFormat.TextField(java.util.Calendar.DAY_OF_WEEK, (tokenLen < 4 ? shortWeekdays : weekdays));
                    break;
                case 'D' :
                    rule = selectNumberRule(java.util.Calendar.DAY_OF_YEAR, tokenLen);
                    break;
                case 'F' :
                    rule = selectNumberRule(java.util.Calendar.DAY_OF_WEEK_IN_MONTH, tokenLen);
                    break;
                case 'w' :
                    rule = selectNumberRule(java.util.Calendar.WEEK_OF_YEAR, tokenLen);
                    break;
                case 'W' :
                    rule = selectNumberRule(java.util.Calendar.WEEK_OF_MONTH, tokenLen);
                    break;
                case 'a' :
                    rule = new org.apache.commons.lang3.time.FastDateFormat.TextField(java.util.Calendar.AM_PM, AmPmStrings);
                    break;
                case 'k' :
                    rule = new org.apache.commons.lang3.time.FastDateFormat.TwentyFourHourField(selectNumberRule(java.util.Calendar.HOUR_OF_DAY, tokenLen));
                    break;
                case 'K' :
                    rule = selectNumberRule(java.util.Calendar.HOUR, tokenLen);
                    break;
                case 'z' :
                    if (tokenLen >= 4) {
                        rule = new org.apache.commons.lang3.time.FastDateFormat.TimeZoneNameRule(mTimeZone, mLocale, java.util.TimeZone.LONG);
                    }else {
                        rule = new org.apache.commons.lang3.time.FastDateFormat.TimeZoneNameRule(mTimeZone, mLocale, java.util.TimeZone.SHORT);
                    }
                    break;
                case 'Z' :
                    if (tokenLen == 1) {
                        rule = org.apache.commons.lang3.time.FastDateFormat.TimeZoneNumberRule.INSTANCE_NO_COLON;
                    }else {
                        rule = org.apache.commons.lang3.time.FastDateFormat.TimeZoneNumberRule.INSTANCE_COLON;
                    }
                    break;
                case '\'' :
                    java.lang.String sub = token.substring(1);
                    if ((sub.length()) == 1) {
                        rule = new org.apache.commons.lang3.time.FastDateFormat.CharacterLiteral(sub.charAt(0));
                    }else {
                        rule = new org.apache.commons.lang3.time.FastDateFormat.StringLiteral(sub);
                    }
                    break;
                default :
                    throw new java.lang.IllegalArgumentException(("Illegal pattern component: " + token));
            }
            rules.add(rule);
        }
        return rules;
    }

    protected java.lang.String parseToken(java.lang.String pattern, int[] indexRef) {
        java.lang.StringBuilder buf = new java.lang.StringBuilder();
        int i = indexRef[0];
        int length = pattern.length();
        char c = pattern.charAt(i);
        if (((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z'))) {
            buf.append(c);
            while ((i + 1) < length) {
                char peek = pattern.charAt((i + 1));
                if (peek == c) {
                    buf.append(c);
                    i++;
                }else {
                    break;
                }
            } 
        }else {
            buf.append('\'');
            boolean inLiteral = false;
            for (; i < length; i++) {
                c = pattern.charAt(i);
                if (c == '\'') {
                    if (((i + 1) < length) && ((pattern.charAt((i + 1))) == '\'')) {
                        i++;
                        buf.append(c);
                    }else {
                        inLiteral = !inLiteral;
                    }
                }else
                    if ((!inLiteral) && (((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')))) {
                        i--;
                        break;
                    }else {
                        buf.append(c);
                    }

            }
        }
        indexRef[0] = i;
        return buf.toString();
    }

    protected org.apache.commons.lang3.time.FastDateFormat.NumberRule selectNumberRule(int field, int padding) {
        switch (padding) {
            case 1 :
                return new org.apache.commons.lang3.time.FastDateFormat.UnpaddedNumberField(field);
            case 2 :
                return new org.apache.commons.lang3.time.FastDateFormat.TwoDigitNumberField(field);
            default :
                return new org.apache.commons.lang3.time.FastDateFormat.PaddedNumberField(field, padding);
        }
    }

    @java.lang.Override
    public java.lang.StringBuffer format(java.lang.Object obj, java.lang.StringBuffer toAppendTo, java.text.FieldPosition pos) {
        if (obj instanceof java.util.Date) {
            return format(((java.util.Date) (obj)), toAppendTo);
        }else
            if (obj instanceof java.util.Calendar) {
                return format(((java.util.Calendar) (obj)), toAppendTo);
            }else
                if (obj instanceof java.lang.Long) {
                    return format(((java.lang.Long) (obj)).longValue(), toAppendTo);
                }else {
                    throw new java.lang.IllegalArgumentException(("Unknown class: " + (obj == null ? "<null>" : obj.getClass().getName())));
                }


    }

    public java.lang.String format(long millis) {
        return format(new java.util.Date(millis));
    }

    public java.lang.String format(java.util.Date date) {
        java.util.Calendar c = new java.util.GregorianCalendar(mTimeZone, mLocale);
        c.setTime(date);
        return applyRules(c, new java.lang.StringBuffer(mMaxLengthEstimate)).toString();
    }

    public java.lang.String format(java.util.Calendar calendar) {
        return format(calendar, new java.lang.StringBuffer(mMaxLengthEstimate)).toString();
    }

    public java.lang.StringBuffer format(long millis, java.lang.StringBuffer buf) {
        return format(new java.util.Date(millis), buf);
    }

    public java.lang.StringBuffer format(java.util.Date date, java.lang.StringBuffer buf) {
        java.util.Calendar c = new java.util.GregorianCalendar(mTimeZone, mLocale);
        c.setTime(date);
        return applyRules(c, buf);
    }

    public java.lang.StringBuffer format(java.util.Calendar calendar, java.lang.StringBuffer buf) {
        return applyRules(calendar, buf);
    }

    protected java.lang.StringBuffer applyRules(java.util.Calendar calendar, java.lang.StringBuffer buf) {
        for (org.apache.commons.lang3.time.FastDateFormat.Rule rule : mRules) {
            rule.appendTo(buf, calendar);
        }
        return buf;
    }

    @java.lang.Override
    public java.lang.Object parseObject(java.lang.String source, java.text.ParsePosition pos) {
        pos.setIndex(0);
        pos.setErrorIndex(0);
        return null;
    }

    public java.lang.String getPattern() {
        return mPattern;
    }

    public java.util.TimeZone getTimeZone() {
        return mTimeZone;
    }

    public java.util.Locale getLocale() {
        return mLocale;
    }

    public int getMaxLengthEstimate() {
        return mMaxLengthEstimate;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        if ((obj instanceof org.apache.commons.lang3.time.FastDateFormat) == false) {
            return false;
        }
        org.apache.commons.lang3.time.FastDateFormat other = ((org.apache.commons.lang3.time.FastDateFormat) (obj));
        return ((mPattern.equals(other.mPattern)) && (mTimeZone.equals(other.mTimeZone))) && (mLocale.equals(other.mLocale));
    }

    @java.lang.Override
    public int hashCode() {
        return (mPattern.hashCode()) + (13 * ((mTimeZone.hashCode()) + (13 * (mLocale.hashCode()))));
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ("FastDateFormat[" + (mPattern)) + "]";
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
        in.defaultReadObject();
        init();
    }

    private interface Rule {
        int estimateLength();

        void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar);
    }

    private interface NumberRule extends org.apache.commons.lang3.time.FastDateFormat.Rule {
        void appendTo(java.lang.StringBuffer buffer, int value);
    }

    private static class CharacterLiteral implements org.apache.commons.lang3.time.FastDateFormat.Rule {
        private final char mValue;

        CharacterLiteral(char value) {
            mValue = value;
        }

        public int estimateLength() {
            return 1;
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            buffer.append(mValue);
        }
    }

    private static class StringLiteral implements org.apache.commons.lang3.time.FastDateFormat.Rule {
        private final java.lang.String mValue;

        StringLiteral(java.lang.String value) {
            mValue = value;
        }

        public int estimateLength() {
            return mValue.length();
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            buffer.append(mValue);
        }
    }

    private static class TextField implements org.apache.commons.lang3.time.FastDateFormat.Rule {
        private final int mField;

        private final java.lang.String[] mValues;

        TextField(int field, java.lang.String[] values) {
            mField = field;
            mValues = values;
        }

        public int estimateLength() {
            int max = 0;
            for (int i = mValues.length; (--i) >= 0;) {
                int len = mValues[i].length();
                if (len > max) {
                    max = len;
                }
            }
            return max;
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            buffer.append(mValues[calendar.get(mField)]);
        }
    }

    private static class UnpaddedNumberField implements org.apache.commons.lang3.time.FastDateFormat.NumberRule {
        private final int mField;

        UnpaddedNumberField(int field) {
            mField = field;
        }

        public int estimateLength() {
            return 4;
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            appendTo(buffer, calendar.get(mField));
        }

        public final void appendTo(java.lang.StringBuffer buffer, int value) {
            if (value < 10) {
                buffer.append(((char) (value + '0')));
            }else
                if (value < 100) {
                    buffer.append(((char) ((value / 10) + '0')));
                    buffer.append(((char) ((value % 10) + '0')));
                }else {
                    buffer.append(java.lang.Integer.toString(value));
                }

        }
    }

    private static class UnpaddedMonthField implements org.apache.commons.lang3.time.FastDateFormat.NumberRule {
        static final org.apache.commons.lang3.time.FastDateFormat.UnpaddedMonthField INSTANCE = new org.apache.commons.lang3.time.FastDateFormat.UnpaddedMonthField();

        UnpaddedMonthField() {
            super();
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            appendTo(buffer, ((calendar.get(java.util.Calendar.MONTH)) + 1));
        }

        public final void appendTo(java.lang.StringBuffer buffer, int value) {
            if (value < 10) {
                buffer.append(((char) (value + '0')));
            }else {
                buffer.append(((char) ((value / 10) + '0')));
                buffer.append(((char) ((value % 10) + '0')));
            }
        }
    }

    private static class PaddedNumberField implements org.apache.commons.lang3.time.FastDateFormat.NumberRule {
        private final int mField;

        private final int mSize;

        PaddedNumberField(int field, int size) {
            if (size < 3) {
                throw new java.lang.IllegalArgumentException();
            }
            mField = field;
            mSize = size;
        }

        public int estimateLength() {
            return 4;
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            appendTo(buffer, calendar.get(mField));
        }

        public final void appendTo(java.lang.StringBuffer buffer, int value) {
            if (value < 100) {
                for (int i = mSize; (--i) >= 2;) {
                    buffer.append('0');
                }
                buffer.append(((char) ((value / 10) + '0')));
                buffer.append(((char) ((value % 10) + '0')));
            }else {
                int digits;
                if (value < 1000) {
                    digits = 3;
                }else {
                    org.apache.commons.lang3.Validate.isTrue((value > (-1)), "Negative values should not be possible", value);
                    digits = java.lang.Integer.toString(value).length();
                }
                for (int i = mSize; (--i) >= digits;) {
                    buffer.append('0');
                }
                buffer.append(java.lang.Integer.toString(value));
            }
        }
    }

    private static class TwoDigitNumberField implements org.apache.commons.lang3.time.FastDateFormat.NumberRule {
        private final int mField;

        TwoDigitNumberField(int field) {
            mField = field;
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            appendTo(buffer, calendar.get(mField));
        }

        public final void appendTo(java.lang.StringBuffer buffer, int value) {
            if (value < 100) {
                buffer.append(((char) ((value / 10) + '0')));
                buffer.append(((char) ((value % 10) + '0')));
            }else {
                buffer.append(java.lang.Integer.toString(value));
            }
        }
    }

    private static class TwoDigitYearField implements org.apache.commons.lang3.time.FastDateFormat.NumberRule {
        static final org.apache.commons.lang3.time.FastDateFormat.TwoDigitYearField INSTANCE = new org.apache.commons.lang3.time.FastDateFormat.TwoDigitYearField();

        TwoDigitYearField() {
            super();
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            appendTo(buffer, ((calendar.get(java.util.Calendar.YEAR)) % 100));
        }

        public final void appendTo(java.lang.StringBuffer buffer, int value) {
            buffer.append(((char) ((value / 10) + '0')));
            buffer.append(((char) ((value % 10) + '0')));
        }
    }

    private static class TwoDigitMonthField implements org.apache.commons.lang3.time.FastDateFormat.NumberRule {
        static final org.apache.commons.lang3.time.FastDateFormat.TwoDigitMonthField INSTANCE = new org.apache.commons.lang3.time.FastDateFormat.TwoDigitMonthField();

        TwoDigitMonthField() {
            super();
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            appendTo(buffer, ((calendar.get(java.util.Calendar.MONTH)) + 1));
        }

        public final void appendTo(java.lang.StringBuffer buffer, int value) {
            buffer.append(((char) ((value / 10) + '0')));
            buffer.append(((char) ((value % 10) + '0')));
        }
    }

    private static class TwelveHourField implements org.apache.commons.lang3.time.FastDateFormat.NumberRule {
        private final org.apache.commons.lang3.time.FastDateFormat.NumberRule mRule;

        TwelveHourField(org.apache.commons.lang3.time.FastDateFormat.NumberRule rule) {
            mRule = rule;
        }

        public int estimateLength() {
            return mRule.estimateLength();
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            int value = calendar.get(java.util.Calendar.HOUR);
            if (value == 0) {
                value = (calendar.getLeastMaximum(java.util.Calendar.HOUR)) + 1;
            }
            mRule.appendTo(buffer, value);
        }

        public void appendTo(java.lang.StringBuffer buffer, int value) {
            mRule.appendTo(buffer, value);
        }
    }

    private static class TwentyFourHourField implements org.apache.commons.lang3.time.FastDateFormat.NumberRule {
        private final org.apache.commons.lang3.time.FastDateFormat.NumberRule mRule;

        TwentyFourHourField(org.apache.commons.lang3.time.FastDateFormat.NumberRule rule) {
            mRule = rule;
        }

        public int estimateLength() {
            return mRule.estimateLength();
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            int value = calendar.get(java.util.Calendar.HOUR_OF_DAY);
            if (value == 0) {
                value = (calendar.getMaximum(java.util.Calendar.HOUR_OF_DAY)) + 1;
            }
            mRule.appendTo(buffer, value);
        }

        public void appendTo(java.lang.StringBuffer buffer, int value) {
            mRule.appendTo(buffer, value);
        }
    }

    private static class TimeZoneNameRule implements org.apache.commons.lang3.time.FastDateFormat.Rule {
        private final java.util.TimeZone mTimeZone;

        private final java.lang.String mStandard;

        private final java.lang.String mDaylight;

        TimeZoneNameRule(java.util.TimeZone timeZone, java.util.Locale locale, int style) {
            mTimeZone = timeZone;
            mStandard = org.apache.commons.lang3.time.FastDateFormat.getTimeZoneDisplay(timeZone, false, style, locale);
            mDaylight = org.apache.commons.lang3.time.FastDateFormat.getTimeZoneDisplay(timeZone, true, style, locale);
        }

        public int estimateLength() {
            return java.lang.Math.max(mStandard.length(), mDaylight.length());
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            if ((mTimeZone.useDaylightTime()) && ((calendar.get(java.util.Calendar.DST_OFFSET)) != 0)) {
                buffer.append(mDaylight);
            }else {
                buffer.append(mStandard);
            }
        }
    }

    private static class TimeZoneNumberRule implements org.apache.commons.lang3.time.FastDateFormat.Rule {
        static final org.apache.commons.lang3.time.FastDateFormat.TimeZoneNumberRule INSTANCE_COLON = new org.apache.commons.lang3.time.FastDateFormat.TimeZoneNumberRule(true);

        static final org.apache.commons.lang3.time.FastDateFormat.TimeZoneNumberRule INSTANCE_NO_COLON = new org.apache.commons.lang3.time.FastDateFormat.TimeZoneNumberRule(false);

        final boolean mColon;

        TimeZoneNumberRule(boolean colon) {
            mColon = colon;
        }

        public int estimateLength() {
            return 5;
        }

        public void appendTo(java.lang.StringBuffer buffer, java.util.Calendar calendar) {
            int offset = (calendar.get(java.util.Calendar.ZONE_OFFSET)) + (calendar.get(java.util.Calendar.DST_OFFSET));
            if (offset < 0) {
                buffer.append('-');
                offset = -offset;
            }else {
                buffer.append('+');
            }
            int hours = offset / ((60 * 60) * 1000);
            buffer.append(((char) ((hours / 10) + '0')));
            buffer.append(((char) ((hours % 10) + '0')));
            if (mColon) {
                buffer.append(':');
            }
            int minutes = (offset / (60 * 1000)) - (60 * hours);
            buffer.append(((char) ((minutes / 10) + '0')));
            buffer.append(((char) ((minutes % 10) + '0')));
        }
    }

    private static class TimeZoneDisplayKey {
        private final java.util.TimeZone mTimeZone;

        private final int mStyle;

        private final java.util.Locale mLocale;

        TimeZoneDisplayKey(java.util.TimeZone timeZone, boolean daylight, int style, java.util.Locale locale) {
            mTimeZone = timeZone;
            if (daylight) {
                style |= -2147483648;
            }
            mStyle = style;
            mLocale = locale;
        }

        @java.lang.Override
        public int hashCode() {
            return ((((mStyle) * 31) + (mLocale.hashCode())) * 31) + (mTimeZone.hashCode());
        }

        @java.lang.Override
        public boolean equals(java.lang.Object obj) {
            if ((this) == obj) {
                return true;
            }
            if (obj instanceof org.apache.commons.lang3.time.FastDateFormat.TimeZoneDisplayKey) {
                org.apache.commons.lang3.time.FastDateFormat.TimeZoneDisplayKey other = ((org.apache.commons.lang3.time.FastDateFormat.TimeZoneDisplayKey) (obj));
                return ((mTimeZone.equals(other.mTimeZone)) && ((mStyle) == (other.mStyle))) && (mLocale.equals(other.mLocale));
            }
            return false;
        }
    }
}

