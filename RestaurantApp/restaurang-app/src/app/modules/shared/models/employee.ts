import { SalaryInfo } from './salaryInfo';

export interface Employee{
    id?: number;
    email?: String;
    name?: String;
    surname?: String;
    birthday?: Date;
    type?: String;
    salaries?: SalaryInfo[];
    pincode?: number;
}

export interface EmployeesDTO {
    employees: Employee[];
}

//employeeType