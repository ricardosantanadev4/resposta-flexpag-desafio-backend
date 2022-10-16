package com.flexpag.paymentscheduler.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.flexpag.paymentscheduler.orm.Schedules;
import com.flexpag.paymentscheduler.orm.SchedulingStatus;
import com.flexpag.paymentscheduler.repository.SchedulesRepository;

@Service
public class CrudSchedulesService {

	private Boolean system = true;
	private final SchedulesRepository scheduleRepository;
//	allows you to set the date and time formats
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
//	function messages
	private final String idDoesNotExistOrStatusPaid = "Operação abortada! O id informado não existe ou o agendamento já foi pago, consulte o status do agentamento!";
	private final String updatedDateTime = "Data e hora atualizada com sucesso, consulte os detalhes do agendamento!";
	private final String updatedDate = "Data atualizada com sucesso, consulte os detalhes do agendamento!";
	private final String updatedTime = "Hora atualizada com sucesso, consulte os detalhes do agendamento!";
	private final String idNotFound = "O id informado não foi encontrado, verifique o número e retorne novamente!";
	private final String paymentMade = "Pagamento efetuado, consulte detalhes do pagamento!";

	public CrudSchedulesService(SchedulesRepository cargoRepository) {
		this.scheduleRepository = cargoRepository;
	}

	public void begin() {
		Scanner scanner = new Scanner(System.in);
		while (system) {
			System.out.println("0 - Sair");
			System.out.println("1 - Agendar pagamento");
			System.out.println("2 - Consultar o status do agendamento");
			System.out.println("3 - Excluir um agendamento pendente");
			System.out.println("4 - Atualizar a data:hora do agendamento");
			System.out.println("5 - Consultar detalhes do agendamento");
			System.out.println("6 - Realizar pagamento");
			System.out.println("7 - listar agendamentos");
			Integer function = scanner.nextInt();
			switch (function) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				seeStatus(scanner);
				break;
			case 3:
				deleteSchedule(scanner);
				break;
			case 4:
				menuUpdateDateTimeSchedule(scanner);
				break;
			case 5:
				seeScheduleDetails(scanner);
				break;
			case 6:
				makePayment(scanner);
				break;
			case 7:
				viewSchedules();
				break;
			default:
				System.out.println("Saindo ...");
				system = false;
				break;
			}
		}
	}

	private void salvar(Scanner scanner) {
		System.out.println("Informe a data para poder agendar o pagamento. No formato: dd/MM/yyyy");
		String schedulingDate = scanner.next();
		System.out.println("Informe o horário. No formato: HH:mm:ss");
		String schedulingTime = scanner.next();
		System.out.println("Digite o valor");
		Double amount = scanner.nextDouble();
		Schedules scheduling = new Schedules();
		scheduling.setSchedulingDate(LocalDate.parse(schedulingDate, formatter));
		scheduling.setSchedulingTime(LocalTime.parse(schedulingTime, formatterTime));
		scheduling.setStatus(SchedulingStatus.PENDING.toString());
		scheduling.setAmount(amount);
		scheduleRepository.save(scheduling);
		System.out.println("Agendamento efetuado! " + "ID: " + scheduling.getId());
	}

	private void seeStatus(Scanner scanner) {
		System.out.println("Informe o ID do agendamento");
		Long id = scanner.nextLong();
		Optional<Schedules> optional = scheduleRepository.findById(id);
		if (optional.isPresent()) {
			System.out.println("Status do agentamento de id " + id + " " + optional.get().getStatus()
					+ " Para mais iformações ver detalhes do agendamento");
		} else {
			System.out.println("Não foi encontrado agendamento com o id " + id
					+ " informado. Verifique o número e retorne novamente!");
		}
	}

	private void deleteSchedule(Scanner scanner) {
		System.out.println("Informe o ID do agendamento");
		Long id = scanner.nextLong();
		Optional<Schedules> optional = scheduleRepository.findById(id);
		if (optional.isPresent() && optional.get().getStatus() == "PENDING") {
			scheduleRepository.deleteById(id);
			System.out.println("Agendamento exluido com sucesso!");
		} else {
			System.out.println(idDoesNotExistOrStatusPaid);
		}
	}

	private void menuUpdateDateTimeSchedule(Scanner scanner) {
		System.out.println("0 - Sair");
		System.out.println("1 - Atualizar data e hora do agendamento");
		System.out.println("2 - Atualizar somente a data do agendamento");
		System.out.println("3 - Atualizar somente a hora do agendamento");
		Integer function = scanner.nextInt();
		switch (function) {
		case 1:
			updateDateAndTime(scanner);
			break;
		case 2:
			updateScheduleDate(scanner);
			break;
		case 3:
			updateScheduleTime(scanner);
			break;
		default:
			system = false;
			System.out.println("Saindo ...");
			break;
		}
	}

	private void updateDateAndTime(Scanner scanner) {
		System.out.println("Informe o id do agendamento");
		Long id = scanner.nextLong();
		Optional<Schedules> optional = scheduleRepository.findById(id);
		if (optional.isPresent()) {
			System.out.println("Informe a nova data do agendamento. No formato: dd/MM/yyyy");
			String schedulingDate = scanner.next();
			System.out.println("Informe o novo horário do agendamento. No formato: HH:mm:ss");
			String schedulingTime = scanner.next();
			optional.get().setSchedulingDate(LocalDate.parse(schedulingDate, formatter));
			optional.get().setSchedulingTime(LocalTime.parse(schedulingTime, formatterTime));
			scheduleRepository.save(optional.get());
			System.out.println(updatedDateTime);
		} else {
			System.out.println(idNotFound);
		}
	}

	private void updateScheduleDate(Scanner scanner) {
		System.out.println("Informe o id do agendamento");
		Long id = scanner.nextLong();
		Optional<Schedules> optional = scheduleRepository.findById(id);
		if (optional.isPresent()) {
			System.out.println("Informe a nova data do agendamento. No formato: dd/MM/yyyy");
			String schedulingDate = scanner.next();
			optional.get().setSchedulingDate(LocalDate.parse(schedulingDate, formatter));
			scheduleRepository.save(optional.get());
			System.out.println(updatedDate);
		} else {
			System.out.println(idNotFound);
		}
	}

	private void updateScheduleTime(Scanner scanner) {
		System.out.println("Informe o id do agendamento");
		Long id = scanner.nextLong();
		Optional<Schedules> optional = scheduleRepository.findById(id);
		if (optional.isPresent()) {
			System.out.println("Informe o novo horário do agendamento. No formato: HH:mm:ss");
			String schedulingTime = scanner.next();
			optional.get().setSchedulingTime(LocalTime.parse(schedulingTime, formatterTime));
			scheduleRepository.save(optional.get());
			System.out.println(updatedTime);
		} else {
			System.out.println(idNotFound);
		}
	}

	private void seeScheduleDetails(Scanner scanner) {
		System.out.println("Informe o id do agendamento");
		Long id = scanner.nextLong();
		Optional<Schedules> optional = scheduleRepository.findById(id);
		if (optional.isPresent()) {
			optional.get().getId();
			optional.get().getSchedulingDate();
			optional.get().getSchedulingTime();
			optional.get().getStatus();
			System.out.println("Detalhes do agendamento: " + optional.get());
		} else {
			System.out.println(idNotFound);
		}
	}

	private void makePayment(Scanner scanner) {
		System.out.println("Informe o id do agendamento");
		Long id = scanner.nextLong();
		Optional<Schedules> optional = scheduleRepository.findById(id);
		if (optional.isPresent()) {
			optional.get().setStatus(SchedulingStatus.PAID.toString());
			scheduleRepository.save(optional.get());
			System.out.println(paymentMade);
		} else {
			System.out.println(idNotFound);
		}
	}

	private void viewSchedules() {
		Iterable<Schedules> cargos = scheduleRepository.findAll();
		cargos.forEach(cargo -> System.out.println(cargo));
	}
}
