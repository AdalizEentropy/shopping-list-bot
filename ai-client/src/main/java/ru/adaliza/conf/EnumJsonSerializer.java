package ru.adaliza.conf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import ru.adaliza.model.WebMessageRole;

public class EnumJsonSerializer extends JsonSerializer<WebMessageRole> {

    @Override
    public void serialize(
            final WebMessageRole enumValue,
            final JsonGenerator gen,
            final SerializerProvider serializer)
            throws IOException {
        gen.writeString(enumValue.getRole());
    }
}
