import { User } from "./user";

export class Blog {

        public id: number | null
        public content: string | null
        public userDto: User | null
        public createdAt: Date | null
        public updatedAt: Date | null


        constructor(id: number | null, content: string | null, userDto: User | null,createdAt: Date | null, updatedAt: Date | null) {
            this.id = id;
            this.content = content;
            this.userDto = userDto;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }



}
