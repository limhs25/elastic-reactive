import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LogicsComponent} from "./logics/logics.component";
import {LogicEditorComponent} from "./logics/logic-editor/logic-editor.component";
import {DashboardsComponent} from "./dashboards/dashboards.component";

const routes: Routes = [
  {path: 'logic', component: LogicEditorComponent},
  {path: 'logic/:id', component: LogicEditorComponent},
  {path: 'logics', component: LogicsComponent},
  {path: 'dashboard', component: DashboardsComponent},
  {path: '**', component: LogicsComponent},
  {path: '', redirectTo: '/logics', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
