import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app.routes';
import { CommonModule } from '@angular/common';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './auth.interceptor';


@NgModule({
  imports:      [ 
    BrowserModule,
    FormsModule,
    CommonModule,
    AppRoutingModule

 ],
  providers:    [ {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }], 
  declarations: [
  ],
  exports:      [],
  bootstrap:    []
})
export class AppModule { }