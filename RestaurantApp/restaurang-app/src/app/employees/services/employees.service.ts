import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Employee } from 'src/app/modules/shared/models/employee';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
@Injectable({
  providedIn: 'any',
})
export class EmployeesService {
  private header = new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Bearer ' + localStorage.getItem('token'),
  });
  constructor(private http: HttpClient) {}

  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>('/api/employee', {
      headers: this.header,
    });
  }

  deleteEmployee(employeeId: number | undefined): Observable<Employee> {
    return this.http.delete<Employee>('/api/employee/' + employeeId, {
      headers: this.header,
    });
  }

  updateEmployee(employeeData: Employee): Observable<Employee> {
    return this.http.put<Employee>(
      '/api/employee/' + employeeData.id,
      employeeData,
      {
        headers: this.header,
      }
    );
  }

  createEmployee(employeeData: Employee): Observable<Employee> {
    return this.http.post<Employee>('/api/employee', employeeData, {
      headers: this.header,
    });
  }

  getEmployeeIdByPincode(employeePin: number | undefined) {
    return this.http.get<boolean>('/api/employee/getByPincode/' + employeePin, {
      headers: this.header,
    });
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
      .set('dateAfter', dateAfter);
    return this.http.get<Employee[]>(`api/employee/search`, {
      params: params,
      headers: this.header,
    });
  }
}
