import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { faMapMarked, faDog, faBoxOpen, faEnvelope } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  menuItems = [
    {
      title: 'Map',
      path: '',
      isActive: false,
      icon: faMapMarked,
    },
    {
      title: 'Pets',
      path: '',
      isActive: false,
      icon: faDog
    },
    {
      title: 'Shelters',
      path: '',
      isActive: false,
      icon: faBoxOpen
    },
    {
      title: 'Messages',
      path: '',
      isActive: false,
      icon: faEnvelope,
    }
  ]
  constructor(private router: Router, private location: Location) { }

  changeItem(index) {
    this.menuItems.find((item) => item.isActive).isActive = false;
    this.menuItems[index].isActive = true;
  }

  ngOnInit() {
    const currentPath = this.location.path().split("/")[1];
    const paths = {
      "tracks": 0,
      "skill-sets": 1,
      "questions": 2,
      "interviews": 3
    };
    this.menuItems[paths[currentPath]].isActive = true;
  }
}
