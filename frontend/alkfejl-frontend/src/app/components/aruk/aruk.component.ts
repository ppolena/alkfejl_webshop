import { Component, OnInit } from '@angular/core';
import { WaresService } from '../../wares.service';
import { Ware } from '../../model/Ware';

@Component({
  selector: 'app-aruk',
  templateUrl: './aruk.component.html',
  styleUrls: ['./aruk.component.css']
})
export class ArukComponent implements OnInit {

  constructor(
	private waresService: WaresService
  ) { }


  private sajt()
  {
    return 2*2;
  }
  private sajt2()
  {
	console.log(this.waresService.getWares());
    return this.waresService.getWares();
  }

  ngOnInit() {
	  this.waresService.reqWares();
  }

}
