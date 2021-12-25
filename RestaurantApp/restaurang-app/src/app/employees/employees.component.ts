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

  isLastPage(): boolean {
    return this.employees
      ? this.first === this.employees.length - this.rows
      : true;
  }

  isFirstPage(): boolean {
    return this.employees ? this.first === 0 : true;
  }
}
