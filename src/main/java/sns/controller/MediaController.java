package sns.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sns.config.AuthUser;
import sns.controller.dto.MediaUploadedRequest;
import sns.controller.media.MediaInitRequest;
import sns.controller.media.MediaInitResponse;
import sns.controller.media.MediaResponse;
import sns.controller.media.PresignedUrlResponse;
import sns.domain.media.Media;
import sns.domain.media.MediaService;
import sns.domain.media.PresignedUrl;
import sns.domain.user.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping("/api/media/init")
    public ResponseEntity<MediaInitResponse> create(
            @AuthUser User user,
            @RequestBody MediaInitRequest request) {
        PresignedUrl result = mediaService.initMedia(request.mediaType(), request.fileSize(), user);
        return ResponseEntity.ok(MediaInitResponse.from(result));
    }

    @GetMapping("/api/media/{id}/presigned-url")
    public PresignedUrlResponse getPresignedUrl(@PathVariable Long id) {
        Media media = mediaService.getMediaById(id);
        String presignedUrl = mediaService.getPresignedUrl(media);

        return new PresignedUrlResponse(presignedUrl, MediaResponse.from(media));
    }

    @PostMapping("/api/media/uploaded")
    public MediaResponse mediaUploaded(
            @RequestBody MediaUploadedRequest request,
            @AuthUser User user
    ) {
        Media media = mediaService.mediaUploaded(request.mediaId(), request.parts(), user);
        return MediaResponse.from(media);
    }

    @GetMapping("/api/media/{id}")
    public MediaResponse getMediaById(@PathVariable Long id) {
        Media media = mediaService.getMediaById(id);
        return MediaResponse.from(media);
    }

    @GetMapping("/api/users/{userId}/media")
    public List<MediaResponse> getMediaByUserId(@PathVariable Long userId) {
        return mediaService.getMediaByUserId(userId).stream()
                .map(MediaResponse::from)
                .toList();
    }

    @DeleteMapping("/api/media/{id}")
    public void deleteMedia(
            @PathVariable Long id,
            @AuthUser User user
    ) {
        mediaService.deleteMedia(id, user);
    }
}
