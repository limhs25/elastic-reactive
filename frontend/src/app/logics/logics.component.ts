import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Logic} from "../../model/logic";
import {LogicService} from "../../services/logic.service";
import {Subscription} from "rxjs/internal/Subscription";
import {Constants} from "../../model/constants";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-logics',
  templateUrl: './logics.component.html',
  styleUrls: ['./logics.component.css']
})
export class LogicsComponent implements OnInit, OnDestroy {

  @Input()
  logics: Logic[];
  query: string = "";
  page: number = 1;
  end: false;

  subscription: Subscription;
  events: Subscription;

  constructor(private logicService: LogicService,
              private snackBar: MatSnackBar) {
    this.subscription = this.logicService.querySource$.subscribe(query => this.executeSearch(query));
    this.events = this.logicService.getIndicatorsStream().subscribe(logic => this.updateLogic(logic));
  }

  ngOnInit() {
    this.executeSearch();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.events.unsubscribe();
  }

  private updateLogic(logic: Logic) {
    const number = this.logics.findIndex(e => e.id === logic.id);
    if (this.logics[number].active !== logic.active) {
      this.logics[number].active = logic.active;
      this.snackBar.open("Logic entry updated with success by other user", Constants.DISMISS)
    }
  }

  private executeSearch(query: string = "") {
    this.page = 1;
    this.query = query;
    this.end = false;
    this.logicService.search(this.query, this.page).subscribe(result => this.logics = result)
  }

  private pageChanged() {
    this.page++;
    this.logicService.search(this.query, this.page).subscribe(result => this.logics = result)
  }

  onDeleted(event) {
    this.logics = this.logics.filter(obj => obj !== event);
  }
}
