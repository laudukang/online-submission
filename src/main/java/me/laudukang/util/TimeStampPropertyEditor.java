package me.laudukang.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class TimeStampPropertyEditor extends PropertyEditorSupport {

    // private String pattern = "yyyy-MM-dd HH:mm:ss.S";
    private String pattern = "yyyy-MM-dd";

    private final String[] formater_pattern = new String[]{"yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss"};// "yyyy-MM-dd HH:mm:ss.S",
    // "yyyy-MM-dd HH:mm:ss" };

    public TimeStampPropertyEditor() {
    }

    public TimeStampPropertyEditor(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (null == text) {
            setValue(null);
        } else {
            try {
                Date date = DateUtils.parseDate(text, formater_pattern);
                Timestamp timestamp = new Timestamp(date.getTime());
                // 设置转换完的值
                setValue(timestamp);
            } catch (ParseException e) {
                e.printStackTrace();
                setValue(null);
            }
        }

    }

    @Override
    public String getAsText() {
        // 获取model的值
        Timestamp value = (Timestamp) getValue();
        if (value == null) {
            return "";
        } else {
            try {
                Date date = new Date(value.getTime());
                String str = DateFormatUtils.format(date, pattern);
                return str;
            } catch (Exception e) {
                return "";
            }
        }
    }
}