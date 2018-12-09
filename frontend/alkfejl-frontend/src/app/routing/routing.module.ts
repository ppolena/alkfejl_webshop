import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ArukComponent } from '../components/aruk/aruk.component';
import { ChartComponent } from '../components/chart/chart.component';
import { IndexComponent } from '../components/index/index.component';
import { RegComponent } from '../components/reg/reg.component';
import { LoginComponent } from '../login/login.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/index',
    pathMatch: 'full',
  },
  {
    path: 'index',
    component: IndexComponent,
  },
  {
    path: 'aruk',
    component: ArukComponent,
  },
  {
    path: 'chart',
    component: ChartComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'reg',
    component: RegComponent,
  },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes)  ],
  exports: [ RouterModule ],
  declarations: [],
})
export class RoutingModule { }