import { Student } from "./student";
import { Tutor } from "./tutor";

export class Schedule {


        public id: number | null
        public studentDto: Student | null
        public tutorDto: Tutor | null
        public startTime: Date | null
        public endTime: Date | null


        constructor(id: number | null, studentDto: Student | null, tutorDto: Tutor | null,startTime: Date | null, endTime: Date | null) {
            this.id = id;
            this.studentDto = studentDto;
            this.tutorDto = tutorDto;
            this.startTime = startTime;
            this.endTime = endTime;
        }

}
