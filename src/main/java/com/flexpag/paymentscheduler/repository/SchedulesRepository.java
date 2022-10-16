package com.flexpag.paymentscheduler.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flexpag.paymentscheduler.orm.Schedules;

@Repository
public interface SchedulesRepository extends CrudRepository<Schedules, Long> {

}
