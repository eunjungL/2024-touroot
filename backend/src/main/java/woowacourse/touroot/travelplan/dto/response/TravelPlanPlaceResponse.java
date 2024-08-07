package woowacourse.touroot.travelplan.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import woowacourse.touroot.place.domain.Place;
import woowacourse.touroot.travelplan.domain.TravelPlanPlace;

@Builder
public record TravelPlanPlaceResponse(
        @Schema(description = "여행 장소 이름") String placeName,
        @Schema(description = "여행 장소 위치") TravelPlanLocationResponse location,
        @Schema(description = "여행 장소 설명") String description
) {

    public static TravelPlanPlaceResponse from(TravelPlanPlace planPlace) {
        Place place = planPlace.getPlace();
        TravelPlanLocationResponse locationResponse = TravelPlanLocationResponse.from(place);

        return TravelPlanPlaceResponse.builder()
                .placeName(place.getName())
                .location(locationResponse)
                .description(planPlace.getDescription())
                .build();
    }
}
