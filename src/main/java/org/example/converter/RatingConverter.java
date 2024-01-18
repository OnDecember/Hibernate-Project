package org.example.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.enums.Rating;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(Rating rating) {
        if (rating == null) return Rating.G.getValue();
        return rating.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(String value) {
        if (value == null) return Rating.G;
        return Rating.fromValue(value);
    }
}