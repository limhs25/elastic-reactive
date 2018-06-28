import { Component, OnInit } from '@angular/core';
import {LogicService} from "../../../services/logic.service";

@Component({
  selector: 'app-logic-search',
  templateUrl: './logic-search.component.html',
  styleUrls: ['./logic-search.component.css']
})
export class LogicSearchComponent implements OnInit {

  constructor(private logicService: LogicService) { }

  ngOnInit() {
  }

  search(query: string): void {
    // if (query.length > 2 && query !== undefined) {
      this.logicService.reload(query);
    // }
  }
}
