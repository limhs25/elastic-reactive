import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Logic} from "../../../model/logic";
import {LogicService} from "../../../services/logic.service";
import {MatSnackBar} from "@angular/material";
import {Constants} from "../../../model/constants";

@Component({
  selector: 'app-logic',
  templateUrl: './logic.component.html',
  styleUrls: ['./logic.component.css']
})
export class LogicComponent implements OnInit, OnDestroy {
  @Input() logic: Logic;
  @Output() deleted: EventEmitter<any> = new EventEmitter();
  @Output() updated: EventEmitter<any> = new EventEmitter();

  constructor(private logicService: LogicService, private snackBar: MatSnackBar) {
  }

  ngOnInit() {
  }

  ngOnDestroy() {
  }

  update(id: string) {
    this.logicService.update(id, this.logic).subscribe(
      () => this.snackBar.open("Logic entry updated with success", Constants.DISMISS, {duration: 500}),
      () => this.snackBar.open("Error while processing request", Constants.DISMISS),
      () => this.updated.emit(this.logic))
  }

  delete(id: string) {
    this.logicService.delete(id).subscribe(
      () => this.snackBar.open("Logic entry deleted with success", Constants.DISMISS),
      () => this.snackBar.open("Error while processing request", Constants.DISMISS),
      () => this.deleted.emit(this.logic))
  }
}
