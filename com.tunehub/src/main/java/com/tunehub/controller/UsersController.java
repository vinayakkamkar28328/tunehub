package com.tunehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tunehub.entities.Users;
import com.tunehub.services.UsersService;

import jakarta.servlet.http.HttpSession;

//Adding the user information and checking the user if user is new then user account will be created
//else it will display "User already exists"
@Controller
public class UsersController {
	@Autowired
	UsersService services;
	@PostMapping("/register")
	public String addUsers(@ModelAttribute Users user) {
		boolean userStatus=services.emailExists(user.getEmail());
		if(userStatus== false)
		{
			services.addUser(user);
			System.out.println("User added");
		}
		else
		{
			System.out.println("User already exists");
		}
		return "home";
	}
//In this the method will be check the validate mail if the mail is register by admin then it will
//go to admin home or else customerHome page
	@PostMapping("/validate")
	public String validate(@RequestParam("email")String email, @RequestParam("password")String password,HttpSession session){
	{
		
		if(services.validateUser(email,password)==true)
		{
			String role=services.getRole(email);
			session.setAttribute("email",email);
			if(role.equals("admin"))
			{
				return "adminHome";
			}
			else
			{
				return "customerHome";
			}
		}
		else
		{
			return"login";
		}}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
}

