package com.Rest.Application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.Pojo.Administrator;
import com.RestServices.AdminLoginService;
import com.RestServices.AdministratorService;
import com.RestServices.BookService;
import com.RestServices.ReaderService;
//import javax.ws.rs.core.Application;
@ApplicationPath("webServices")
public class RestApplication extends Application {
	
   @Override
   public Set<Class<?>> getClasses(){
	   Set<Class <?>> classes= new HashSet();
	   addResourceClasses(classes);
	   return classes;
	   
   }
   private void addResourceClasses(Set<Class<?>> resources){
	   resources.add(AdminLoginService.class);
	   resources.add(BookService.class);
	   resources.add(ReaderService.class);
	   resources.add(AdministratorService.class);
	   
   }
	

}
