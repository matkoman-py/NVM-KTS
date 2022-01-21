import { Component, OnInit } from '@angular/core';
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

  constructor(private tableLayoutService: TableLayoutService) { }

  ngOnInit(): void {
    this.canvas = new fabric.Canvas('demoCanvas');
    this.deserialize();
  }

  deserialize() {
    this.tableLayoutService.getTableLayout()
        .subscribe((data: any) => this.canvas.loadFromJSON(data, this.canvas.renderAll.bind(this.canvas)));
    
    this.canvas.forEachObject((o : any) => {
      o.selectable = false;
      o.on('mousedown', function(e: any) {
        console.log(o.id, o.order_id);
        console.log(o);
        if(o.order_id !== "") {
          o._objects[0].set('fill', 'green');
        }
      })
    })
    this.canvas.renderAll();
  }

}
