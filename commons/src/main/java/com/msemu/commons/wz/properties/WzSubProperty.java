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

package com.msemu.commons.wz.properties;

import com.msemu.commons.wz.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Weber on 2018/4/20.
 */
public class WzSubProperty extends WzExtended implements IPropertyContainer {
    public WzSubProperty(String name) {
        super(name);
    }

    @Override
    public void addProperty(WzImageProperty prop) {
        prop.setParent(this);
        properties.add(prop);
    }

    @Override
    public void addProperties(List<WzImageProperty> props) {
        props.forEach(this::addProperty);
    }

    @Override
    public void removeProperty(WzImageProperty prop) {
        if (properties.contains(prop)) {
            prop.setParent(null);
            properties.remove(prop);
        }
    }

    @Override
    public void clearProperties() {
        properties.forEach(p -> p.setParent(null));
        properties.clear();
    }

    @Override
    public List<WzImageProperty> getProperties() {
        return properties.stream().collect(Collectors.toList());
    }

    @Override
    public WzImageProperty get(String name) {
        return properties.stream().filter(p -> p.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Override
    public WzPropertyType propType() {
        return WzPropertyType.SubProperty;
    }

    @Override
    public WzObjectType type() {
        return WzObjectType.Property;
    }


    @Override
    public int getInt() {
        return Integer.parseInt(name);
    }

}
