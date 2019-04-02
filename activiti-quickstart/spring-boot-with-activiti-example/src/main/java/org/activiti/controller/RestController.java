package org.activiti.controller;

import org.activiti.dto.Mail;
import org.activiti.dto.Ping;
import org.activiti.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RestController {
	private final MailService mailService;

	@Autowired
	public RestController(MailService mailService) {
		this.mailService = mailService;
	}

	@RequestMapping(value = "/ping", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Ping ping() {
    	Ping ping = new Ping();
    	ping.setPong("pong");
		return ping;
	}

	@RequestMapping(value = "/mails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Mail> getMails() {
		List<Mail> mails = mailService.fetch(); //new ArrayList<Mail>();
		//mails.add(new Mail());
		return mails;
	}

}
