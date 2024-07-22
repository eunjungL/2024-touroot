package woowacourse.touroot.travelogue.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import woowacourse.touroot.travelogue.domain.TravelogueDay;
import woowacourse.touroot.travelogue.domain.TraveloguePlace;

public interface TraveloguePlaceRepository extends JpaRepository<TraveloguePlace, Long> {

    List<TraveloguePlace> findByTravelogueDay(TravelogueDay travelogueDay);
}