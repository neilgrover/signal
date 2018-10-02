package ca.i4s.foodguide.persistence;

import ca.i4s.foodguide.model.Gender;
import ca.i4s.foodguide.persistence.converter.GenderSymbolConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Sql2o;
import org.sql2o.converters.Converter;
import org.sql2o.quirks.NoQuirks;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class Sql extends Sql2o {

    @Autowired
    public Sql(DataSource dataSource) {
        super(dataSource, new NoQuirks(getConverterMap()));
    }

    protected static Map<Class, Converter> getConverterMap() {
        Map<Class, Converter> mappers = new HashMap<>();
        mappers.put(Gender.class, new GenderSymbolConverter());
        return mappers;
    }
}
