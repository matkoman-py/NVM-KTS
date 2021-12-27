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
}
