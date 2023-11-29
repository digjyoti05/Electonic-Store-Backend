package com.Digjyoti.electronic.store.exceptions;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;

@Builder
public class ResourceNotFoundExcepation extends RuntimeException {

    public ResourceNotFoundExcepation(){
        super("Resource Not found");
    }
    public ResourceNotFoundExcepation(String message){
        super(message);
        System.out.println("ENTERING: "+ message);

    }
}
