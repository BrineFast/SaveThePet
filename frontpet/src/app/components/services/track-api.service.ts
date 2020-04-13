import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import Track from "../entities/Track";
import { Observable } from 'rxjs';
import ITrack from '../entities/ITrack';

@Injectable({providedIn: "root"})

export default class TrackApiService {
    private URL: string = "http://localhost:4200/assets/testData.json";
    
    constructor(private httpClient: HttpClient) {
    }

    getTrack(id: number): Observable<Track>{
        return this.httpClient.get<Track>(this.URL + "/" + id);
    }

    getTracks(): Observable<Track[]> {
        return this.httpClient.get<Track[]>(this.URL);
    }

    getTracksInformation(): Observable<ITrack> {
        return this.httpClient.get<ITrack>(this.URL);
    }

    addTrack(track: Track): Observable<Track> {
        return this.httpClient.post<Track>(this.URL, track);
    }

    deleteTrack(id: number): Observable<void> {
        return this.httpClient.delete<void>(this.URL + "/" + id);
    }

    changeTrack(id, Track) {
        return this.httpClient.put<Track>(this.URL + "/" + id, Track);
    }
}
