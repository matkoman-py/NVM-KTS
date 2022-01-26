import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { fabric } from 'fabric';
import { TableLayoutService } from './services/table-layout.service';


@Component({
  selector: 'app-table-layout',
  templateUrl: './table-layout.component.html',
  styleUrls: ['./table-layout.component.css']
})
export class TableLayoutComponent implements OnInit {

  private canvas = new fabric.Canvas('demoCanvas');
  private json = "";

  constructor(
    private tableLayoutService: TableLayoutService,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.canvas = new fabric.Canvas('demoCanvas');
    this.deserialize();
  }

  deserialize() {
    this.tableLayoutService.getTableLayout()
        .subscribe((data: any) => this.canvas.loadFromJSON(data, () => {
          this.canvas.renderAll();
          this.setTableEvents();
    }));
  }

  setTableEvents() {
    this.canvas.forEachObject((o : any) => {
      o.selectable = false;
      o.order_id = 1;
      if(o.order_id !== undefined)
        o.getObjects('rect')[0].set('fill', 'green');
      o.on('mousedown', (e : any) => {
        if(o.order_id !== undefined) {
          // ovde se radi rutiranje na stranicu
        }
      })
    })
    this.canvas.renderAll();
  }

}
