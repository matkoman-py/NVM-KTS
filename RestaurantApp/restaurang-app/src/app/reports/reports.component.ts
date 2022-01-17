import {
  Component,
  OnInit
} from '@angular/core';
import {
  ReportsService
} from './services/reports.service';
import {
  MessageService,
  PrimeNGConfig
} from 'primeng/api';
import {
  Router
} from "@angular/router"
import { getLocaleDayNames } from '@angular/common';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css'],
  providers: [MessageService],
})
export class ReportsComponent implements OnInit {
  articleReportData: any;
  data: any;

  constructor(
    private reportsService: ReportsService,
    private messageService: MessageService,
    private primengConfig: PrimeNGConfig,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.reportsService.articleProfitYearReport(2021).subscribe((data) => {
      this.articleReportData = data;

      this.data = {
        labels: this.getNames(),
        datasets: [{
          data: [300, 50, 100],
          backgroundColor: [
            "#FF6384",
            "#36A2EB",
            "#FFCE56"
          ],
          hoverBackgroundColor: [
            "#FF6384",
            "#36A2EB",
            "#FFCE56"
          ]
        }]
      };

    });
  }
  getNames() {
    return ['E', 'F', 'G'];
  }
}
