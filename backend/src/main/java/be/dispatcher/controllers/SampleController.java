package be.dispatcher.controllers;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import be.dispatcher.world.World;

@RestController
public class SampleController {

	@Autowired
	private World world;

	@RequestMapping("api/vehicle/status/{status}")
	@ResponseBody
	String home(@PathVariable("status") String status) {
		world.getObjectAndSetToStatus(status);
		return "WORLD STARTED";
	}

}