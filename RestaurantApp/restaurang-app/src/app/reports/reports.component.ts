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
import { ArticleProfit } from '../modules/shared/models/articleProfit';
import { ProfitDTO } from '../modules/shared/models/articleProfit';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css'],
  providers: [MessageService],
})
export class ReportsComponent implements OnInit {
  articleReportData: ArticleProfit;
  articleYearInput: number = 2021;
  articleMonthYearInput: number = 2021;
  articleQuarterInput: any;
  articleQuarterYearInput: number = 2021;
  articleMonthInput: any;
  date1: Date = new Date('01/3/2021');
  date2: Date = new Date('01/1/2021');
  date3: Date = new Date('12/30/2021');

  data: any;
  categories: any[] = 
    [{name: 'Profit', key: 'earning'},
    {name: 'Number of orders', key: 'numberOfOrders'}, 
    {name: 'Total making price', key: 'totalMakingPrice'}, 
    {name: 'Total selling price', key: 'totalSellingPrice'}];
  
  quarters: any[] = [
    { name: 'First', value: '1' },
    { name: 'Second', value: '2' },
    { name: 'Third', value: '3' },
    { name: 'Fourth', value: '4' },
  ];

  months: any[] = [
    { name: 'January', value: '1' },
    { name: 'February', value: '2' },
    { name: 'March', value: '3' },
    { name: 'April', value: '4' },
    { name: 'May', value: '5' },
    { name: 'June', value: '6' },
    { name: 'July', value: '7' },
    { name: 'August', value: '8' },
    { name: 'September', value: '9' },
    { name: 'October', value: '10' },
    { name: 'November', value: '11' },
    { name: 'December', value: '12' },
  ];

  selectedCategory: any = null;

  constructor(
    private reportsService: ReportsService,
    private messageService: MessageService,
    private primengConfig: PrimeNGConfig,
    private router: Router
  ) {}

  ngOnInit(): void {
    // this.date1.setMilliseconds();
    this.primengConfig.ripple = true;
    this.selectedCategory = this.categories[1];
    this.getArticleYearlyData();
  }

  handleChange(event: any) {
    if(event.index == 0) this.getArticleYearlyData();
    else if(event.index == 1) this.getArticleQuarterData();
    else if(event.index == 2) this.getArticleMonthData();
    else if(event.index == 3) this.getArticleDayData();
    else if(event.index == 4) this.getArticleFromToData();
  }

  getArticleFromToData(){
    this.reportsService.articleProfitBetweenDates(this.date2, this.date3).subscribe((data) => {
      this.articleReportData = data;
      this.fixUpData();
    },err => {
      this.messageService.add({
          key: 'tc',
          severity: 'warn',
          summary: 'No data available',
          detail: err.error,
          });
      console.log(err.error);
      this.makeEmptyData();
      this.articleReportData.articleProfits = new Map();
    });
  }

  getArticleDayData(){
    let year = this.date1.getFullYear();
    let month = this.date1.getMonth() + 1;
    let day = this.date1.getUTCDate();
    this.reportsService.articleProfitDay(year, month, day).subscribe((data) => {
      this.articleReportData = data;
      this.fixUpData();
    },err => {
      this.messageService.add({
          key: 'tc',
          severity: 'warn',
          summary: 'No data available',
          detail: err.error,
          });
      console.log(err.error);
      this.makeEmptyData();
      this.articleReportData.articleProfits = new Map();
    });
  }

  getArticleMonthData(){
    this.reportsService.articleProfitMonth(this.articleMonthYearInput, this.articleMonthInput.value).subscribe((data) => {
      this.articleReportData = data;
      this.fixUpData();
    },err => {
      this.messageService.add({
          key: 'tc',
          severity: 'warn',
          summary: 'No data available',
          detail: err.error,
          });
      console.log(err.error);
      this.makeEmptyData();
      this.articleReportData.articleProfits = new Map();
    });
  }

  getArticleQuarterData() {
    this.reportsService.articleProfitQuarter(this.articleQuarterYearInput, this.articleQuarterInput.value).subscribe((data) => {
      this.articleReportData = data;
      this.fixUpData();
    },err => {
      this.messageService.add({
          key: 'tc',
          severity: 'warn',
          summary: 'No data available',
          detail: err.error,
          });
      console.log(err.error);
      this.makeEmptyData();
      this.articleReportData.articleProfits = new Map();
    });
  }
  
  getArticleYearlyData(){
    this.reportsService.articleProfitYearReport(this.articleYearInput).subscribe((data) => {
      this.articleReportData = data;
      this.fixUpData();
    },err => {
      this.messageService.add({
          key: 'tc',
          severity: 'warn',
          summary: 'No data available',
          detail: err.error,
          });
      console.log(err.error);
      this.makeEmptyData();
      this.articleReportData.articleProfits = new Map();
    });
  }

  makeEmptyData() {
    this.data = {
      labels: ['No data availabe'],
      datasets: [{
        data: [1],
        backgroundColor: ['#808080'],
      }]
    };
  }

  fixUpData() {
    if(this.articleReportData.articleProfits.size == 0) {
      this.makeEmptyData();
      return;
    }
    this.data = {
      labels: this.getNames(),
      datasets: [{
        data: this.getData(this.selectedCategory.key),
        backgroundColor: this.getRandomColors(),
      }]
    };
  }

  getNames() {
    let names: string[] = [];
    Object.entries(this.articleReportData.articleProfits).forEach(([key, value])=>{
      console.log(key, value.articleName)
      names.push(value.articleName);
    })
    console.log(names);
    return names;
  }

  getData(typeOfData: string) {
    let data: number[] = [];
    Object.entries(this.articleReportData.articleProfits).forEach(([key, value])=>{
      //console.log(key, value)
      switch(typeOfData){
        case 'earning':{
          data.push(value.earning);
          break;    
        }
        case 'numberOfOrders':{
          data.push(value.numberOfOrders);
          break;    
        } 
        case 'totalMakingPrice':{
          data.push(value.totalMakingPrice);
          break;    
        } 
        case 'totalSellingPrice':{
          data.push(value.totalSellingPrice);
          break;    
        }  
      }
    })
    console.log(data);
    return data;
  }

  getRandomColors() {
    let colors: string[] = [];
    Object.entries(this.articleReportData.articleProfits).forEach(([key, value])=>{
      colors.push("#" + ((1<<24)*Math.random() | 0).toString(16));
    })
    return colors;
  }
}
    // private Double earning;
    // private int numberOfOrders;
    // private Double totalMakingPrice;
    // private Double totalSellingPrice;