import {Pipe, PipeTransform} from "@angular/core";

@Pipe({name: 'joinPipe'})

export class JoinPipe implements PipeTransform {
    transform(item):string {
        return item.tracks.map(item => item.name).join(', ')
    }
}