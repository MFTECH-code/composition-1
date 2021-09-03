package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import entities.Department;
import entities.HourContract;
import entities.Worker;
import entities.enums.WorkerLevel;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfSearch = new SimpleDateFormat("MM/yyyy");
		
		// Cadastrando departamento
		System.out.print("Enter department's name: ");
		String departmentName = sc.next();
		Department department = new Department(departmentName);
		
		// Cadastrando worker
		Worker worker = new Worker();
		worker.setDepartment(department);
		System.out.println("Enter worker data:");
		System.out.print("Name: ");
		String workerName = sc.next();
		worker.setName(workerName);
		System.out.print("Level: ");
		String workerLevelString = sc.next();
		WorkerLevel workerLevel = WorkerLevel.valueOf(workerLevelString);
		worker.setLevel(workerLevel);
		System.out.print("Base salary: ");
		double workerBaseSalary = sc.nextDouble();
		worker.setBaseSalary(workerBaseSalary);
		// Preenchendo lista de contratos
		System.out.print("How many contracts to this worker? ");
		int numOfContracts = sc.nextInt();
		for (int i = 0; i < numOfContracts; i++) {
			// Cadastrando contrato
			HourContract contract = new HourContract();
			System.out.println("Enter contract #" + (i + 1) + "data: ");
			System.out.print("Date (DD/MM/YYYY): ");
			String contractDateString = sc.next();
			try {
				Date contractDate = sdf.parse(contractDateString);
				contract.setDate(contractDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			System.out.print("Value per hour: ");
			double valuePerHour = sc.nextDouble();
			contract.setValuePerHour(valuePerHour);
			System.out.print("Duration (hours):");
			int duration = sc.nextInt();
			contract.setHours(duration);
			worker.addContract(contract);
		}
		
		System.out.println();
		System.out.print("Enter month and year to calculate income (MM/YYYY):");
		String searchMonthString = sc.next();
		Calendar cal = Calendar.getInstance();
		try {
			Date searchMonth = sdfSearch.parse(searchMonthString);
			cal.setTime(searchMonth);
			int searchCalMonth = 1 + cal.get(Calendar.MONTH);
			int searchCalYear = cal.get(Calendar.YEAR);
			// Fazendo varredura na lista
			for (HourContract c : worker.getContracts()) {
				cal.setTime(c.getDate());
				int contractMonth = 1 + cal.get(Calendar.MONTH);
				int contractYear = cal.get(Calendar.YEAR);
				if (searchCalMonth == contractMonth && searchCalYear == contractYear) {
					System.out.println("Name: " + worker.getName());
					System.out.println("Department: " + worker.getDepartment().getName());
					System.out.println("Income for " + sdfSearch.format(c.getDate()) + ": " + (c.totalValue() + worker.getBaseSalary()));
					break;
				} else {
					System.out.println("Not found this contract...");
					break;
				}
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		sc.close();
	}

}
