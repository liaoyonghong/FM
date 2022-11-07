package com.versionsystem.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class KendoAwareObjectMapper extends ObjectMapper {

    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public KendoAwareObjectMapper() {
        //indent the json output so it is easier to read
        configure(SerializationFeature.INDENT_OUTPUT, true);

        //Wite/Read dates as ISO Strings
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        //dateFormat.setTimeZone(TimeZone.getTimeZone(configService.getProperty(ApplicationParas.APP_TIMEZONE)));
        this.setDateFormat(dateFormat);

    }

}
