import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import Question from '../entities/Question';
import { Observable } from 'rxjs';
import {QuestionsTableRespond} from '../entities/QuestionsTableRespond';

@Injectable({
    providedIn: 'root'
})
export default class QuestionsApiService {
    private URL: string = "http://localhost:4200/assets/questions.json"

    constructor(private httpClient: HttpClient) {
    }

    getQuestions(): Observable<Question[]> {
        return this.httpClient.get<Question[]>(this.URL)
    }

    getQuestionsById(id: number): Observable<Question> {
        return this.httpClient.get<Question>(`${this.URL}/${id}`);
    }

    getQuestionsInformation(): Observable<QuestionsTableRespond[]> {
        return this.httpClient.get<QuestionsTableRespond[]>(this.URL);
    }

    addQuestion(question: Question): Observable<void> {
        return this.httpClient.post<void>(this.URL, question);
    }

    deleteQuestion(id: number): Observable<void> {
        return this.httpClient.delete<void>(this.URL + `/${id}`);
    }

    changeQuestion(id: number, question: Question): Observable<void> {
        return this.httpClient.put<void>(this.URL + `/${id}`, question);
    }
}
