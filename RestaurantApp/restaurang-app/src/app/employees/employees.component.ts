import { Component, OnInit } from '@angular/core';
import { EmployeesService } from './services/employees.service';
import { Employee } from '../modules/shared/models/employee';
import { MessageService, PrimeNGConfig } from 'primeng/api';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css'],
  providers: [MessageService],
})
export class EmployeesComponent implements OnInit {
  employees: Employee[] = [];
  selectedEmployee: Employee = {};

  employeeData: Employee = {};
  nameInput: string | undefined = '';
  surnameInput: string | undefined = '';
  emailInput: string | undefined = '';
  roleInput: string | undefined = '';
  salaryInput: number | undefined = undefined;
  pincodeInput: number | undefined = undefined;
  first = 0;
  rows = 10;

  constructor(
    private employeesService: EmployeesService,
    private primengConfig: PrimeNGConfig
  ) {}

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.getEmployees();
  }

  next() {
    this.first = this.first + this.rows;
  }

  prev() {
    this.first = this.first - this.rows;
  }

  reset() {
    this.first = 0;
  }

  getEmployees() {
    this.employeesService.getEmployees().subscribe((data) => {
      this.employees = data;
    });
  }

  deleteEmployee(){
    this.employeesService.deleteEmployee(this.selectedEmployee.id).subscribe(() => {
      this.getEmployees();
    });
  }

  updateEmployee() {
    this.nameInput = this.nameInput == '' ? this.selectedEmployee.name: this.nameInput;
    this.surnameInput = this.surnameInput == '' ? this.selectedEmployee.surname: this.surnameInput;
    this.emailInput = this.emailInput == '' ? this.selectedEmployee.email: this.emailInput;
    this.roleInput = this.roleInput == '' ? this.selectedEmployee.employeeType: this.roleInput;
    this.salaryInput = this.salaryInput == undefined ? this.selectedEmployee.salary: this.salaryInput;
    this.pincodeInput = this.pincodeInput == undefined ? this.selectedEmployee.pincode: this.pincodeInput;

    this.employeeData.id = this.selectedEmployee.id;
    this.employeeData.name = this.nameInput;
    this.employeeData.surname = this.surnameInput;
    this.employeeData.email = this.emailInput;
    this.employeeData.pincode = this.pincodeInput;
    this.employeeData.salary = this.salaryInput;
    this.employeeData.employeeType = this.roleInput;
    this.employeeData.birthday = new Date();
    this.employeeData.type = 'EMPLOYEE';

    this.employeesService.updateEmployee(this.employeeData).subscribe(() => {
      this.getEmployees();
    });

    this.nameInput = '';
    this.surnameInput = '';
    this.emailInput = '';
    this.roleInput = '';
    this.salaryInput = undefined;
    this.pincodeInput = undefined;

    this.selectedEmployee = {};
  }


  isLastPage(): boolean {
    return this.employees
      ? this.first === this.employees.length - this.rows
      : true;
  }

  isFirstPage(): boolean {
    return this.employees ? this.first === 0 : true;
  }
}
