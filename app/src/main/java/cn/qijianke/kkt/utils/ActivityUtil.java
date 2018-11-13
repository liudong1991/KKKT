package cn.qijianke.kkt.utils;

import android.app.Activity;

import java.util.LinkedList;

public class ActivityUtil {

    private static LinkedList<Activity> stack;

    static {
        stack = new LinkedList<>();
    }

    public static void pop(int flag) {
        while (flag-- > 0) {
            stack.pop().finish();
        }
    }

    public static void clear() {
        for (Activity a : stack) {
            a.finish();
        }
        stack.clear();
    }

    public static void push(Activity activity) {
        stack.push(activity);
    }

}
