package sns.controller.media;

import java.util.Map;

public record MediaUpdateAttributesRequest(
        Map<String, Object> attributes
) {
}
