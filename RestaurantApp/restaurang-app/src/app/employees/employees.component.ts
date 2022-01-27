import {
  Component,
  OnInit
} from '@angular/core';
import {
  EmployeesService
} from './services/employees.service';
import {
  Employee,
  EmployeeType
} from '../modules/shared/models/employee';
import {
  MessageService,
  PrimeNGConfig
} from 'primeng/api';
import { format } from 'path/posix';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css'],
  providers: [MessageService],
})
export class EmployeesComponent implements OnInit {
  employees: Employee[] = [];
  date1: Date = new Date();
  formatedDate: Date;
  selectedEmployee: Employee | null = null;
  employee: Employee ={};
  updatedEmployee: Employee = {};
  dateFrom: Date = new Date(1900,0,1);
  dateTo: Date = new Date();
  employeeTypes: EmployeeType[] = [{
      name: 'WAITER',
      value: 'WAITER'
    },
    {
      name: 'BARMAN',
      value: 'BARMAN'
    },
    {
      name: 'COOK',
      value: 'COOK'
    },
  ];
  employeeType: EmployeeType = {name:'BARMAN', value:'BARMAN'};
  nameSearchParam = '';
  surnameSearchParam = '';
  emailSearchParam = '';
  pincodeSearchParam = '';

  employeeData: Employee = {};
  nameInput: string | undefined = '';
  typeInput: EmployeeType = {
    name: '',
    value: ''
  };
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


  search() {
    var df: string = "";
    var dt: string = "";
    if(this.dateFrom) {
      df = this.dateFrom.getFullYear() + "-" + (this.dateFrom.getMonth()+1) + "-" + this.dateFrom.getDate()
      console.log(df);
    }
    if(this.dateTo) {
      dt = this.dateTo.getFullYear() + "-" + (this.dateTo.getMonth()+1) + "-" + this.dateTo.getDate()
    }
    this.employeesService
      .search(this.nameSearchParam, 
            this.surnameSearchParam, 
            this.emailSearchParam, 
            df,
            dt).subscribe((data) => {
      this.employees = data;
    });
  }
  bla() {
    console.log("DSADS")
  }

  getEmployees() {
    this.employeesService.getEmployees().subscribe((data) => {
      this.employees = data;
    });
  }

  deleteEmployee() {
    this.employeesService.deleteEmployee(this.selectedEmployee!.id).subscribe((deletedEmployee) => {
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: 'Employee with pincode ' + deletedEmployee.pincode + ' succesfully deleted',
      });
      this.getEmployees();
      this.selectedEmployee = null;
    });
  }

  formatToDate(birthday: string) : string {
    this.formatedDate = new Date(birthday);
    this.formatedDate.setTime( this.formatedDate.getTime() - new Date().getTimezoneOffset()*60*1000 );
    return this.formatedDate.toDateString();
  }

  checkEmployeeValid() : boolean {
    if((this.employee.name || '').toString().trim().length === 0) {
      return false;
    }
    if((this.employee.surname || '').toString().trim().length === 0) {
      return false;
    }
    if((this.employee.email || '').toString().trim().length === 0) {
      return false;
    }
    if(this.employeeType.value != 'BARMAN' && this.employeeType.value != 'COOK' && this.employeeType.value != 'WAITER') {
      return false;
    }
    if(!this.date1) {
      return false;
    }
    if(!this.employee.salary) {
      return false;
    }
    if(!this.employee.pincode) {
      return false;
    }
    return true;
  }


  mapToEmployee(e: Employee): void {
    this.employee.name = e.name;
    this.employee.id = e.id;
    this.employee.surname = e.surname;
    this.employee.birthday = e.birthday;
    this.employee.email = e.email;
    this.employee.pincode = e.pincode;
    this.employee.salary = e.salary;
    this.employee.type = e.type;
    this.employeeType = {name:e.employeeType, value:e.employeeType};
    this.employee.employeeType = this.employeeType.value;
    this.date1 = new Date(e.birthday?.toString()!);
    this.employee.birthday = this.date1;
    console.log(this.employee);
  }

  createEmployee() {
    //if (!this.createEmployeeData()) return;

    this.employee.birthday = this.date1;
    this.employee.employeeType = this.employeeType.value;

    this.employeesService.createEmployee(this.employee).subscribe((createdEmployee) => {
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
    this.employee.employeeType = this.employeeType.value;
    this.date1 ? this.updatedEmployee.birthday = this.date1 : this.updatedEmployee.birthday = this.employee.birthday;
    this.employee.name?.trim().length == 0 ? this.updatedEmployee.name = this.selectedEmployee?.name : this.updatedEmployee.name = this.employee.name;
    this.employee.surname?.trim().length == 0 ? this.updatedEmployee.surname = this.selectedEmployee?.surname : this.updatedEmployee.surname = this.employee.surname;
    this.employee.email?.trim().length == 0 ? this.updatedEmployee.email = this.selectedEmployee?.email : this.updatedEmployee.email = this.employee.email;
    this.employee.pincode ? this.updatedEmployee.pincode = this.employee?.pincode : this.updatedEmployee.pincode = this.selectedEmployee?.pincode;
    this.employee.salary ? this.updatedEmployee.salary = this.employee?.salary : this.updatedEmployee.salary = this.selectedEmployee?.salary;
    this.updatedEmployee.employeeType = this.employee.employeeType;
    this.updatedEmployee.id = this.employee.id;


    this.employeesService.updateEmployee(this.updatedEmployee).subscribe((emp) => {
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: 'Employee with pincode ' + emp.pincode + ' succesfully deleted',
      });
      this.getEmployees();
    });

    this.clearInputFields();
  }

  isSelected(): boolean {
    if (Object.keys(this.selectedEmployee!).length === 0) return true;
    return false;
  }

  clearInputFields() {
    this.nameInput = '';
    this.surnameInput = '';
    this.emailInput = '';
    this.typeInput = {
      name: '',
      value: ''
    };
    this.salaryInput = undefined;
    this.pincodeInput = undefined;

    this.selectedEmployee = {};
  }

  isDataValid(): boolean {
    if (this.nameInput == '') return true;
    if (this.surnameInput == '') return true;
    if (this.emailInput == '') return true;
    if (this.pincodeInput == undefined) return true;
    if (this.salaryInput == undefined) return true;
    if (this.typeInput.name == '') return true;
    return false;
  }

  createEmployeeData(): boolean {
    this.createEmployeeDTO();
    if (this.employees.filter(employee => employee.email == this.employeeData.email).length != 0) {
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

  updateEmployeeData(): boolean {
    this.createEmployeeDTO();
    if ((this.employees.filter(employee => employee.email == this.employeeData.email).length == 1) &&
      this.selectedEmployee!.email != this.employeeData.email) {
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

  createEmployeeDTO() {
    this.employeeData.id = this.selectedEmployee!.id;
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
    return this.employees ?
      this.first === this.employees.length - this.rows :
      true;
  }

  isFirstPage(): boolean {
    return this.employees ? this.first === 0 : true;
  }
}
