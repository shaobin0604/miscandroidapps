package cn.yo2.aquarium.example.bitmapvideooutput;

import static android.util.Log.DEBUG;
import static android.util.Log.ERROR;
import static android.util.Log.INFO;
import static android.util.Log.WARN;
import static android.util.Log.VERBOSE;
/**
 * This class defines the Logger
 */

public final class Log {
	private static final String TAG = "BitmapVideoOutput";

	private static final boolean USE_DETAIL_LOG = true;	
	private static final boolean USE_IS_LOGGABLE = false;

	@SuppressWarnings(value="all")
	private static boolean isLoggable(int level) {
		return !USE_IS_LOGGABLE || android.util.Log.isLoggable(TAG, level);
	}

	private Log() {
	}

	private static String buildMsg(String msg) {
		StringBuilder buffer = new StringBuilder();

		if (USE_DETAIL_LOG) {
			final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
			
			buffer.append("[ ");
			buffer.append(Thread.currentThread().getName());
			buffer.append(": ");
			buffer.append(stackTraceElement.getFileName());
			buffer.append(": ");
			buffer.append(stackTraceElement.getLineNumber());
			buffer.append(" ] - ");
			buffer.append(stackTraceElement.getMethodName());
		}

		buffer.append(" - ");

		buffer.append(msg);

		return buffer.toString();
	}

	public static void i(String msg) {
		if (isLoggable(INFO)) {
			android.util.Log.i(TAG, buildMsg(msg));
		}
	}

	public static void e(String msg) {
		if (isLoggable(ERROR)) {
			android.util.Log.e(TAG, buildMsg(msg));
		}
	}

	public static void e(String msg, Exception e) {
		if (isLoggable(ERROR)) {
			android.util.Log.e(TAG, buildMsg(msg), e);
		}
	}

	public static void d(String msg) {
		if (isLoggable(DEBUG)) {
			android.util.Log.d(TAG, buildMsg(msg));
		}
	}

	public static void v(String msg) {
		if (isLoggable(VERBOSE)) {
			android.util.Log.v(TAG, buildMsg(msg));
		}
	}

	public static void w(String msg) {
		if (isLoggable(VERBOSE)) {
			android.util.Log.w(TAG, msg);
		}
	}

	public static void w(String msg, Exception e) {
		if (isLoggable(WARN)) {
			android.util.Log.w(TAG, msg, e);
		}
	}
}

