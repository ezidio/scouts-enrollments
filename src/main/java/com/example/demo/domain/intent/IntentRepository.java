package com.example.demo.domain.intent;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IntentRepository extends CrudRepository<Intent, IntentId> {

    Iterable<Intent> findTop10ByOrderByCreatedAtAsc();

    @Query("from Intent where id not in (select intent from Enrollment) order by createdAt asc")
    List<Intent> findWaitingList(Pageable pageable);


    @Query("from Intent where " +
            "id not in (select intent from Enrollment) and " +
            "cast((current_date() - birth) as integer) between :min and :max " +
            "order by createdAt asc")
    List<Intent> findAcceptedAgeWaitingList(@Param("min") int minAge, @Param("max") int maxAge, Pageable pageable);

    @Query("Select cast((current_date() - birth) as integer) from Intent " +
            "order by createdAt asc")
    List<Integer> teste();
}
