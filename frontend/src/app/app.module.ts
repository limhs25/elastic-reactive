import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {LogicsComponent} from './logics/logics.component';
import {LogicSearchComponent} from './logics/logic-search/logic-search.component';
import {LogicComponent} from './logics/logic/logic.component';
import {LogicService} from "../services/logic.service";
import {HttpClientModule} from "@angular/common/http";
import {
  MAT_SNACK_BAR_DEFAULT_OPTIONS,
  MatButtonModule, MatCardModule,
  MatCheckboxModule, MatIconModule,
  MatInputModule,
  MatSlideToggleModule,
  MatSnackBarModule, MatToolbarModule, MatTooltipModule
} from "@angular/material";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgxChartsModule} from "@swimlane/ngx-charts";
import {DashboardsComponent} from "./dashboards/dashboards.component";
import {LogicEditorComponent} from './logics/logic-editor/logic-editor.component';
import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app-routing.module";
import 'hammerjs';
import {HashLocationStrategy, LocationStrategy} from "@angular/common";

@NgModule({
  declarations: [
    AppComponent,
    LogicsComponent,
    LogicSearchComponent,
    LogicComponent,
    DashboardsComponent,
    LogicEditorComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    NgxChartsModule,
    HttpClientModule,
    MatButtonModule,
    MatSlideToggleModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatCardModule,
    MatIconModule,
    MatToolbarModule,
    MatTooltipModule,
    MatInputModule
  ],
  providers: [LogicService,
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 2000}}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
