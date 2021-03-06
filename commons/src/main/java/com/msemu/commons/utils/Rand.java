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

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Weber on 2018/3/14.
 */
public class Rand {

    public Rand() {
    }

    public static double get() {
        return ThreadLocalRandom.current().nextDouble();
    }

    public static int get(int n) {
        return ThreadLocalRandom.current().nextInt(Math.abs(n));
    }

    public static long get(long n) {
        return (long) (ThreadLocalRandom.current().nextDouble() * (double) n);
    }

    public static double get(double n) {
        return ThreadLocalRandom.current().nextDouble() * n;
    }

    public static int get(int min, int max) {
        return min + get(max - min + 1);
    }

    public static long get(long min, long max) {
        return min + get(max - min + 1L);
    }

    public static double get(double min, double max) {
        return min + get(max - min + 1.0D);
    }

    public static int nextInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    public static double nextDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    public static double nextGaussian() {
        return ThreadLocalRandom.current().nextGaussian();
    }

    public static boolean nextBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static byte[] nextBytes(byte[] bytes) {
        ThreadLocalRandom.current().nextBytes(bytes);
        return bytes;
    }

    public static boolean getChance(int chance) {
        return getChance(chance, 100);
    }

    public static boolean getChance(double chance) {
        return getChance(chance, 100);
    }

    public static boolean getChance(double chance, double max) {
        return ThreadLocalRandom.current().nextDouble() * max <= chance;
    }

    public static <E> E get(E... list) {
        return list[get(list.length)];
    }

    public static int get(int... list) {
        return list[get(list.length)];
    }

    public static <E> E get(List<E> list) {
        return list.get(get(list.size()));
    }

    public static String nextString() {
        String code = "";
        for (int i = 0; i < 200; i++) {
            switch (get(10)) {
                case 0:
                    code += "_";
                    break;
                case 1:
                    code += "~";
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                    // 大寫字母還是小寫字母
                    int temp = get(2) == 0 ? 0x41 : 0x61;
                    code += (char) (get(0x1A) + temp);
                    break;
                default:
                    code += String.valueOf(get(10));
            }
        }
        return code;
    }
}
