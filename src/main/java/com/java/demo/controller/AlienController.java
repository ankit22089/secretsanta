package com.java.demo.controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.tomcat.util.json.JSONParser;
/*
insert into alien values (101,'Near Jagarnath Temple','Nothing','nikhil8.jsg@gmail.com','Car','google.com','google.com','765623723','Nikhil');
insert into alien values (102,'Gumadera','Nothing','nikhil8.bph@gmail.com','Cartoon','www.google.com','www.google.com','765623724','Nikhil Sharma');
insert into alien values (103,'Belpahar','Nothing','nikhil8.od@gmail.com','Car','www.google.com','google.com','765623744','Nitin');
select * from alien;
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.java.demo.dao.AlienRepo;
import com.java.demo.dao.AlienShuffledRepo;
import com.java.demo.dao.BookedSantaRepo;
import com.java.demo.dao.SantaRepo;
import com.java.demo.email.EmailSenderService;
import com.java.demo.model.Alien;
import com.java.demo.model.AlienShuffled;
import com.java.demo.model.AllSanta;
import com.java.demo.model.BookedSanta;

@RestController
@CrossOrigin("*")
public class AlienController {
	
	@Autowired
	AlienRepo repo;
	
	@Autowired
	AlienShuffledRepo reposhuffled;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	SantaRepo reposanta;
	
	@Autowired 
	BookedSantaRepo repoBookedSanta;
	
	@GetMapping("/shuffle")
	public List<AlienShuffled> shuffleAliens()
	{
		reposhuffled.deleteAll();
		ArrayList<Alien> alienList = new ArrayList<Alien>();
		alienList = (ArrayList<Alien>) repo.findAll();
		ArrayList<Integer> aIds = new ArrayList<Integer>();
		for(Alien alien:alienList)
			aIds.add(alien.getAid());
		
		for(int i=0;i<aIds.size();i++)
		{
			AlienShuffled alienShu = new AlienShuffled();
			alienShu.setChild(aIds.get(i));
			alienShu.setAid(alienList.get(aIds.size()-i-1).getAid());
			reposhuffled.save(alienShu);
		}
		return reposhuffled.findAll();
	}
	
	@GetMapping(path="/usershuffled/{id}",produces= {"application/json"})
	public Optional<AlienShuffled> getChildById(@PathVariable(value="id") Integer aid)
	{
		return reposhuffled.findById(aid);
	}
	
	@GetMapping("/users")
	public List<Alien> getAllUsers() {
	  return (List<Alien>) repo.findAll();
	}
	
	@GetMapping("/email")
	public String sendEmailUsers() {
		String result="";	
		try
		{
		List<AlienShuffled> aIdList = new ArrayList<AlienShuffled>();
		aIdList = reposhuffled.findAll();
		for(AlienShuffled alienShuffled:aIdList)
		{
			
			
				Optional<Alien> alienSender = repo.findById(alienShuffled.getAid());
				Optional<Alien> alienReceiver = repo.findById(alienShuffled.getChild());
				String message = "Hi,"+alienSender.get().getName()+"\n\n Your Child Details are"
					+"\n\nName : "+alienReceiver.get().getName()
					+"\nMobile : "+alienReceiver.get().getMobileNo()
					+"\nEmail : "+alienReceiver.get().getEmailId()
					+"\nAddress : "+alienReceiver.get().getAddress()
					+"\nGift : "+alienReceiver.get().getGift()
					+"\nGift Link 1 : "+alienReceiver.get().getGiftLink1()
					+"\nGift Link 2 : "+alienReceiver.get().getGiftLink2()
					+"\nDescription : "+alienReceiver.get().getDescription()
					+"\n\nThank You \n\n (note: in case of questions mail to nikhil.shareware@gmail.com)"
					;
				String subject = "[Secret Santa] Your child Details";
				emailSenderService.sendEmail(alienSender.get().getEmailId(),subject,message);
				result = "Successfully Mailed";			
		}
		}
		catch (Exception e) {
			result = "Unable to send mail";
		}
		return "{\"response\" :\"" +result+"\"}";
	}
	@GetMapping(path="/users/{id}",produces= {"application/json"})
	public Optional<Alien> getUsersById(@PathVariable(value = "id") Integer aid) {
	  return repo.findById(aid);
	}
	
	@DeleteMapping(path="/users/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteUsersById(@PathVariable(value = "id") Integer aid) {
		String result="";
		try {
			repo.deleteById(aid);
			result = "Successfully Deleted";
		}
		catch (Exception e) {
			result = "Unable to delete";
		}
		return "{\"response\" :\"" +result+"\"}";
	}
	
	@PostMapping(path="/users",produces= {"application/json"})
	public Alien addUsers(@RequestBody Alien alien) {
	  alien = repo.save(alien);
	  try {
		BookedSanta bookedSanta = new BookedSanta();
		bookedSanta.setId(reposanta.findById(Integer.parseInt(alien.getParent())).get().getId());
		bookedSanta.setName(reposanta.findById(Integer.parseInt(alien.getParent())).get().getName());
		repoBookedSanta.save(bookedSanta);
		reposanta.deleteById(Integer.parseInt(alien.getParent()));
	  }
	  catch (Exception e) {
		System.out.println("Unable to remove Santa");
	  }
	  return alien;	
	}
	
	@PostMapping(path="/addsanta",produces= {"application/json"})
	public AllSanta addSantas(@RequestBody AllSanta santa) {
	  santa = reposanta.save(santa);
	  return santa;	
	}
	
	@GetMapping(path="/getsanta",produces= {"application/json"})
	public List<AllSanta> getSanta() {
	  return reposanta.findAll();
	}
	
	@DeleteMapping(path="/deletesanta/{id}",produces= {"application/json"})
	public String deleteSanta(@PathVariable(value="id") Integer id) 
	{
		String result="";
		try {
			reposanta.deleteById(id);
			result = "Successfully Deleted";
		}
		catch (Exception e) {
			result = "Unable to delete";
		}
		return "{\"response\" :\"" +result+"\"}";
	}
}
