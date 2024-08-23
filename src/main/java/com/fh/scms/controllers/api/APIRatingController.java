package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.rating.RatingRequestUpdate;
import com.fh.scms.pojo.Rating;
import com.fh.scms.pojo.User;
import com.fh.scms.services.RatingService;
import com.fh.scms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/ratings", produces = "application/json; charset=UTF-8")
public class APIRatingController {

    private final RatingService ratingService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> listRatings(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<Rating> ratingList = this.ratingService.findAllWithFilter(params);

        return ResponseEntity.ok(ratingList);
    }

    @GetMapping(path = "/{ratingId}")
    public ResponseEntity<?> getRating(@PathVariable(value = "ratingId") Long id) {
        Rating rating = this.ratingService.findById(id);

        if (rating == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rating);
    }

    @PostMapping(path = "/{ratingId}")
    public ResponseEntity<?> updateRating(Principal principal, @PathVariable(value = "ratingId") Long id,
                                          @ModelAttribute @Valid RatingRequestUpdate ratingRequestUpdate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> messageResponses = MessageResponse.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(messageResponses);
        }

        Rating rating = this.ratingService.findById(id);

        if (rating == null) {
            return ResponseEntity.notFound().build();
        }

        User user = this.userService.findByUsername(principal.getName());
        if (Objects.equals(user.getId(), rating.getUser().getId())) {
            rating = this.ratingService.update(rating, ratingRequestUpdate);

            return ResponseEntity.ok(rating);
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Bạn không có quyền sửa đánh giá này"));
    }

    @DeleteMapping(path = "/{ratingId}")
    public ResponseEntity<?> deleteRating(Principal principal, @PathVariable(value = "ratingId") Long id) {
        Rating rating = this.ratingService.findById(id);

        if (rating == null) {
            return ResponseEntity.notFound().build();
        }

        User user = this.userService.findByUsername(principal.getName());
        if (Objects.equals(user.getId(), rating.getUser().getId())) {
            this.ratingService.delete(id);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Bạn không có quyền xóa đánh giá này"));
    }
}