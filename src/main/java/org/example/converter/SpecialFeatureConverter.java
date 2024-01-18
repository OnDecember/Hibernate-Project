package org.example.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.enums.SpecialFeature;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class SpecialFeatureConverter implements AttributeConverter<Set<SpecialFeature>, String> {
    @Override
    public String convertToDatabaseColumn(Set<SpecialFeature> specialFeatures) {
        if (specialFeatures == null || specialFeatures.isEmpty()) return "";
        return specialFeatures.stream()
                .map(SpecialFeature::getValue)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<SpecialFeature> convertToEntityAttribute(String data) {
        if (data == null || data.trim().isEmpty()) return null;
        return Arrays.stream(data.split(","))
                .map(String::trim)
                .map(SpecialFeature::fromValue)
                .collect(Collectors.toSet());
    }
}
