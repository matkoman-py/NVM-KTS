import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  Employee,
} from 'src/app/modules/shared/models/employee';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
@Injectable({
  providedIn: 'any',
})
export class EmployeesService {
  
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient) {}

  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>('/api/employee');
  }

  deleteEmployee(employeeId: number | undefined) : Observable<Employee>{
    return this.http.delete<Employee>('/api/employee/' + employeeId);
  }

  updateEmployee(employeeData: Employee):Observable<Employee>{
    return this.http.put<Employee>('/api/employee/' + employeeData.id,  employeeData );
  }

  createEmployee(employeeData: Employee):Observable<Employee>{
    return this.http.post<Employee>('/api/employee',  employeeData );
  }

  getEmployeeIdByPincode(employeePin: number | undefined) {
    return this.http.get<boolean>('/api/employee/getByPincode/' + employeePin);
  }

  search(
    nameSearchParam: string,
    surnameSearchParam: string,
    emailSearchParam: string,
    dateBefore: string,
    dateAfter: string
  ): Observable<Employee[]> {
    let params = new HttpParams()
      .set('name', nameSearchParam)
      .set('surname', surnameSearchParam)
      .set('email', emailSearchParam)
      .set('dateBefore', dateBefore)
      .set('dateAfter', dateAfter)
    return this.http.get<Employee[]>(`api/employee/search`, { params: params });
  }
}
