/*
 * MIT License
 *
 * Copyright (c) 2018 msemu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.msemu.commons.utils;

/**
 * Created by Weber on 2018/3/16.
 */

import java.io.*;
import java.nio.charset.Charset;

/**
 * Provides a suite of utilities for manipulating strings.
 *
 * @author Frz
 * @version 1.0
 * @since Revision 336
 */
public class StringUtils {

    /**
     * Gets a string padded from the left to <code>length</code> by
     * <code>padchar</code>.
     *
     * @param in      The input string to be padded.
     * @param padchar The character to pad with.
     * @param length  The length to pad to.
     * @return The padded string.
     */
    public static final String getLeftPaddedStr(final String in, final char padchar, final int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int x = in.getBytes().length; x < length; x++) {
            builder.append(padchar);
        }
        builder.append(in);
        return builder.toString();
    }

    public static final int getlength(final String str) {
        byte[] bt = str.getBytes(Charset.forName("BIG5"));
        return bt.length;
    }

    public static final String getRightPaddedStr(final String in, final char padchar, final int length) {
        StringBuilder builder = new StringBuilder(in);
        for (int x = in.getBytes().length; x < length; x++) {
            builder.append(padchar);
        }
        return builder.toString();
    }

    public static final String joinStringFrom(final String arr[], final int start) {
        return joinStringFrom(arr, start, " ");
    }


    public static final String joinStringFrom(final String arr[], final int start, final String sep) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) {
                builder.append(sep);
            }
        }
        return builder.toString();
    }

    public static boolean isNumber(String string) {
        return string.matches("-?\\d+(\\.\\d+)?");
    }

    public static final String makeEnumHumanReadable(final String enumName) {
        StringBuilder builder = new StringBuilder(enumName.length() + 1);
        for (String word : enumName.split("_")) {
            if (word.length() <= 2) {
                builder.append(word); // assume that it's an abbrevation
            } else {
                builder.append(word.charAt(0));
                builder.append(word.substring(1).toLowerCase());
            }
            builder.append(' ');
        }
        return builder.substring(0, enumName.length());
    }

    public static final int countCharacters(final String str, final char chr) {
        int ret = 0;
        for (int i = 0; i < str.getBytes().length; i++) {
            if (str.charAt(i) == chr) {
                ret++;
            }
        }
        return ret;
    }

    public static final String getReadableMillis(long startMillis, long endMillis) {
        StringBuilder sb = new StringBuilder();
        double elapsedSeconds = (endMillis - startMillis) / 1000.0;
        int elapsedSecs = ((int) elapsedSeconds) % 60;
        int elapsedMinutes = (int) (elapsedSeconds / 60.0);
        int elapsedMins = elapsedMinutes % 60;
        int elapsedHrs = elapsedMinutes / 60;
        int elapsedHours = elapsedHrs % 24;
        int elapsedDays = elapsedHrs / 24;
        if (elapsedDays > 0) {
            boolean mins = elapsedHours > 0;
            sb.append(elapsedDays);
            sb.append(" day" + (elapsedDays > 1 ? "s" : "") + (mins ? ", " : "."));
            if (mins) {
                boolean secs = elapsedMins > 0;
                if (!secs) {
                    sb.append("and ");
                }
                sb.append(elapsedHours);
                sb.append(" hour" + (elapsedHours > 1 ? "s" : "") + (secs ? ", " : "."));
                if (secs) {
                    boolean millis = elapsedSecs > 0;
                    if (!millis) {
                        sb.append("and ");
                    }
                    sb.append(elapsedMins);
                    sb.append(" minute" + (elapsedMins > 1 ? "s" : "") + (millis ? ", " : "."));
                    if (millis) {
                        sb.append("and ");
                        sb.append(elapsedSecs);
                        sb.append(" second" + (elapsedSecs > 1 ? "s" : "") + ".");
                    }
                }
            }
        } else if (elapsedHours > 0) {
            boolean mins = elapsedMins > 0;
            sb.append(elapsedHours);
            sb.append(" hour" + (elapsedHours > 1 ? "s" : "") + (mins ? ", " : "."));
            if (mins) {
                boolean secs = elapsedSecs > 0;
                if (!secs) {
                    sb.append("and ");
                }
                sb.append(elapsedMins);
                sb.append(" minute" + (elapsedMins > 1 ? "s" : "") + (secs ? ", " : "."));
                if (secs) {
                    sb.append("and ");
                    sb.append(elapsedSecs);
                    sb.append(" second" + (elapsedSecs > 1 ? "s" : "") + ".");
                }
            }
        } else if (elapsedMinutes > 0) {
            boolean secs = elapsedSecs > 0;
            sb.append(elapsedMinutes);
            sb.append(" minute" + (elapsedMinutes > 1 ? "s" : "") + (secs ? " " : "."));
            if (secs) {
                sb.append("and ");
                sb.append(elapsedSecs);
                sb.append(" second" + (elapsedSecs > 1 ? "s" : "") + ".");
            }
        } else if (elapsedSeconds > 0) {
            sb.append((int) elapsedSeconds);
            sb.append(" second" + (elapsedSeconds > 1 ? "s" : "") + ".");
        } else {
            sb.append("None.");
        }
        return sb.toString();
    }

    public static final int getDaysAmount(long startMillis, long endMillis) {
        double elapsedSeconds = (endMillis - startMillis) / 1000.0;
        int elapsedMinutes = (int) (elapsedSeconds / 60.0);
        int elapsedHrs = elapsedMinutes / 60;
        int elapsedDays = elapsedHrs / 24;
        return elapsedDays;
    }

    public static String codeString(String fileName) throws FileNotFoundException {
        return codeString(new File(fileName));
    }

    /**
     * 判斷檔案的編碼格式
     *
     * @param file :file
     * @return 檔案的編碼格式
     * @throws java.io.FileNotFoundException
     */
    public static String codeString(File file) throws FileNotFoundException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
        int p = 0;
        try {
            p = (bin.read() << 8) + bin.read();
            bin.close();
        } catch (IOException ex) {
        }
        String code;

        switch (p) {
            case 0xFFFE:
                code = "Unicode";
                break;
            case 0xFEFF:
                code = "UTF-16BE";
                break;
            case 0xEFBB:
            default:
                code = "UTF-8";
        }
        return code;
    }
}
