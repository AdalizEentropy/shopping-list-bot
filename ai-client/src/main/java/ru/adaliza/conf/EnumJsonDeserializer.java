package ru.adaliza.conf;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import ru.adaliza.model.WebMessageRole;

public class EnumJsonDeserializer extends JsonDeserializer<WebMessageRole> {

    @Override
    public WebMessageRole deserialize(final JsonParser parser, final DeserializationContext context)
            throws IOException {
        final String jsonValue = parser.getText();
        return WebMessageRole.of(jsonValue);
    }
}
