package site.zhongkai.ask.utils;

import org.apache.commons.lang3.ArrayUtils;

@SuppressWarnings({"unused","WeakerAccess"})
public class JudgeUtils {

    // 单个判断是否为空
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0 || "null".equalsIgnoreCase(cs.toString().trim()) || isBlank(cs);
    }

    private static boolean isBlank(CharSequence cs) {
        for (int i = 0; i < cs.length(); ++i) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // 多个判断是否存在空
    public static boolean isAnyEmpty(CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) return true;
        for (CharSequence cs : css) if (isEmpty(cs)) return true;
        return false;
    }

}
