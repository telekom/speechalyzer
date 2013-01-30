package com.tlabs.labeltool.gui;

import java.util.Properties;

import com.tlabs.labeltool.Util;


public class RecorderProperties {
    protected static Properties props;

    public RecorderProperties() {
        props = new Properties();
        props.putAll(Util.getValuesFromFile("recorderProperties.txt"));
    }

}
