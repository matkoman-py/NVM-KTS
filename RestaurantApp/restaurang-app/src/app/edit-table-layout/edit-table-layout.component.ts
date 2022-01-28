import { Component, OnInit } from '@angular/core';
import { fabric } from 'fabric';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import { TableSerialization } from '../modules/shared/models/table';
import { EditTableLayoutService } from './services/edit-table-layout.service';

@Component({
  selector: 'app-edit-table-layout',
  templateUrl: './edit-table-layout.component.html',
  styleUrls: ['./edit-table-layout.component.css'],
  providers: [MessageService]
})
export class EditTableLayoutComponent implements OnInit {
  canvas = new fabric.Canvas('demoCanvas');

  numOfSeats = [2, 4, 6];
  selectedNumOfSeats = 0;
  table_num = 0;
  selectedTable = new fabric.Object();

  constructor(
    private editTableLayoutService: EditTableLayoutService,
    private messageService: MessageService,
    private primengConfig: PrimeNGConfig
    ) { }


  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.canvas = new fabric.Canvas('demoCanvas');

    this.loadTableLayout();
    this.table_num = this.canvas.getObjects().length + 1;
  }

  loadTableLayout() {
    this.editTableLayoutService.getTableLayout()
        .subscribe((data: any) =>{
        this.canvas.loadFromJSON(data, () => this.canvas.renderAll())
      
        this.table_num = 0;

        this.canvas.forEachObject((o: any) => {
          let table: fabric.Rect = o.getObjects('rect')[0];
          table.set('fill', 'gray');
          
          this.table_num++;

          o.toObject = ((toObject) => {
            return (propertiesToInclude:any) => {
              return fabric.util.object.extend(toObject.call(o, propertiesToInclude), {

                id: o.id,
                order_id: '',
              }
            );
          };
        })(o.toObject);
      });

      this.canvas.renderAll();
    });
  }

  addTable() {
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
        this.messageService.add({key: 'tc', severity:'success', summary: 'Select table seat number!', detail: 'Warning'});
        return;
      }
      this.messageService.add({key: 'tc', severity:'success', summary: 'Table successfully added!', detail: 'Added'});
  }

  makeTableWithSixSeats() {
    var rect: any = this.makeTable(200);

    var chair1 = new fabric.Circle({
      left: rect.left - 40,
      top: rect.top + 30,
      radius: 20,
    });

    var chair2 = new fabric.Circle({
      left: rect.left + 200,
      top: rect.top + 30,
      radius: 20,
    });

    var chair3 = new fabric.Circle({
      left: rect.left + 30,
      top: rect.top - 40,
      radius: 20,
    });

    var chair4 = new fabric.Circle({
      left: rect.left + 30,
      top: rect.top + 100,
      radius: 20,
    });

    var chair5 = new fabric.Circle({
      left: rect.left + 130,
      top: rect.top - 40,
      radius: 20,
    });

    var chair6 = new fabric.Circle({
      left: rect.left + 130,
      top: rect.top + 100,
      radius: 20,
    });

    var text = new fabric.Text('Table ' + (++this.table_num), {
      fontSize: 25,
      fontFamily: 'sans-serif',
      left: rect.left + 60,
      top: rect.top + 33,
    });

    var group = new fabric.Group([
      rect,
      chair1,
      chair2,
      chair3,
      chair4,
      chair5,
      chair6,
      text,
    ]);

    group.on('selected', (e) => {
      this.selectedTable = group;
    });

    let temp = this.table_num;

    group.toObject = ((toObject) => {
      return (propertiesToInclude) => {
        return fabric.util.object.extend(
          toObject.call(group, propertiesToInclude),
          {
            id: temp,
            order_id: '',
          }
        );
      };
    })(group.toObject);

    return group;

    return group;
  }

  makeTableWithFourSeats() {
    var rect: any = this.makeTable(100);

    var chair1 = new fabric.Circle({
      left: rect.left - 40,
      top: rect.top + 30,
      radius: 20,
    });

    var chair2 = new fabric.Circle({
      left: rect.left + 100,
      top: rect.top + 30,
      radius: 20,
    });

    var chair3 = new fabric.Circle({
      left: rect.left + 30,
      top: rect.top - 40,
      radius: 20,
    });

    var chair4 = new fabric.Circle({
      left: rect.left + 30,
      top: rect.top + 100,
      radius: 20,
    });

    var text = new fabric.Text('Table ' + (++this.table_num), {
      fontSize: 25,
      fontFamily: 'sans-serif',
      left: rect.left + 10,
      top: rect.top + 33,
    });

    var group = new fabric.Group([rect, chair1, chair2, chair3, chair4, text]);

    group.on('selected', (e) => {
      this.selectedTable = group;
    });

    let temp = this.table_num;

    group.toObject = ((toObject) => {
      return (propertiesToInclude) => {
        return fabric.util.object.extend(
          toObject.call(group, propertiesToInclude),
          {
            id: temp,
            order_id: '',
          }
        );
      };
    })(group.toObject);

    return group;
  }

  makeTableWithTwoSeats() {
    var rect: any = this.makeTable(100);

    var chair1 = new fabric.Circle({
      left: rect.left - 40,
      top: rect.top + 30,
      radius: 20,
    });

    var chair2 = new fabric.Circle({
      left: rect.left + 100,
      top: rect.top + 30,
      radius: 20,
    });

    var text = new fabric.Text('Table ' + ++this.table_num, {
      fontSize: 25,
      fontFamily: 'sans-serif',
      left: rect.left + 10,
      top: rect.top + 33,
    });

    let group = new fabric.Group([rect, chair1, chair2, text]);

    group.on('selected', (e) => {
      this.selectedTable = group;
    });

    let temp = this.table_num;

    group.toObject = ((toObject) => {
      return (propertiesToInclude) => {
        return fabric.util.object.extend(
          toObject.call(group, propertiesToInclude),
          {
            id: temp,
            order_id: '',
          }
        );
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
    });
  }

  saveLayout() {
    this.table_num = 0;

    this.canvas.forEachObject((o: any) => {
      let temp = ++this.table_num;

      o.toObject = ((toObject) => {
        return (propertiesToInclude: any) => {
          return fabric.util.object.extend(
            toObject.call(o, propertiesToInclude),
            {
              id: temp,
              order_id: '',
            }
          );
        };
      })(o.toObject);
    });
    
    this.editTableLayoutService.postTableLayout(JSON.stringify(this.canvas))
        .subscribe(res => this.messageService.add({key: 'tc', severity:'success', summary: 'Table layout saved!', detail: 'Saved'})
        );    
  }

  removeTable() {
    var itemsToRemove = this.canvas.getActiveObject();
    this.canvas.remove(itemsToRemove);
    this.table_num = 0;

    this.canvas.forEachObject((o: any) => {
      let text: fabric.Text = <fabric.Text>o.getObjects('text')[0];
      text.set('text', 'Table ' + ++this.table_num);

      let temp = this.table_num;

      o.toObject = ((toObject) => {
        return (propertiesToInclude: any) => {
          return fabric.util.object.extend(
            toObject.call(o, propertiesToInclude),
            {
              id: temp,
              order_id: '',
            }
          );
        };
      })(o.toObject);
    });
    
    this.messageService.add({key: 'tc', severity:'error', summary: 'Table deleted!', detail: 'Remove'});

    this.canvas.renderAll();
  }
}
