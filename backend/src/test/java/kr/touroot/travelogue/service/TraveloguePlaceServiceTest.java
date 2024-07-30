package kr.touroot.travelogue.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import kr.touroot.global.ServiceTest;
import kr.touroot.global.exception.BadRequestException;
import kr.touroot.place.domain.Place;
import kr.touroot.travelogue.domain.Travelogue;
import kr.touroot.travelogue.domain.TravelogueDay;
import kr.touroot.travelogue.domain.TraveloguePlace;
import kr.touroot.travelogue.dto.request.TraveloguePhotoRequest;
import kr.touroot.travelogue.dto.request.TraveloguePlaceRequest;
import kr.touroot.travelogue.fixture.TravelogueRequestFixture;
import kr.touroot.travelogue.helper.TravelogueTestHelper;
import kr.touroot.utils.DatabaseCleaner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("여행기 장소 서비스")
@Import(value = {TraveloguePlaceService.class, TravelogueTestHelper.class})
@ServiceTest
class TraveloguePlaceServiceTest {

    private final TraveloguePlaceService placeService;
    private final DatabaseCleaner databaseCleaner;
    private final TravelogueTestHelper testHelper;

    @Autowired
    public TraveloguePlaceServiceTest(
            TraveloguePlaceService placeService,
            DatabaseCleaner databaseCleaner,
            TravelogueTestHelper testHelper
    ) {
        this.placeService = placeService;
        this.databaseCleaner = databaseCleaner;
        this.testHelper = testHelper;
    }

    @BeforeEach
    void setUp() {
        databaseCleaner.executeTruncate();
    }

    @DisplayName("여행기 장소를 생성한다.")
    @Test
    void createPlaces() {
        List<TraveloguePlaceRequest> requests = TravelogueRequestFixture.getTraveloguePlaceRequests();
        Travelogue travelogue = testHelper.persistTravelogue();
        TravelogueDay day = testHelper.persistTravelogueDay(travelogue);

        Map<TraveloguePlace, List<TraveloguePhotoRequest>> placesMap = placeService.createPlaces(requests, day);
        List<TraveloguePlace> places = placesMap.keySet().stream().toList();

        assertAll(
                () -> assertThat(placesMap.keySet()).hasSize(requests.size()),
                () -> assertThat(placesMap).containsEntry(places.get(0), requests.get(0).photoUrls())
        );
    }

    @DisplayName("여행기 장소를 여행기 일자를 기준으로 조회한다.")
    @Test
    void findTraveloguePlacesByDay() {
        Travelogue travelogue = testHelper.persistTravelogue();
        TravelogueDay day = testHelper.persistTravelogueDay(travelogue);
        Place position = testHelper.persistPlace();
        TraveloguePlace place = testHelper.persistTraveloguePlace(position, day);

        List<TraveloguePlace> places = placeService.findTraveloguePlacesByDay(day);

        assertThat(places).contains(place);
    }

    @DisplayName("여행기 장소를 ID를 기준으로 조회한다.")
    @Test
    void findTraveloguePlaceById() {
        testHelper.initTravelogueTestData();

        assertDoesNotThrow(() -> placeService.findTraveloguePlaceById(1L));
    }

    @DisplayName("존재하지 않는 여행기 장소 ID로 조회하면 예외가 발생한다.")
    @Test
    void findDayByInvalidIdThrowException() {
        assertThatThrownBy(() -> placeService.findTraveloguePlaceById(1L))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("존재하지 않는 여행기 장소입니다.");
    }
}