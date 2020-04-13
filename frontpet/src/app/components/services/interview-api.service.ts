import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import Interview from '../entities/Interview';
import { Observable } from 'rxjs';
import { InterviewsTableRespond } from "../entities/InterviewsTableRespond";

@Injectable({
    providedIn: 'root'
})
export default class InterviewsApiService {
    private URL: string = "http://localhost:4200/assets/interviews.json"

    constructor(private httpClient: HttpClient) {
    }

    getInterviews(): Observable<Interview[]> {
        return this.httpClient.get<Interview[]>(this.URL)
    }

    getInterviewsById(id: number): Observable<Interview> {
        return this.httpClient.get<Interview>(`${this.URL}/${id}`);
    }

    getInterviewsInformation(): Observable<InterviewsTableRespond[]> {
        return this.httpClient.get<InterviewsTableRespond[]>(this.URL);
    }

    addInterview(interview: Interview): Observable<void> {
        return this.httpClient.post<void>(this.URL, interview);
    }

    deleteInterview(id: number): Observable<void> {
        return this.httpClient.delete<void>(this.URL + `/${id}`);
    }

    changeInterview(id: number, interview: Interview): Observable<void> {
        return this.httpClient.put<void>(this.URL + `/${id}`, interview);
    }
}
