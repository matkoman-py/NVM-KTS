import { Component, OnInit } from '@angular/core';
import { EmployeesService } from './services/employees.service';
import { Employee, EmployeeType } from '../modules/shared/models/employee';
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
  employeeTypes: EmployeeType[] = [
    { name: 'WAITER', value: 'WAITER' },
    { name: 'BARMAN', value: 'BARMAN' },
    { name: 'COOK', value: 'COOK' },
  ];

  employeeData: Employee = {};
  nameInput: string | undefined = '';
  typeInput: EmployeeType = { name: '', value: '' };
  surnameInput: string | undefined = '';
  emailInput: string | undefined = '';
  salaryInput: number | undefined = undefined;
  pincodeInput: number | undefined = undefined;
  first = 0;
  rows = 10;

  constructor(
    private employeesService: EmployeesService,
    private primengConfig: PrimeNGConfig,
    private messageService: MessageService
  ) {}
  
  isSelected() : boolean{
    if (Object.keys(this.selectedEmployee).length === 0) return true;
    return false;
  }

  getEmployees() {
    this.employeesService.getEmployees().subscribe((data) => {
      this.employees = data;
    });
  }

  deleteEmployee(){
    this.employeesService.deleteEmployee(this.selectedEmployee.id).subscribe((deletedEmployee) => {
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: 'Employee with pincode ' + deletedEmployee.pincode + ' succesfully deleted',
      });
      this.getEmployees();
      this.selectedEmployee = {};
    });
  }

  createEmployee(){
    if(!this.createEmployeeData()) return;

    this.employeesService.createEmployee(this.employeeData).subscribe((createdEmployee) => {
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: 'New employee succesfully created',
      });
      this.getEmployees();
      this.clearInputFields();
    });
  }

  updateEmployee() {
    this.nameInput = this.nameInput == '' ? this.selectedEmployee.name: this.nameInput;
    this.surnameInput = this.surnameInput == '' ? this.selectedEmployee.surname: this.surnameInput;
    this.emailInput = this.emailInput == '' ? this.selectedEmployee.email: this.emailInput;
    this.typeInput = this.typeInput.name == '' ? { name:this.selectedEmployee.employeeType, value: this.selectedEmployee.employeeType}: this.typeInput;
    this.salaryInput = this.salaryInput == undefined ? this.selectedEmployee.salary: this.salaryInput;
    this.pincodeInput = this.pincodeInput == undefined ? this.selectedEmployee.pincode: this.pincodeInput;

    if(!this.updateEmployeeData()) return;

    this.employeesService.updateEmployee(this.employeeData).subscribe((updatedEmployee) => {
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: 'Employee with pincode ' + updatedEmployee.pincode + ' succesfully deleted',
      });
      this.getEmployees();
    });

    this.clearInputFields();
  }
  
  clearInputFields(){
    this.nameInput = '';
    this.surnameInput = '';
    this.emailInput = '';
    this.typeInput = { name: '', value: '' };
    this.salaryInput = undefined;
    this.pincodeInput = undefined;

    this.selectedEmployee = {};
  }

  isDataValid() : boolean{
    if(this.nameInput == '') return true;
    if(this.surnameInput == '') return true;
    if(this.emailInput == '') return true;
    if(this.pincodeInput == undefined) return true;
    if(this.salaryInput == undefined) return true;
    if(this.typeInput.name == '') return true;
    return false;
  }

  createEmployeeData() : boolean{
    this.createEmployeeDTO();
    if(this.employees.filter(employee => employee.email == this.employeeData.email).length != 0){
      this.messageService.add({
        key: 'tc',
        severity: 'warn',
        summary: 'Fail',
        detail: 'Employee with email ' + this.employeeData.email + ' already exists',
      });
      this.clearInputFields();
      return false;
    }
    return true;
  }

  updateEmployeeData() : boolean{
    this.createEmployeeDTO();
    if((this.employees.filter(employee => employee.email == this.employeeData.email).length == 1) 
     && this.selectedEmployee.email != this.employeeData.email) {
      this.messageService.add({
        key: 'tc',
        severity: 'warn',
        summary: 'Fail',
        detail: 'Employee with email ' + this.employeeData.email + ' already exists',
      });
      this.clearInputFields();
      return false;
    }
    return true;
  }

  createEmployeeDTO(){
    this.employeeData.id = this.selectedEmployee.id;
    this.employeeData.name = this.nameInput;
    this.employeeData.surname = this.surnameInput;
    this.employeeData.email = this.emailInput;
    this.employeeData.pincode = this.pincodeInput;
    this.employeeData.salary = this.salaryInput;
    this.employeeData.employeeType = this.typeInput.value;
    this.employeeData.birthday = new Date();
    this.employeeData.type = 'EMPLOYEE';
  }

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

  isLastPage(): boolean {
    return this.employees
      ? this.first === this.employees.length - this.rows
      : true;
  }

  isFirstPage(): boolean {
    return this.employees ? this.first === 0 : true;
  }
}
