package cz.muni.fi.pa165.config.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public final class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime>
{
    private final DateTimeFormatter formatter;

    public StringToLocalDateTimeConverter(DateTimeFormatter formatter)
    {
        this.formatter = formatter;
    }

    @Override
    public LocalDateTime convert(String sourceDate)
    {
        if (sourceDate == null || sourceDate.isEmpty()) {
            return null;
        }

        return LocalDateTime.parse(sourceDate, formatter);
    }
}
