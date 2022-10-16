package com.flexpag.paymentscheduler.orm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SCHEDULES")
public class Schedules {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate schedulingDate;
	private LocalTime schedulingTime;
	private String status;
	private Double amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getSchedulingDate() {
		return schedulingDate;
	}

	public void setSchedulingDate(LocalDate schedulingDate) {
		this.schedulingDate = schedulingDate;
	}

	public LocalTime getSchedulingTime() {
		return schedulingTime;
	}

	public void setSchedulingTime(LocalTime schedulingTime) {
		this.schedulingTime = schedulingTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Schedules [id=" + id + ", schedulingDate=" + schedulingDate + ", schedulingTime=" + schedulingTime
				+ ", status=" + status + ", amount=" + amount + "]";
	}
}
