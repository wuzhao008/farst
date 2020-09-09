package com.farst;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; 

 

@SpringBootApplication 
@MapperScan("com.farst.**.mapper")
public class FarstApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(FarstApplication.class, args);
    }
}

