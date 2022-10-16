package com.flexpag.paymentscheduler;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.flexpag.paymentscheduler.service.CrudSchedulesService;

@EnableJpaRepositories
@SpringBootApplication
public class PaymentSchedulerApplication implements CommandLineRunner {
	private Boolean system = true;
	private final CrudSchedulesService scheduling;

	public PaymentSchedulerApplication(CrudSchedulesService scheduling) {
		this.scheduling = scheduling;
	}

	public static void main(String[] args) {
		SpringApplication.run(PaymentSchedulerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		while (system) {
			System.out.println("0 - sair");
			System.out.println("1 - Ir para a agenda de pagamentos");
			Integer function = scanner.nextInt();

			switch (function) {
			case 1:
				scheduling.begin();
				break;
			default:
				System.out.println("Saindo ...");
				system = false;
				break;
			}
		}

	}

}
