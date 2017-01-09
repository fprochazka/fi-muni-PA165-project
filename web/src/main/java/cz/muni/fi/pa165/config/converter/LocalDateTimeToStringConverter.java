package cz.muni.fi.pa165.config.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public final class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String>
{
    private final DateTimeFormatter formatter;

    public LocalDateTimeToStringConverter(DateTimeFormatter formatter)
    {
        this.formatter = formatter;
    }

    @Override
    public String convert(LocalDateTime sourceDate)
    {
        if (sourceDate == null) {
            return null;
        }

        return sourceDate.format(formatter);
    }

}
