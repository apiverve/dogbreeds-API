// Converter.java

// To use this code, add the following Maven dependency to your project:
//
//
//     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
//     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
//
// Import this package:
//
//     import com.apiverve.data.Converter;
//
// Then you can deserialize a JSON string with
//
//     DogBreedsData data = Converter.fromJsonString(jsonString);

package com.apiverve.dogbreeds.data;

import java.io.IOException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Converter {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    public static DogBreedsData fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(DogBreedsData obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return Converter.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(DogBreedsData.class);
        writer = mapper.writerFor(DogBreedsData.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) instantiateMapper();
        return writer;
    }
}

// DogBreedsData.java

package com.apiverve.dogbreeds.data;

import com.fasterxml.jackson.annotation.*;

public class DogBreedsData {
    private String breed;
    private long foundCount;
    private FoundBreed[] foundBreeds;

    @JsonProperty("breed")
    public String getBreed() { return breed; }
    @JsonProperty("breed")
    public void setBreed(String value) { this.breed = value; }

    @JsonProperty("foundCount")
    public long getFoundCount() { return foundCount; }
    @JsonProperty("foundCount")
    public void setFoundCount(long value) { this.foundCount = value; }

    @JsonProperty("foundBreeds")
    public FoundBreed[] getFoundBreeds() { return foundBreeds; }
    @JsonProperty("foundBreeds")
    public void setFoundBreeds(FoundBreed[] value) { this.foundBreeds = value; }
}

// FoundBreed.java

package com.apiverve.dogbreeds.data;

import com.fasterxml.jackson.annotation.*;

public class FoundBreed {
    private String name;
    private Weight weight;
    private Height height;
    private String bredFor;
    private String group;
    private LifeSpan lifeSpan;
    private String[] traits;

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("weight")
    public Weight getWeight() { return weight; }
    @JsonProperty("weight")
    public void setWeight(Weight value) { this.weight = value; }

    @JsonProperty("height")
    public Height getHeight() { return height; }
    @JsonProperty("height")
    public void setHeight(Height value) { this.height = value; }

    @JsonProperty("bredFor")
    public String getBredFor() { return bredFor; }
    @JsonProperty("bredFor")
    public void setBredFor(String value) { this.bredFor = value; }

    @JsonProperty("group")
    public String getGroup() { return group; }
    @JsonProperty("group")
    public void setGroup(String value) { this.group = value; }

    @JsonProperty("life_span")
    public LifeSpan getLifeSpan() { return lifeSpan; }
    @JsonProperty("life_span")
    public void setLifeSpan(LifeSpan value) { this.lifeSpan = value; }

    @JsonProperty("traits")
    public String[] getTraits() { return traits; }
    @JsonProperty("traits")
    public void setTraits(String[] value) { this.traits = value; }
}

// Height.java

package com.apiverve.dogbreeds.data;

import com.fasterxml.jackson.annotation.*;

public class Height {
    private long lowerInches;
    private long upperInches;

    @JsonProperty("lowerInches")
    public long getLowerInches() { return lowerInches; }
    @JsonProperty("lowerInches")
    public void setLowerInches(long value) { this.lowerInches = value; }

    @JsonProperty("upperInches")
    public long getUpperInches() { return upperInches; }
    @JsonProperty("upperInches")
    public void setUpperInches(long value) { this.upperInches = value; }
}

// LifeSpan.java

package com.apiverve.dogbreeds.data;

import com.fasterxml.jackson.annotation.*;

public class LifeSpan {
    private long lowerYears;
    private long upperYears;

    @JsonProperty("lowerYears")
    public long getLowerYears() { return lowerYears; }
    @JsonProperty("lowerYears")
    public void setLowerYears(long value) { this.lowerYears = value; }

    @JsonProperty("upperYears")
    public long getUpperYears() { return upperYears; }
    @JsonProperty("upperYears")
    public void setUpperYears(long value) { this.upperYears = value; }
}

// Weight.java

package com.apiverve.dogbreeds.data;

import com.fasterxml.jackson.annotation.*;

public class Weight {
    private long lowerLbs;
    private long upperLbs;

    @JsonProperty("lowerLbs")
    public long getLowerLbs() { return lowerLbs; }
    @JsonProperty("lowerLbs")
    public void setLowerLbs(long value) { this.lowerLbs = value; }

    @JsonProperty("upperLbs")
    public long getUpperLbs() { return upperLbs; }
    @JsonProperty("upperLbs")
    public void setUpperLbs(long value) { this.upperLbs = value; }
}