package com.handmark.pulltorefresh.util;

import android.util.Log;

public class Logger {
    /**
     * log tag
     */
    private static String tag = "queminye";    // application name
    /**
     * debug or not
     */
    private static boolean debug = true;

    private static Logger instance = new Logger();

    private Logger() {

    }

    public static Logger getLogger() {
        return instance;
    }

    /**
     * set debug
     */
    public static void setDebug(boolean d) {
        debug = d;
    }

    /**
     * log.d
     */
    public static void w(String msg) {
        if (debug) {
            String message = getLogger().createMessage(msg);
            Log.w(tag, message);
        }
    }

    /**
     * log.i
     */
    public static void i(String msg) {
        if (debug) {
            String message = getLogger().createMessage(msg);
            Log.i(tag, message);
        }
    }

    /**
     * log.v
     */
    public static void v(String msg) {
        if (debug) {
            String message = getLogger().createMessage(msg);
            Log.v(tag, message);
        }
    }

    /**
     * log.d
     */
    public static void d(String msg) {
        if (debug) {
            String message = getLogger().createMessage(msg);
            Log.d(tag, message);
        }
    }

    /**
     * log.e
     */
    public static void e(String msg) {
        if (debug) {
            String message = getLogger().createMessage(msg);
            Log.e(tag, message);
        }
    }

    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }

            return "[" + Thread.currentThread().getName() + "(" + Thread.currentThread().getId() + "): "
                    + st.getFileName() + ":" + st.getLineNumber() + "  " + st.getMethodName() + "]";
        }

        return null;
    }

    private String createMessage(String msg) {
        String functionName = getFunctionName();
        String message = (functionName == null ? msg : (functionName + " - " + msg));
        return message;
    }

    /**
     * log.error
     */
    public void error(Exception e) {
        if (debug) {
            StringBuffer sb = new StringBuffer();
            String name = getFunctionName();
            StackTraceElement[] sts = e.getStackTrace();

            if (name != null) {
                sb.append(name + " - " + e + "\r\n");
            } else {
                sb.append(e + "\r\n");
            }
            if (sts != null && sts.length > 0) {
                for (StackTraceElement st : sts) {
                    if (st != null) {
                        sb.append("[ " + st.getFileName() + ":" + st.getLineNumber() + " ]\r\n");
                    }
                }
            }
            Log.e(tag, sb.toString());
        }
    }

    public void setTag(String tag) {
        Logger.tag = tag;
    }
}
