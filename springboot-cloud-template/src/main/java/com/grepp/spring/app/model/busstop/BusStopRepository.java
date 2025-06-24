package com.grepp.spring.app.model.busstop;

import com.grepp.spring.app.model.busstop.entity.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStopRepository extends JpaRepository<BusStop, Long> {

}
