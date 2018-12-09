// Angularos dolgok
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule, MatToolbarModule, MatButtonModule, MatFormFieldModule, MatInputModule } from '@angular/material';
import { RoutingModule } from './routing/routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ArukComponent } from './components/aruk/aruk.component';
import { ChartComponent } from './components/chart/chart.component';
import { IndexComponent } from './components/index/index.component';
import { WaresService } from './wares.service';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';


// Materialize cuccos
import { MaterializeModule } from 'ngx-materialize';
import { RegComponent } from './components/reg/reg.component';
@NgModule({
  declarations: [
    AppComponent,
    ArukComponent,
    ChartComponent,
    IndexComponent,
    LoginComponent,
    RegComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    RoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MaterializeModule, // ez betolja a teljes modult és kész...
  ],
  providers: [WaresService],
  bootstrap: [AppComponent]
})
export class AppModule { }
