package ru.paul.tagimage.db;

import androidx.room.TypeConverter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    @TypeConverter
    public String dateConvert(Date date) {
        Format formatter =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
        Date today = Calendar.getInstance().getTime();
        return formatter.format(today);
    }
}
