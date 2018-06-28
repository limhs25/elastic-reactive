import {Injectable} from '@angular/core';
import {Observable} from "rxjs/internal/Observable";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Logic} from "../model/logic";
import {Subject} from "rxjs/internal/Subject";

@Injectable()
export class LogicService {
  private root = `/api/logic`;
  private querySource = new Subject<string>();
  querySource$ = this.querySource.asObservable();

  constructor(private http: HttpClient) {
  }

  reload(query: string = "") {
    this.querySource.next(query);
  }

  search(query: string, page: number = 0): Observable<Logic[]> {
    const url = this.root;
    return this.http.get<Logic[]>(url, {params: new HttpParams().set("q", query).set("p", page.toString())});
  }

  loadOne(id: string): Observable<Logic> {
    const url = `${this.root}/${id}`;
    return this.http.get<Logic>(url);
  }

  update(id: string, logic: Logic): Observable<Logic> {
    const url = `${this.root}/${id}`;
    return this.http.put<Logic>(url, logic);
  }

  create(logic: Logic): Observable<Logic> {
    const url = this.root;
    return this.http.post<Logic>(url, logic);
  }

  delete(id: string) {
    const url = `${this.root}/${id}`;
    return this.http.delete(url);
  }

  getIndicatorsStream(): Observable<Logic> {
    console.log("subscribed to this");
    return Observable.create((observer) => {
      let eventSource = new EventSource("/api/logic/stream");

      eventSource.onmessage = (event) => {
        let json = JSON.parse(event.data);
        let logic = Object.assign(new Logic, json);
        observer.next(logic);
      };
      eventSource.onerror =
        (error) => {
          if (eventSource.readyState === 0) {
            console.log('The stream has been closed by the server.');
            eventSource.close();
            observer.complete();
          } else {
            observer.error('EventSource error: ' + error);
          }
        }
    });
  }

}
