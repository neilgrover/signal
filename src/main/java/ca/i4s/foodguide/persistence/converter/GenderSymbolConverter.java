package ca.i4s.foodguide.persistence.converter;

import ca.i4s.foodguide.model.Gender;
import org.sql2o.converters.Converter;
import org.sql2o.converters.ConverterException;

public class GenderSymbolConverter implements Converter<Gender> {

    @Override
    public Gender convert(Object o) throws ConverterException {
        return Gender.valueOfSymbol(o.toString());
    }

    @Override
    public Object toDatabaseParam(Gender gender) {
        return gender.toString();
    }
}
