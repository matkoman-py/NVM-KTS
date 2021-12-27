export interface Employee{
    id?: number;
    email?: string;
    name?: string;
    surname?: string;
    birthday?: Date;
    employeeType?: string;
    salary?: number;
    pincode?: number;
    type?: string;
}

export interface EmployeesDTO {
    employees: Employee[];
}