import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import SkillSet from '../entities/SkillSet';
import { Observable } from 'rxjs';
import ISkillSets from '../entities/ISkillSets';

@Injectable({
  providedIn: 'root'
})
export default class SkillSetsApiService {
  private URL: string = "http://localhost:4200/assets/skillSets.json"

  constructor(private httpClient: HttpClient) { 
  }

  getSkillSets(): Observable<SkillSet[]> {
    return this.httpClient.get<SkillSet[]>(this.URL)
  }

  getSkillSetsById(id: number): Observable<SkillSet> {
    return this.httpClient.get<SkillSet>(`${this.URL}/${id}`);
  }

  getSkillSetsInformation(): Observable<ISkillSets[]> {
    return this.httpClient.get<ISkillSets[]>(this.URL);
  }

  addSkillSet(skillSet: SkillSet): Observable<void> {
    return this.httpClient.post<void>(this.URL, skillSet);
  }

  deleteSkillSet(id: number): Observable<void> {
    return this.httpClient.delete<void>(this.URL + `/${id}`);
  }

  changeSkillSet(id: number, skillSet: SkillSet): Observable<void> {
    return this.httpClient.put<void>(this.URL + `/${id}`, skillSet);
  }
}
