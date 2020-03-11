package com.bridgelabz.fundookeep.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundookeep.dao.Label;
import com.bridgelabz.fundookeep.dao.User;
import com.bridgelabz.fundookeep.dto.LabelDTO;
import com.bridgelabz.fundookeep.dto.Response;
import com.bridgelabz.fundookeep.exception.LabelException;
import com.bridgelabz.fundookeep.exception.UserException;
import com.bridgelabz.fundookeep.repository.LabelRepository;
import com.bridgelabz.fundookeep.repository.UserRepository;
import com.bridgelabz.fundookeep.utils.JwtUtils;

@Service
@PropertySource("classpath:message.properties")
public class LabelServiceProvider implements LabelService{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private LabelRepository labelRepository;
	
	@Autowired
	private Environment env;
	
	public Response createLabel(String token, LabelDTO labelDTO) {
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		Label label = new Label(labelDTO);
		boolean exist = user.getLabels().stream().noneMatch(lbl -> lbl.getLabelName().equalsIgnoreCase(label.getLabelName()));

		if(exist) {
			user.getLabels().add(label);
			repository.save(user);
			return new Response(HttpStatus.OK.value(), "label created successfully",labelDTO);
		}
		return new Response(HttpStatus.ALREADY_REPORTED.value(), "this label already exist",labelDTO);
	}
	 
	@Transactional
	public void updateLabel(String token,  LabelDTO labelDTO, Long lId) {
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Label> labels = user.getLabels();
		Label filteredLabel = labels.stream().filter(lbl -> lbl.getLabelId().equals(lId)).findFirst().orElseThrow(() -> new LabelException(404,"Label not exist!!!!"));
		filteredLabel.setLabelName(labelDTO.getLabelName());
		repository.save(user);
	}
	
	@Transactional
	public void deleteLabel(String token, Long lId) {
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Label> labels = user.getLabels();
		Label filteredLabel = labels.stream().filter(lbl -> lbl.getLabelId().equals(lId)).findFirst().orElseThrow(() -> new LabelException(404,"Label not exist!!!!"));
		labels.remove(filteredLabel);
		labelRepository.deleteById(filteredLabel.getLabelId());
		repository.save(user);
	}
	
	public List<Label> getAllLables(String token){
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Label> labels = user.getLabels();
		return labels;
	}
	
	
}
