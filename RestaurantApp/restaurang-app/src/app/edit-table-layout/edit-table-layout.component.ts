import { Component, OnInit } from '@angular/core';
import { fabric } from 'fabric';
import { EditTableLayoutService } from './services/edit-table-layout.service';

@Component({
  selector: 'app-edit-table-layout',
  templateUrl: './edit-table-layout.component.html',
  styleUrls: ['./edit-table-layout.component.css'],
})
export class EditTableLayoutComponent implements OnInit {

  canvas = new fabric.Canvas('demoCanvas');

  numOfSeats = [2, 4, 6];
  selectedNumOfSeats = 0;
  table_num = 0;
  selectedTable = new fabric.Object();

  constructor(
    private editTableLayoutService: EditTableLayoutService
    ) { }

  ngOnInit(): void {
    this.canvas = new fabric.Canvas('demoCanvas');

    this.loadTableLayout();
    console.log(this.canvas)
    this.table_num = this.canvas.getObjects().length + 1;
  
  }

  loadTableLayout() {
    this.editTableLayoutService.getTableLayout()
        .subscribe((data: any) => this.canvas.loadFromJSON(data, this.canvas.renderAll.bind(this.canvas)))
  }

  addTable() {
    console.log(this.selectedNumOfSeats);
      if(this.selectedNumOfSeats === 2) {
        var table = this.makeTableWithTwoSeats();
        this.canvas.add(table);
      } else if(this.selectedNumOfSeats === 4) {
        var table = this.makeTableWithFourSeats();
        this.canvas.add(table);
      } else if(this.selectedNumOfSeats === 6) {
        var table = this.makeTableWithSixSeats();
        this.canvas.add(table);
      } else {

      }
  }

  makeTableWithSixSeats() {
    var rect: any = this.makeTable(200);

    var chair1 = new fabric.Circle({
      left: rect.left - 40,
      top: rect.top + 30,
      radius: 20
    })

    var chair2 = new fabric.Circle({
      left: rect.left + 200,
      top: rect.top + 30,
      radius: 20
    })

    var chair3 = new fabric.Circle({
      left: rect.left + 30,
      top: rect.top - 40,
      radius: 20
    })

    var chair4 = new fabric.Circle({
      left: rect.left + 30,
      top: rect.top + 100,
      radius: 20
    })

    var chair5 = new fabric.Circle({
      left: rect.left + 130,
      top: rect.top - 40,
      radius: 20
    })

    var chair6 = new fabric.Circle({
      left: rect.left + 130,
      top: rect.top + 100,
      radius: 20
    })

    var text = new fabric.Text('Table ' + (++this.table_num), {
      fontSize: 25,
      fontFamily: "sans-serif",
      left: rect.left + 60,
      top: rect.top + 33,
    });

    var group = new fabric.Group([rect, chair1, chair2, chair3, chair4, chair5, chair6, text]);


    return group;
  }

  makeTableWithFourSeats() {
    var rect: any = this.makeTable(100);

    var chair1 = new fabric.Circle({
      left: rect.left - 40,
      top: rect.top + 30,
      radius: 20
    })

    var chair2 = new fabric.Circle({
      left: rect.left + 100,
      top: rect.top + 30,
      radius: 20
    })

    var chair3 = new fabric.Circle({
      left: rect.left + 30,
      top: rect.top - 40,
      radius: 20
    })

    var chair4 = new fabric.Circle({
      left: rect.left + 30,
      top: rect.top + 100,
      radius: 20
    })

    var text = new fabric.Text('Table ' + (++this.table_num), {
      fontSize: 25,
      fontFamily: "sans-serif",
      left: rect.left + 10,
      top: rect.top + 33,
    });

    var group = new fabric.Group([rect, chair1, chair2, chair3, chair4, text]);

    group.on('selected', e => {
      this.selectedTable = group;
      console.log('slected');
    })

    return group;
  }

  makeTableWithTwoSeats() {
    var rect: any = this.makeTable(100);

    var chair1 = new fabric.Circle({
      left: rect.left - 40,
      top: rect.top + 30,
      radius: 20
    })

    var chair2 = new fabric.Circle({
      left: rect.left + 100,
      top: rect.top + 30,
      radius: 20
    })

    var text = new fabric.Text('Table ' + (++this.table_num), {
      fontSize: 25,
      fontFamily: "sans-serif",
      left: rect.left + 10,
      top: rect.top + 33,
    });

    var group = new fabric.Group([rect, chair1, chair2, text]);

    group.on('selected', e => {
      this.selectedTable = group;
    })

    group.toObject = ((toObject) => {
      return (propertiesToInclude) => {
        return fabric.util.object.extend(toObject.call(group, propertiesToInclude), {
          id: this.table_num,
          order_id: ""
        });
      };
    })(group.toObject);

    return group;
  }

  makeTable(width: any) {
    return new fabric.Rect({
      left: this.canvas.width! / 2,
      top: this.canvas.height! / 2,
      fill: 'gray',
      width: width,
      height: 100,
    })

  }

  saveLayout() {
    this.editTableLayoutService.postTableLayout(this.canvas.toJSON())
        .subscribe(res => alert("saved"));
  }

  removeTable() {
    var itemsToRemove = this.canvas.getActiveObject();
    this.canvas.remove(itemsToRemove);
    this.table_num = 0;
    
    this.canvas.forEachObject((o : any) => {
      o.id = ++this.table_num;
      let text : fabric.Text = <fabric.Text> o.getObjects("text")[0];
      text.set('text', "Table " + this.table_num);
    })
    
    this.canvas.renderAll();
  }

}