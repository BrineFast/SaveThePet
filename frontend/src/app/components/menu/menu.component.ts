import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { faScroll, faQuestion, faAddressCard, faCogs } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  menuItems = [
    {
      title: 'Tracks',
      path: '/tracks/browse',
      isActive: false,
      icon: faScroll
    },
    {
      title: 'Skill Sets',
      path: '/skill-sets/browse',
      isActive: false,
      icon: faCogs
    },
    {
      title: 'Questions',
      path: '/questions/browse',
      isActive: false,
      icon: faQuestion
    },
    {
      title: 'Interview Session',
      path: '/interviews/browse',
      isActive: false,
      icon: faAddressCard
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
