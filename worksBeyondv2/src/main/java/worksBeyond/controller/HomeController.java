package worksBeyond.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import worksBeyond.Service.UserService;
import worksBeyond.model.User;



@Controller
@RequestMapping(value="/")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
    public ModelAndView homepage(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("welcomeHome");
        return mav;
    }
   
    @RequestMapping(value= "/ourStory")
    public ModelAndView ourStorypage(){
        return new ModelAndView("ourStory");
    }  
    
    @RequestMapping(value= "/whoWeServe")
    public ModelAndView whoWeServepage(){
        return new ModelAndView("whoWeServe");
    }  
    
    @RequestMapping(value= "/requestServices")
    public ModelAndView requestServicespage(){
        return new ModelAndView("requestServices");
    } 
    
    @RequestMapping(value= "/faq")
    public ModelAndView faqpage(){
        return new ModelAndView("faq");
    }  
    
    @RequestMapping(value= "/contactUs")
    public ModelAndView contactUspage(){
        return new ModelAndView("contactUs");
        
    }
   
}
