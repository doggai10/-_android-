package com.example.map.pp;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {

        public String name;
        public String password;
        public String id;
        public long birthday;
        public FirebasePost(){

        }


    public FirebasePost(String name, String id, String password, long birthday) {
            this.name=name;
            this.id=id;
            this.password=password;
            this.birthday=birthday;
    }

    public Map<String, Object> toMap(){
            HashMap<String, Object> result= new HashMap<>();
            result.put("name",name);
            result.put("password", password);
            result.put("id", id);
            result.put("birthday", birthday);
            return result;
        }


}
