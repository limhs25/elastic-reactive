import {Component, OnInit} from '@angular/core';
import {Logic} from "../../../model/logic";
import {LogicService} from "../../../services/logic.service";
import {ActivatedRoute} from "@angular/router";
import {MatSnackBar} from "@angular/material";
import {Constants} from "../../../model/constants";

@Component({
  selector: 'app-logic-editor',
  templateUrl: './logic-editor.component.html',
  styleUrls: ['./logic-editor.component.css']
})
export class LogicEditorComponent implements OnInit {

  logic: Logic = new Logic();

  constructor(private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private logicService: LogicService) {
  }

  ngOnInit() {
    this.load();
  }

  save(): void {
    this.logicService.create(this.logic).subscribe(
      () => this.snackBar.open("Logic entry created with success", Constants.DISMISS),
      () => this.snackBar.open("Error while processing request", Constants.DISMISS))
  }

  load(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      // no updates
      // this.logicService.loadOne(id).subscribe(res => this.logic = res);
    }
  }
}
